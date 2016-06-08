package project.iuh.hh.quosera.entites;

import java.util.List;
import java.util.UUID;

/**
 * Created by Nguyen Hoang on 19/02/2016.
 */
public class Tag {
    public Tag(){};
    public Tag(String tagname,List<String> postsoftag)
    {
        this.setTagid(UUID.randomUUID().toString());
        this.setTagname(tagname);
        this.setPostsoftag(postsoftag);
    };
    private String tagid;
    private String tagname;
    private List<String> postsoftag;

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public List<String> getPostsoftag() {
        return postsoftag;
    }

    public void setPostsoftag(List<String> postsoftag) {
        this.postsoftag = postsoftag;
    }

    public String getTagid() {
        return tagid;
    }

    public void setTagid(String tagid) {
        this.tagid = tagid;
    }
}
