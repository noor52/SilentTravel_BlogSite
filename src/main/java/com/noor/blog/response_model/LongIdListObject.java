package com.noor.blog.response_model;

import java.util.List;

public class LongIdListObject {

    List<Long> ids;

    public LongIdListObject() {
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    @Override
    public String
    toString() {
      String str = "";
        for(int i = 0; i<ids.size(); i++){
            str = str+" "+ids.get(i);
        }
        return str;
    }
}
