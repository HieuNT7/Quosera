package project.iuh.hh.quosera.parse;

import com.parse.ParseException;

/**
 * Created by Nguyen Hoang on 19/02/2016.
 */
public interface ParseHelper {
    boolean postObject(Object o);
    Object getObjectById(String code) throws ParseException;
}
