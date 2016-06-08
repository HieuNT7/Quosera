package project.iuh.hh.quosera.entites;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nguyen Hoang on 17/04/2016.
 */
public class TempPost {
    private String fullName;
    private int numberOfComment;
    private boolean isLike;
    private boolean isFollowing;

    public boolean isEdit() {
        return isEdit;
    }

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    private boolean isEdit;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    private List<String> tags;



    public TempPost() {
    }

    public TempPost(String fullName, int numberOfComment, boolean isLike, boolean isFollowing,boolean isEdit, List<String> tags) {
        this.fullName = fullName;
        this.numberOfComment = numberOfComment;
        this.isLike = isLike;
        this.isFollowing = isFollowing;
        this.isEdit = isEdit;
        this.tags = tags;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }



    public int getNumberOfComment() {
        return numberOfComment;
    }

    public void setNumberOfComment(int numberOfComment) {
        this.numberOfComment = numberOfComment;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setIsLike(boolean isLike) {
        this.isLike = isLike;
    }

    public boolean isFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(boolean isFollowing) {
        this.isFollowing = isFollowing;
    }
}
