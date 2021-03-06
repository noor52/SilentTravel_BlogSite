package com.noor.blog.services;


import com.noor.blog.config.persistancy.HibernateConfig;
import com.noor.blog.dtos.*;
import com.noor.blog.models.*;
import com.noor.blog.dtos.*;
import com.noor.blog.models.Comment;
import com.noor.blog.models.Like;
import com.noor.blog.models.Post;
import com.noor.blog.models.User;
import org.hibernate.HibernateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private HibernateConfig hibernateConfig;

    public PostService(HibernateConfig hibernateConfig) {
        this.hibernateConfig = hibernateConfig;
    }

    @Transactional
    public long createNewPost(PostDto postDto, UserDto userDto) {

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        LocalDateTime dateTime = LocalDateTime.now();
        User user = new User();
        BeanUtils.copyProperties(userDto, user);

        Post post = new Post();
        post.setPostText(postDto.getPostText());
        post.setPostImages(postDto.getPostImages());
        post.setActive(false);
        post.setUser(user);
        post.setPostTime(dateTime);


        long postId = 0;
        try {
            postId = (long) session.save(post);

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return postId;

    }

    public Post getPostById(long postid) {

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Post> postCQ = cb.createQuery(Post.class);
        Root<Post> root = postCQ.from(Post.class);

        postCQ.where(cb.equal(root.get("id"), postid));

        var query = session.createQuery(postCQ);
        var post = new Post();
        try {
            post = query.getSingleResult();
        } catch (HibernateException e) {

            e.printStackTrace();
        } finally {
            session.close();
        }


        return post;

    }

    public List<Post> getAllPost() {

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Post> postCQ = cb.createQuery(Post.class);
        Root<Post> root = postCQ.from(Post.class);
        postCQ.select(root);
        var query = session.createQuery(postCQ);

        List<Post> resultList = new ArrayList<>();

        try {
            resultList = query.getResultList();

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return resultList;
    }

    /*  public List<Post> getAllPostWithCommentAndLike() {

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()){
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Post> postCQ = cb.createQuery(Post.class);
        Root<Post> root = postCQ.from(Post.class);
        postCQ.select(root);
        var query = session.createQuery(postCQ);

        List<Post> resultList = new ArrayList<>();

        try {
            resultList = query.getResultList();

            for (int i = 0; i<resultList.size(); i++){
               var comments =  resultList.get(i).getComments();
                var likes=  resultList.get(i).getLikes();
            }

        }catch (HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return resultList;
    }
*/
    public List<PostDto> getAllPostDtoWithCommentAndLike(long userId) {

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Post> postCQ = cb.createQuery(Post.class);
        Root<Post> root = postCQ.from(Post.class);
        postCQ.select(root);
        var query = session.createQuery(postCQ);

        List<PostDto> resultList = new ArrayList<>();

        try {
            query.getResultList().forEach(post -> {

                PostDto dto = new PostDto();
                BeanUtils.copyProperties(post, dto);
                dto.setComments(post.getComments());
                dto.setLikes(post.getLikes());
                dto.setTotalComment(post.getComments().size());
                dto.setTotalLike(post.getLikes().size());
                dto.setIsLiked(false);
                var likes = dto.getLikes();

                for (int i = 0; i < likes.size(); i++) {

                    if (likes.get(i).getWoner().getId() == userId) {

                        dto.setIsLiked(true);
                        break;
                    }
                }


                resultList.add(dto);


            });

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return resultList;
    }

    @Transactional
    public long addNewComment(CommentDto commentDto) {
        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDto, comment);

        comment.setCommentTime(LocalDateTime.now());

        long commentId = 0;
        try {
            commentId = (long) session.save(comment);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }

        } finally {
            session.close();
        }

        return commentId;
    }

    public CommentDto getCommentById(long commentId) {
        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Comment> ccQuery = cb.createQuery(Comment.class);
        Root<Comment> root = ccQuery.from(Comment.class);

        ccQuery.where(cb.equal(root.get("id"), commentId));

        var query = session.createQuery(ccQuery);

        Comment comment = new Comment();

        try {
            comment = query.getSingleResult();

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        CommentDto commentDto = new CommentDto();
        BeanUtils.copyProperties(comment, commentDto);

        return commentDto;


    }

    public long addNewLike(long postId, long userId) {
        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        Like like = new Like();

        Post post = new Post();
        post.setId(postId);
        User user = new User();
        user.setId(userId);

        like.setPost(post);
        like.setWoner(user);

        long likeId = 0;
        try {
            likeId = (long) session.save(like);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return likeId;
    }

    public boolean isAlreadyLiked(long postId, long userId) {

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Like> likeCQ = cb.createQuery(Like.class);
        Root<Like> root = likeCQ.from(Like.class);

        likeCQ.where(cb.and(cb.equal(root.get("post"), postId), cb.equal(root.get("woner"), userId)));


        var query = session.createQuery(likeCQ);


        var likeList = new ArrayList<Like>();

        try {
            likeList = (ArrayList<Like>) query.getResultList();
            transaction.commit();

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        if (likeList.size() <= 0) {
            return false;
        } else {
            return true;
        }

    }

    @Transactional
    public long removeLike(long postId, long userId) {

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaDelete<Like> criteriaDelete = cb.createCriteriaDelete(Like.class);
        Root<Like> root = criteriaDelete.from(Like.class);

        Post post = new Post();
        post.setId(postId);
        criteriaDelete.where(cb.and(cb.equal(root.get("post"), postId), cb.equal(root.get("woner"), userId)));


        var query = session.createQuery(criteriaDelete);

        long val = 0;

        try {
            val = query.executeUpdate();
            transaction.commit();

        } catch (HibernateException e) {

            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
        }

        return val;

    }

    public List<Long> getFollowingUserIds(long userId) {

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> userfollowingQuery = cb.createQuery(User.class);
        Root<User> root = userfollowingQuery.from(User.class);
        //userCriteriaQuery.select(root.get("following"));
        userfollowingQuery.where(cb.equal(root.get("id"), userId));

        var query = session.createQuery(userfollowingQuery);
        List<Long> followingList = new ArrayList<>();
        try {

            // retrieve user and add following
            User user = query.getSingleResult();
//                followingList = user.getFollowing();


        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return followingList;

    }

    public List<PostDto> getPagedposts(long userId, long pageId) {

        //  List<Long> followingUserIds = getFollowingUserIds(userId);
        int pageTotalItem = 3;
        int offset = 0;
        if (pageId == 1) {
        } else {

            offset = (int) ((pageId - 1) * pageTotalItem);
        }


        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }


        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> userfollowingQuery = cb.createQuery(User.class);
        Root<User> root = userfollowingQuery.from(User.class);
        //userCriteriaQuery.select(root.get("following"));
        userfollowingQuery.where(cb.equal(root.get("id"), userId));

        var query = session.createQuery(userfollowingQuery);
//            List<Long> followingList = query.getSingleResult().getFollowing();


        CriteriaQuery<Post> postCQ = cb.createQuery(Post.class);
        Root<Post> postRoot = postCQ.from(Post.class);
        postCQ.select(postRoot);
        postCQ.orderBy(cb.desc(postRoot.get("id")));


//            postCQ.where(postRoot.get("user").in(followingList));

        var postQuery = session.createQuery(postCQ);
        postQuery.setFirstResult(offset);
        postQuery.setMaxResults(pageTotalItem);

        List<PostDto> resultList = new ArrayList<>();

        try {
            postQuery.getResultList().forEach(post -> {

                PostDto dto = new PostDto();
                BeanUtils.copyProperties(post, dto);
                dto.setComments(post.getComments());
                dto.setLikes(post.getLikes());
                dto.setTotalComment(post.getComments().size());
                dto.setTotalLike(post.getLikes().size());
                dto.setIsLiked(false);
                var likes = dto.getLikes();

                for (int i = 0; i < likes.size(); i++) {

                    if (likes.get(i).getWoner().getId() == userId) {

                        dto.setIsLiked(true);
                        break;
                    }
                }


                resultList.add(dto);


            });

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return resultList;

    }

    public List<PostDtoMinimal> getPagedMinimalposts(long userId, Long pageId) {
        //  List<Long> followingUserIds = getFollowingUserIds(userId);
        int pageTotalItem = 3;
        int offset = 0;
        if (pageId == 1) {
        } else {

            offset = (int) ((pageId - 1) * pageTotalItem);
        }


        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }


        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> userfollowingQuery = cb.createQuery(User.class);
        Root<User> root = userfollowingQuery.from(User.class);
        //userCriteriaQuery.select(root.get("following"));
        userfollowingQuery.where(cb.equal(root.get("id"), userId));

        var query = session.createQuery(userfollowingQuery);
//            List<Long> followingList = query.getSingleResult().getFollowing();


        CriteriaQuery<Post> postCQ = cb.createQuery(Post.class);
        Root<Post> postRoot = postCQ.from(Post.class);
        postCQ.select(postRoot);
        postCQ.orderBy(cb.desc(postRoot.get("id")));


//            postCQ.where(postRoot.get("user").in(followingList));

        var postQuery = session.createQuery(postCQ);
        postQuery.setFirstResult(offset);
        postQuery.setMaxResults(pageTotalItem);

        List<PostDtoMinimal> resultList = new ArrayList<>();

        try {

            postQuery.getResultList().stream().forEach(post -> {

                PostDtoMinimal postDtoMinimal = new PostDtoMinimal();
                BeanUtils.copyProperties(post, postDtoMinimal);
                UserDtoMinimal postWoner = new UserDtoMinimal();
                BeanUtils.copyProperties(post.getUser(), postWoner);
                postDtoMinimal.setWoner(postWoner);
                //keep in consideration
                //postDtoMinimal.setPostTimeString();

                List<CommentDtoMinimal> minimalComments = new ArrayList<>();
                List<Comment> postComments = post.getComments();

                postComments.stream().forEach(comment -> {

                    CommentDtoMinimal cm = new CommentDtoMinimal();
                    cm.setId(comment.getId());
                    cm.setCommentText(comment.getCommentText());
                    // keep in consideration
                    //cm.setCommentTime();

                    UserDtoMinimal userDtoMinimal = new UserDtoMinimal();
                    BeanUtils.copyProperties(comment.getWoner(), userDtoMinimal);
                    cm.setWoner(userDtoMinimal);

                    //   postDtoMinimal.addComment(cm);
                    minimalComments.add(cm);

                });

                postDtoMinimal.setComments(minimalComments);


                //dto.setLikes(post.getLikes());
                postDtoMinimal.setTotalComment(post.getComments().size());
                postDtoMinimal.setTotalLike(post.getLikes().size());
                postDtoMinimal.setIsLiked(false);
                var likes = post.getLikes();

                for (int i = 0; i < likes.size(); i++) {

                    if (likes.get(i).getWoner().getId() == userId) {

                        postDtoMinimal.setIsLiked(true);
                        break;
                    }
                }
                resultList.add(postDtoMinimal);


            });

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return resultList;
    }

    @Transactional
    public void deleteComment(long commentId) {

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaUpdate<Comment> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Comment.class);
        Root<Comment> root = criteriaUpdate.from(Comment.class);

        criteriaUpdate.set("isDeleted", false);
        criteriaUpdate.where(criteriaBuilder.equal(root.get("id"), commentId));

        var query = session.createQuery(criteriaUpdate);

        try {
            int i = query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {

            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        query.executeUpdate();


    }


    public List<PostDto> getAllActivwPost() {

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Post> playerCriteriaQuery = cb.createQuery(Post.class);
        Root<Post> root = playerCriteriaQuery.from(Post.class);
        playerCriteriaQuery.where(cb.isTrue(root.get("isActive")));

        var query = session.createQuery(playerCriteriaQuery);

        var postDtoList = new ArrayList<PostDto>();
        try {
            query.getResultList().forEach(post -> {
                PostDto dto = new PostDto();
                BeanUtils.copyProperties(post, dto);
                postDtoList.add(dto);
            });
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }


        return postDtoList;
    }

    public List<PostDto> getAllDeactivePost() {

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()) {
            transaction = session.beginTransaction();
        }

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Post> playerCriteriaQuery = cb.createQuery(Post.class);
        Root<Post> root = playerCriteriaQuery.from(Post.class);
        playerCriteriaQuery.where(cb.isFalse(root.get("isActive")));

        var query = session.createQuery(playerCriteriaQuery);

        var postDtoList = new ArrayList<PostDto>();
        try {
            query.getResultList().forEach(post -> {
                PostDto dto = new PostDto();
                BeanUtils.copyProperties(post, dto);
                postDtoList.add(dto);
            });
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }


        return postDtoList;
    }

    @Transactional
    public void saveEditedPost(Post post){

        var session = hibernateConfig.getSession();
        var transaction = session.getTransaction();

        if (!transaction.isActive()){
            transaction = session.beginTransaction();
        }
        try{
            //session.save(post);
            session.update(post);
            transaction.commit();
        }catch (HibernateException e){
            if (transaction!= null){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    @Transactional
    public void changePostActiveStatus(long postId, boolean isActive) {

        var session = hibernateConfig.getSession();
        var transection = session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaUpdate<Post> postDelete = criteriaBuilder.createCriteriaUpdate(Post.class);
        Root<Post> root = postDelete.from(Post.class);
        postDelete.where(criteriaBuilder.equal(root.get("id"), postId));
        postDelete.set("isActive",isActive);

        var query = session.createQuery(postDelete);

        try {
            query.executeUpdate();
            transection.commit();

        }catch(HibernateException e) {

            if(transection!= null ) {
                transection.rollback();
            }
            e.printStackTrace();

        }finally {
            session.close();
        }

    }
}