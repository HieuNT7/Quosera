package project.iuh.hh.quosera.entites;

import java.util.List;

/**
 * Created by Nguyen Hoang on 19/02/2016.
 */
public class User {
    public User(){};

    public User(String lastname, String firstname, String email, boolean gender, String birthday) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.gender = gender;
        this.birthday = birthday;
        this.introduction = "";
        this.recentlystatus ="";
        this.followPost = null;
        this.likesPost = null;
        this.numberofContribution = 0;
        this.numberofFollower = 0;
        this.numberofPost = 0;
    }

    //5 properties below can change in Quosera
    private String lastname;
    private String firstname;
    private String email;
    private boolean gender;//true male, false female
    private String introduction;
    private String recentlystatus;



    private String birthday;
    private List<String> followPost;

    public List<String> getLikesPost() {
        return likesPost;
    }

    public void setLikesPost(List<String> likesPost) {
        this.likesPost = likesPost;
    }

    private List<String> likesPost;
    private int numberofPost;
    private int numberofFollower;
    private int numberofContribution;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getRecentlystatus() {
        return recentlystatus;
    }

    public void setRecentlystatus(String recentlystatus) {
        this.recentlystatus = recentlystatus;
    }

    public List<String> getFollowPost() {
        return followPost;
    }

    public void setFollowPost(List<String> followPost) {
        this.followPost = followPost;
    }

    public int getNumberofPost() {
        return numberofPost;
    }

    public void setNumberofPost(int numberofPost) {
        this.numberofPost = numberofPost;
    }

    public int getNumberofFollower() {
        return numberofFollower;
    }

    public void setNumberofFollower(int numberofFollower) {
        this.numberofFollower = numberofFollower;
    }

    public int getNumberofContribution() {
        return numberofContribution;
    }

    public void setNumberofContribution(int numberofContribute) {
        this.numberofContribution = numberofContribute;
    }
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
