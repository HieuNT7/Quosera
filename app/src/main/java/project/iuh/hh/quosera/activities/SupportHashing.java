package project.iuh.hh.quosera.activities;

/**
 * Created by Nguyen Hoang on 20/03/2016.
 */
public class SupportHashing {
    public static long enCryp(String s)
    {
        long res = 0;
        long mod = 1000000000;
        long key = 11;
        for (int i = 0;i<s.length();i++){
            res = (res *key +(int)s.charAt(i))%mod;
        }
        return res;
    }
}
