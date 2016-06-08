package project.iuh.hh.quosera.parse;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import project.iuh.hh.quosera.entites.Account;

/**
 * Created by Nguyen Hoang on 20/03/2016.
 */
public class ParseAccountHelper implements ParseHelper {
    @Override
    public boolean postObject(Object o) {
        try {
            Account acc = (Account) o;
            ParseObject po = new ParseObject("Account");
            po.put("Email", acc.getEmail());
            po.put("Password", acc.getPassword());
            po.saveInBackground();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    @Override
    public Account getObjectById(String email){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Account");
        ParseObject po;
        try
        {
            query.whereEqualTo("Email", email);
            po = query.getFirst();
            Account acc = new Account();
            acc.setEmail(po.getString("Email"));
            acc.setPassword(po.getLong("Password"));
            return acc;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public boolean updateAccount(Account acc)
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Account");
        ParseObject po;
        try
        {
            query.whereEqualTo("Email",acc.getEmail());
            po = query.getFirst();
            po.put("Password",acc.getPassword());
            po.save();
            return true;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
