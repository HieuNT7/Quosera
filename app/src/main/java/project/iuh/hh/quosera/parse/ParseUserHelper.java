package project.iuh.hh.quosera.parse;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import project.iuh.hh.quosera.entites.User;


/**
 * Created by Nguyen Hoang on 19/02/2016.
 */
public class ParseUserHelper implements ParseHelper{

    public ParseUserHelper(){};
    @Override
    public boolean postObject(Object o) {
        User user = (User)o;
        ParseObject po = new ParseObject("User");


        po.put("Email", user.getEmail());
        po.put("FirstName", user.getFirstname());
        po.put("LastName", user.getLastname());
        po.put("BirthDay", user.getBirthday());
        po.put("Introduction", user.getIntroduction());
        po.put("Gender", user.getGender());
        po.put("RecentlyStatus", user.getRecentlystatus());
        if (user.getFollowPost()!=null)
            po.put("FollowPost",user.getFollowPost());
        if (user.getLikesPost()!=null)
            po.put("LikesPost",user.getLikesPost());
        po.put("NumberOfPost", user.getNumberofPost());
        po.put("NumberOfContribution", user.getNumberofContribution());
        po.put("NumberOfFollower", user.getNumberofFollower());
        po.saveInBackground();

        return true;
    }
    public List<String> getLikesPost(String email)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        ParseObject po= null;
        try {
            query.whereEqualTo("Email",email);
            po = query.getFirst();
            return po.<String>getList("LikesPost");
        } catch (ParseException e) {
            return null;
        }
    }
    public String getName(String email)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        ParseObject po= null;
        try {
            query.whereEqualTo("Email",email);
            po = query.getFirst();
            return po.getString("FirstName") + " " +po.getString("LastName");
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    @Override
    public User getObjectById(String email){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        ParseObject po= null;
        try {
            query.whereEqualTo("Email",email);
            po = query.getFirst();
            User user = new User();
            user.setEmail(po.getString("Email"));
            user.setFirstname(po.getString("FirstName"));
            user.setLastname(po.getString("LastName"));
            user.setBirthday(po.getString("BirthDay"));
            user.setIntroduction(po.getString("Introduction"));
            user.setGender(po.getBoolean("Gender"));
            user.setRecentlystatus(po.getString("RecentlyStatus"));

            user.setFollowPost(po.<String>getList("FollowPost"));
            user.setNumberofContribution(po.getInt("NumberOfContribution"));
            user.setNumberofPost(po.getInt("NumberOfPost"));
            user.setNumberofFollower(po.getInt("NumberOfFollower"));
            return user;
        } catch (ParseException e) {
            return null;
        }
    }

    //with user id, I can get old object in server
    //and replace all properties by properties from first parameter user
    public boolean UpdateUser(User user)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("Email",user.getEmail());
        try {
            ParseObject po = query.getFirst();
            po.put("Email",user.getEmail());
            po.put("FirstName", user.getFirstname());
            po.put("LastName", user.getLastname());
            po.put("BirthDay", user.getBirthday());
            po.put("Introduction", user.getIntroduction());
            po.put("Gender", user.getGender());
            po.put("RecentlyStatus", user.getRecentlystatus());
            if (user.getFollowPost()!=null)
                po.put("FollowPost",user.getFollowPost());
            if (user.getLikesPost()!=null)
                po.put("LikesPost",user.getLikesPost());
            po.put("NumberOfPost", user.getNumberofPost());
            po.put("NumberOfContribution", user.getNumberofContribution());
            po.put("NumberOfFollower", user.getNumberofFollower());
            po.saveInBackground();
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    public static boolean addLikePost(String email,String id)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("Email",email);
        try {
            ParseObject po = query.getFirst();
            List<String> arr = po.getList("LikesPost");
            if (arr==null)
                arr = new ArrayList<String>();
            arr.add(id);
            po.put("LikesPost",arr);
            po.saveInBackground();
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    public static boolean removeLikePost(String email,String id)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("Email",email);
        try {
            ParseObject po = query.getFirst();
            List<String> arr = po.getList("LikesPost");
            if (arr==null)
                return false;
            arr.remove(id);
            po.put("LikesPost",arr);
            po.saveInBackground();
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    public static List<String> getFollowPost(String email)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("Email",email);
        try {
            ParseObject po = query.getFirst();
            List<String> arr = po.getList("FollowPost");
            return arr;
        } catch (ParseException e) {
            return null;
        }
    }
    public static boolean addPostInFollowing(String email,String postID)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("Email",email);
        try {
            ParseObject po = query.getFirst();
            List<String> arr = po.getList("FollowPost");
            if (arr == null)
                arr = new ArrayList<String>();
            arr.add(postID);
            po.put("FollowPost",arr);
            po.save();
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    public static boolean removePostInFollowing(String email, String postID)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("Email",email);
        try {
            ParseObject po = query.getFirst();
            List<String> arr = po.getList("FollowPost");
            if (arr == null)
                return false;
            arr.remove(postID);
            po.put("FollowPost",arr);
            po.save();
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}
