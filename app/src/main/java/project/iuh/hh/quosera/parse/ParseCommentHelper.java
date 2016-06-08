package project.iuh.hh.quosera.parse;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;

import project.iuh.hh.quosera.entites.Comment;

/**
 * Created by Nguyen Hoang on 19/02/2016.
 */
public class ParseCommentHelper implements ParseHelper{
    @Override
    public boolean postObject(Object o) {
        try
        {
            Comment comment = (Comment)o;
            ParseObject po = new ParseObject("Comment");
            po.put("CommentID",comment.getCommentid());
            po.put("Owner_email",comment.getOwner_email());
            po.put("Votes",comment.getVotes());
            po.put("Content",comment.getContent());
            po.saveInBackground();
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    @Override
    public Comment getObjectById(String code) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Comment");
        ParseObject po;
        try
        {
            query.whereEqualTo("CommentID",code);
            po = query.getFirst();
            Comment comment = new Comment();
            comment.setCommentid(po.getString("CommentID"));
            comment.setOwner_email(po.getString("Owner_email"));
            comment.setContent(po.getString("Content"));
            comment.setVotes(po.getInt("Votes"));

            return comment;
        }
        catch (com.parse.ParseException e) {
            return null;
        }
    }
}
