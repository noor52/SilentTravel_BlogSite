package com.noor.blog.controllers;
import com.noor.blog.dtos.CommentDto;
import com.noor.blog.models.Post;
import com.noor.blog.models.User;
import com.noor.blog.request_models.CommentRM;
import com.noor.blog.services.PostService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PostRestController {
    private Logger logger = Logger.getLogger(PostRestController.class);

    @Autowired
    PostService postService;

    @PostMapping("/post/addcomment")
    public ResponseEntity<?> addNewComment(@RequestParam(name = "postId")long postId, @RequestParam(name = "userId") long userId, @RequestParam(name = "commentText") String commentText){

        Post post = new Post();
        post.setId(postId);

        User user = new User();
        user.setId(userId);


        CommentDto commentDto = new CommentDto();
        commentDto.setCommentText(commentText);

        commentDto.setWoner(user);
        commentDto.setPost(post);

        long commentId =  postService.addNewComment(commentDto);
        CommentDto savedCommentDto = postService.getCommentById(commentId);

        CommentRM savedCommentRm = new CommentRM();
        //  BeanUtils.copyProperties(savedCommentDto,savedCommentRm);
        savedCommentRm.setId(savedCommentDto.getId());
        savedCommentRm.setPostId(savedCommentDto.getPost().getId());
        savedCommentRm.setWonerId(savedCommentDto.getWoner().getId());
        savedCommentRm.setCommentText(savedCommentDto.getCommentText());

        //return savedCommentRm;
        return new ResponseEntity<>(savedCommentRm, HttpStatus.OK);

    }



    @PostMapping("/post/like")
    public ResponseEntity<?> addNewLike(@RequestParam(name = "postId")long postId, @RequestParam(name = "userId") long userId){

        logger.info("userId: "+userId+" postId: "+postId);

        long likeid = 0;
        boolean isExists = postService.isAlreadyLiked(postId,userId);
        if (isExists){
            long val = postService.removeLike(postId,userId);
            logger.info("removed val: "+val);

        }else {
            likeid =   postService.addNewLike(postId,userId);
        }

        /*return "redirect:/index";*/

        return new ResponseEntity<>(likeid, HttpStatus.OK);
    }


    public ResponseEntity<?> deleteComment(long commentId){
        postService.deleteComment(commentId);


        return new ResponseEntity<>("Success",HttpStatus.OK);
    }
}

