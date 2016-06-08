package project.iuh.hh.quosera.entites;

import java.util.UUID;

/**
 * Created by Nguyen Hoang on 19/02/2016.
 */
public class Comment {
    public Comment(){};
    public Comment(String owner_email,String content){
        this.setCommentid(UUID.randomUUID().toString());
        this.setOwner_email(owner_email);
        this.setContent(content);
        this.setVotes(0);
    };
    private String commentid;
    private String owner_email;
    private int votes;
    private String content;

    public String getOwner_email() {
        return owner_email;
    }
    public void setOwner_email(String owner_email) {
        this.owner_email = owner_email;
    }

    public int getVotes() {
        return votes;
    }
    public void setVotes(int votes) {
        this.votes = votes;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentid() {
        return commentid;
    }
    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }
}
