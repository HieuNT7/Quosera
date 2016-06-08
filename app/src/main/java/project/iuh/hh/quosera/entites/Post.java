package project.iuh.hh.quosera.entites;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.UUID;

/**
 * Created by Nguyen Hoang on 19/02/2016.
 */
public class Post implements Parcelable{
    public Post(){};
    public Post(String title,String content, String owner_email,List<String> tags)
    {
        //Generate ID
        this.setPostid(UUID.randomUUID().toString());
        this.setPosttitle(title);
        this.setPostcontent(content);
        this.setOwner_email(owner_email);
        this.setTags(tags);

        this.setNumberoflike(0);
        this.setComments(null);
        this.setDateTimeCreated(System.currentTimeMillis());
    }
    //No generate
    public Post(String id,String title,String content, String owner_email,List<String> tags)
    {
        this.setPostid(id);
        this.setPosttitle(title);
        this.setPostcontent(content);
        this.setOwner_email(owner_email);
        this.setTags(tags);

        this.setNumberoflike(0);
        this.setComments(null);
        this.setDateTimeCreated(System.currentTimeMillis());
    }
    //Full paramater, khong co list<comment> de nhe, tang hieu suat
    public Post(String id,String title,String content, String owner_email,int likes,List<String> tags,List<String> comments,long dateTimeCreated)
    {
        //Generate ID
        this.setPostid(id);
        this.setPosttitle(title);
        this.setPostcontent(content);
        this.setOwner_email(owner_email);
        this.setNumberoflike(likes);
        this.setTags(tags);
        this.setComments(comments);
        this.setDateTimeCreated(dateTimeCreated);

    }

    private String postid;
    private String posttitle;
    private String postcontent;
    private String owner_email;
    private int numberoflike;



    private long dateTimeCreated;
    private List<String> tags;
    private List<String> Comments;



    public String getPosttitle() {
        return posttitle;
    }

    public void setPosttitle(String posttitle) {
        this.posttitle = posttitle;
    }

    public String getPostcontent() {
        return postcontent;
    }

    public void setPostcontent(String postcontent) {
        this.postcontent = postcontent;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getOwner_email() {
        return owner_email;
    }

    public void setOwner_email(String owner_email) {
        this.owner_email = owner_email;
    }

    public int getNumberoflike() {
        return numberoflike;
    }

    public void setNumberoflike(int numberoflike) {
        this.numberoflike = numberoflike;
    }

    public List<String> getComments() {
        return Comments;
    }

    public void setComments(List<String> comments) {
        Comments = comments;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }
    public long getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(long dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postid='" + postid + '\'' +
                ", posttitle='" + posttitle + '\'' +
                ", postcontent='" + postcontent + '\'' +
                ", owner_email='" + owner_email + '\'' +
                ", numberoflike=" + numberoflike +
                ", tags=" + tags +
                ", Comments=" + Comments +
                '}';
    }
    public Post(Parcel in) {
        super();
        readFromParcel(in);
    }
    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        public Post[] newArray(int size) {

            return new Post[size];
        }

    };
    public void readFromParcel(Parcel in) {
        postid = in.readString();
        posttitle = in.readString();
        postcontent = in.readString();
        owner_email = in.readString();
        numberoflike = in.readInt();
        dateTimeCreated = in.readLong();
        List<String> listtring = null;
        in.readStringList(tags);
        in.readStringList(Comments);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(postid);
        dest.writeString(posttitle);
        dest.writeString(postcontent);
        dest.writeString(owner_email);
        dest.writeInt(numberoflike);
        dest.writeLong(dateTimeCreated);
        dest.writeStringList(tags);
        dest.writeStringList(Comments);
    }
}
