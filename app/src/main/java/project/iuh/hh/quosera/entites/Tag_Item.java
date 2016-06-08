package project.iuh.hh.quosera.entites;

/**
 * Created by Nguyen Hoang on 30/03/2016.
 */
public class Tag_Item {
    private String content;
    private boolean ischecked;

    public Tag_Item() {
    }

    public Tag_Item(String content,boolean check) {
        this.content = content;
        this.ischecked = check;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public boolean ischecked() {
        return ischecked;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }


}
