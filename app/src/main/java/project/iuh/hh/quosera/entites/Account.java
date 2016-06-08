package project.iuh.hh.quosera.entites;

/**
 * Created by Nguyen Hoang on 18/03/2016.
 */
public class Account {
    private String email;
    private long password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPassword() {
        return password;
    }

    public void setPassword(long password) {
        this.password = password;
    }

    public Account(){};
    public Account(String email, long password) {
        this.email = email;
        this.password = password;
    }
}
