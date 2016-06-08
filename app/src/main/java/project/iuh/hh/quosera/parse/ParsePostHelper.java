package project.iuh.hh.quosera.parse;

import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import project.iuh.hh.quosera.entites.Post;


/**
 * Created by Nguyen Hoang on 19/02/2016.
 */
public class ParsePostHelper implements ParseHelper {
    @Override
    public boolean postObject(Object o) {
        try
        {
            Post post = (Post)o;
            ParseObject po = new ParseObject("Post");

            po.put("PostID", post.getPostid());
            po.put("PostTitle", post.getPosttitle());
            po.put("PostContent", post.getPostcontent());
            po.put("Owner_email", post.getOwner_email());
            po.put("NumberOfLike", post.getNumberoflike());
            po.put("DateTimeCreated", post.getDateTimeCreated());
            if (post.getTags()!=null)
                po.put("Tags",post.getTags());
            if (post.getComments()!=null)
                po.put("Comments",post.getComments());
            po.saveInBackground();
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    @Override
    public Post getObjectById(String code) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
        ParseObject po;
        try
        {
            query.whereEqualTo("PostID", code);

            po = query.getFirst();
            Post post = new Post();
            post.setPostid(po.getString("PostID"));
            post.setPosttitle(po.getString("PostTitle"));
            post.setPostcontent(po.getString("PostContent"));
            post.setOwner_email(po.getString("Owner_email"));
            post.setTags(po.<String>getList("Tags"));
            post.setNumberoflike(po.getInt("NumberOfLike"));
            post.setComments(po.<String>getList("Comments"));
            post.setDateTimeCreated(po.getLong("DateTimeCreated"));
            return post;
        }
        catch (ParseException e)
        {
            return null;
        }
    }

    public static boolean updatePost(Post post)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
        query.whereEqualTo("PostID",post.getPostid());
        try {
            ParseObject po = query.getFirst();
            po.put("PostTitle", post.getPosttitle());
            po.put("PostContent", post.getPostcontent());

            if (post.getTags()!=null)
                po.put("Tags",post.getTags());
            po.saveInBackground();
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    //parameter index, will get item on server from 30*(index-1)+1 to index*30
    //sample: index = 1, 1->30
    //sample: index = 2,31->60
    public ArrayList<Post> getArrayListFrom(int index) {
        try
        {
            Log.w("Test#1", "getArrayListFrom called");
            ArrayList<Post> res = new ArrayList<Post>();
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
            ParseObject po;
            query.setLimit(2).setSkip(index * 2);
            ArrayList<ParseObject> arr = (ArrayList<ParseObject>) query.find();
            for (ParseObject item : arr)
            {
                Post post = new Post(item.getString("PostID"),item.getString("PostTitle"),item.getString("PostContent"),item.getString("Owner_email"),item.getInt("NumberOfLike"),item.<String>getList("Tags"),item.<String>getList("Comments"),item.getLong("DateTimeCreated"));
                res.add(post);
            }
            Log.wtf("GETARRAYLIST",res.size()+"");
            return res;
        }catch (ParseException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
    public static boolean changeLike(String postid,int value)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
        query.whereEqualTo("PostID",postid);
        try {
            ParseObject po = query.getFirst();
            int numberOfLikepresent = (int) po.get("NumberOfLike");
            po.put("NumberOfLike", numberOfLikepresent + value);
            po.saveInBackground();
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    public static int getNumberOfLike(String id)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
        query.whereEqualTo("PostID",id);
        try {
            ParseObject po = query.getFirst();
            return (int) po.get("NumberOfLike");
        } catch (ParseException e) {
            return 0;
        }
    }
    public static boolean pushCommentInPost(String postID, String commentID)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
        query.whereEqualTo("PostID",postID);
        try {
            ParseObject po = query.getFirst();
            List<String> arr = po.getList("Comments");
            if (arr == null)
                arr = new ArrayList<String>();
            arr.add(commentID);
            po.put("Comments",arr);
            po.save();
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}