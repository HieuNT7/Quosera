package project.iuh.hh.quosera.parse;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import project.iuh.hh.quosera.entites.Tag;


/**
 * Created by Nguyen Hoang on 20/02/2016.
 */
public class ParseTagHelper implements ParseHelper{
    @Override
    public boolean postObject(Object o) {
        try
        {
            Tag tag = (Tag)o;

            ParseObject po = new ParseObject("Tag");

            po.put("TagID",tag.getTagid());
            po.put("TagName",tag.getTagname());
            po.put("PostOfTag",tag.getPostsoftag());
            po.saveInBackground();
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    @Override
    public Tag getObjectById(String code) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tag");
        ParseObject po;
        try
        {
            query.whereEqualTo("TagID",code);
            po = query.getFirst();
            Tag tag = new Tag();
            tag.setTagid(po.getString("TagID"));
            tag.setPostsoftag(po.<String>getList("PostOfTag"));
            tag.setTagname(po.getString("TagName"));


            return tag;
        }
        catch (ParseException e)
        {
            return null;
        }
    }
}
