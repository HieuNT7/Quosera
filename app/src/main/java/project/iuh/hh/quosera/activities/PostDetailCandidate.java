package project.iuh.hh.quosera.activities;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import project.iuh.hh.quosera.R;
import project.iuh.hh.quosera.entites.Comment;
import project.iuh.hh.quosera.entites.Post;
import project.iuh.hh.quosera.entites.User;
import project.iuh.hh.quosera.information.Information;
import project.iuh.hh.quosera.parse.ParseCommentHelper;
import project.iuh.hh.quosera.parse.ParsePostHelper;
import project.iuh.hh.quosera.parse.ParseUserHelper;
import project.iuh.hh.quosera.utils.Utils;

public class PostDetailCandidate extends AppCompatActivity {

    TextView tv_title, tv_postcontent;
    EditText edt_comment;
    Button btn_send;
    ListView lstview;
    ArrayList<String> arr_cmt;
    ArrayAdapter<String>arr_adapt;

    ParseCommentHelper parseComment = new ParseCommentHelper();
    ParseUserHelper parseUser = new ParseUserHelper();
    ParsePostHelper parsePost = new ParsePostHelper();

    int FLAG = 0;
    private String postID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_post_detail_candidate);
        getcontrols();

        postID = getIntent().getBundleExtra("bundle").getString("postID");
        ParsePostHelper parsepost = new ParsePostHelper();
        final Post post;
        post = parsepost.getObjectById(postID);

        renderContent(post);
        renderComment(post);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment_content = edt_comment.getText().toString();
                if(!TextUtils.isEmpty(comment_content))
                {

                    String owner_email = Information.email;
                    Comment cmt = new Comment(owner_email, edt_comment.getText().toString());
                    parseComment.postObject(cmt);
                    parsePost.pushCommentInPost(postID, cmt.getCommentid());
                    User user = parseUser.getObjectById(owner_email);
                    arr_cmt.add(cmt.getContent() + "\n--" + user.getFirstname() + " " + user.getLastname() + "--");
                    //renderContent(post);
                    if(FLAG == 1)
                        arr_adapt.notifyDataSetChanged();
                    else
                    {
                        arr_adapt = new ArrayAdapter<String>(PostDetailCandidate.this, android.R.layout.simple_expandable_list_item_1, arr_cmt);
                        lstview.setAdapter(arr_adapt);
                    }
                    edt_comment.setText(null);
                    edt_comment.requestFocus();
                }
                else
                    Toast.makeText(PostDetailCandidate.this, "Leave a comment, please", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void renderContent(Post post) {
        User owner = parseUser.getObjectById(post.getOwner_email());

        tv_title.setTypeface(Typeface.DEFAULT_BOLD);
        tv_title.setText(post.getPosttitle());
        //Render post title
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyy");
        //Render  content
        String datetime = formatter.format(new Date(post.getDateTimeCreated()));
        String content = owner.getLastname() + " " + owner.getFirstname() + " : " +
        post.getPostcontent() + "\n\n" + datetime + "              " + post.getNumberoflike() + " like  ";
        if(post.getComments()!=null) {
            content += post.getComments().size() + " comments";
        }
        else
            content += "0 comment";
        tv_postcontent.setText(content);
    }

    private void renderComment(Post post){
        //render comment
        arr_cmt = new ArrayList<String>();
        Comment cmt = null;
        User user = null;

        if(post.getComments()!=null)
        {
            for(String comment : post.getComments()){
                cmt = parseComment.getObjectById(comment);
                user = parseUser.getObjectById(cmt.getOwner_email());
                arr_cmt.add(cmt.getContent() + "\n--" + user.getFirstname() + " " + user.getLastname() + "--");
            }
            arr_adapt = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, arr_cmt);
            lstview.setAdapter(arr_adapt);
            FLAG = 1;
        }
    }

    private void getcontrols(){
        tv_title = (TextView) findViewById(R.id.tv_detail_titlepost);
        tv_postcontent = (TextView) findViewById(R.id.tv_detail_contentpost);
        btn_send = (Button) findViewById(R.id.btn_send);
        edt_comment = (EditText) findViewById(R.id.edt_comment);
        lstview = (ListView) findViewById(R.id.lv_detail_listcmt);
    }
}
