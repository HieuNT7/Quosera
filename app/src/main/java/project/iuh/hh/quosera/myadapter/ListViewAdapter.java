package project.iuh.hh.quosera.myadapter;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ActionMenuView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import project.iuh.hh.quosera.R;
import project.iuh.hh.quosera.activities.PostActivity;
import project.iuh.hh.quosera.activities.ProfileActivity;
import project.iuh.hh.quosera.entites.Post;
import project.iuh.hh.quosera.entites.TempPost;
import project.iuh.hh.quosera.information.Information;
import project.iuh.hh.quosera.parse.ParsePostHelper;
import project.iuh.hh.quosera.parse.ParseUserHelper;
import project.iuh.hh.quosera.utils.Utils;

/**
 * Created by Nguyen Hoang on 16/04/2016.
 */
public class ListViewAdapter extends ArrayAdapter<Post>{

    private Activity context;
    private int resource;
    private ArrayList<Post> obj;
    private ArrayList<TempPost> arrTempPost;
    public ListViewAdapter(Activity context, int resource,ArrayList<Post> obj) {
        super(context, resource,obj);
        this.context = context;
        this.resource = resource;
        this.obj = obj;
        arrTempPost = new ArrayList<TempPost>();
        if (obj==null)
            return;
        for (Post post:obj) {
            String fullname = new ParseUserHelper().getName(post.getOwner_email());
            int numberOfComment = (post.getComments()==null)?0:post.getComments().size();
            boolean isLike = Utils.islike(Information.email, post.getPostid());
            boolean isFollow = Utils.isfollowing(Information.email, post.getPostid());
            boolean isEdit = post.getOwner_email().equals(Information.email);
            List<String> tags = new ArrayList<String>();
            if (post.getTags()==null)
                tags = new ArrayList<String>();
            else
                tags = post.getTags();
            arrTempPost.add(new TempPost(fullname, numberOfComment, isLike, isFollow,isEdit, tags));
        }
        images = new HashMap<String,int[]>();
        images.put("C/C++",new int[]{R.drawable.img_c1,R.drawable.img_c2,R.drawable.img_c3});
        images.put("Java",new int[]{R.drawable.img_java1,R.drawable.img_java2,R.drawable.img_java3});
        images.put("Android",new int[]{R.drawable.img_android1,R.drawable.img_android2,R.drawable.img_android3});
        images.put("Graphic Design",new int[]{R.drawable.img_g1,R.drawable.img_g2,R.drawable.img_g3});
        images.put("Html",new int[]{R.drawable.img_html1,R.drawable.img_html2,R.drawable.img_html3});
        images.put("C#",new int[]{R.drawable.img_csharp1,R.drawable.img_csharp2,R.drawable.img_csharp3});
        images.put("Other",new int[]{R.drawable.ic_quosera,R.drawable.ic_quosera,R.drawable.ic_quosera});
    }
    private HashMap<String, int[]> images;


    private Post post;
    private TempPost tempost;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView==null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            convertView = layoutInflater.inflate(resource,null);

            holder = new ViewHolder();
            holder.row_image = (ImageView)convertView.findViewById(R.id.row_image);

            holder.row_name = (TextView)convertView.findViewById(R.id.row_name);
            holder.row_title = (TextView)convertView.findViewById(R.id.row_title);
            holder.row_content = (TextView)convertView.findViewById(R.id.row_content);

            holder.row_like = (TextView)convertView.findViewById(R.id.row_like);
            holder.row_comment =(TextView)convertView.findViewById(R.id.row_comment);

            holder.row_tags = (LinearLayout)convertView.findViewById(R.id.row_tags);

            holder.imglike = (ImageView)convertView.findViewById(R.id.imglike);
            holder.imgfollow = (ImageView)convertView.findViewById(R.id.imgfollow);
            holder.imgedit = (ImageView)convertView.findViewById(R.id.imgedit);

            holder.row_time = (TextView)convertView.findViewById(R.id.row_time);

            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder)convertView.getTag();

        post = obj.get(position);
        tempost = arrTempPost.get(position);
        //image
        Random random = new Random();
        String tagname = tempost.getTags().get(0);
        int index = random.nextInt(3);
        holder.row_image.setImageResource(images.get(tagname)[index]);
        //Name
        //final String fullname = new ParseUserHelper().getName(post.getOwner_email());
        holder.row_name.setText(tempost.getFullName());
        //event
        holder.row_name.setTag(post.getOwner_email());
        holder.row_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = (String) v.getTag();
                Intent intent = new Intent(context, ProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("email", email);
                intent.putExtra("bundle", bundle);
                context.startActivity(intent);
            }
        });
        //Title
        holder.row_title.setText(post.getPosttitle());
        //Content
        holder.row_content.setText(post.getPostcontent());

        holder.row_like.setText(post.getNumberoflike() + " Likes");
        holder.row_comment.setText(tempost.getNumberOfComment() + " Comments");
        //TAGS
        if (tempost.getTags()!=null)
        {
            holder.row_tags.removeAllViews();
            for (String str:post.getTags())
            {
                TextView tv = new TextView(context);
                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                llp.setMargins(10,0,0,0);
                tv.setLayoutParams(llp);
                tv.setText("#" + str);
                tv.setBackgroundResource(R.color.green_blue_light);
                holder.row_tags.addView(tv);
            }
        }
        //LIKE
        if (tempost.isLike())
            holder.imglike.setImageResource(R.drawable.heart_red);
        else
            holder.imglike.setImageResource(R.drawable.heart_black);
        holder.imglike.setTag(post.getPostid());
        //event
        final ViewHolder finalHolder = holder;
        holder.imglike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Information.email;
                String postID = (String) v.getTag();
                if (email.isEmpty())
                    return;
                if (Utils.islike(email,postID)) {
                    //unlike process4
                    //update DB
                    ParsePostHelper.changeLike(postID, -1);
                    ParseUserHelper.removeLikePost(email, postID);
                    finalHolder.imglike.setImageResource(R.drawable.heart_black);
                } else {
                    //like process
                    //update DB
                    ParsePostHelper.changeLike(postID, 1);
                    ParseUserHelper.addLikePost(email, postID);
                    finalHolder.imglike.setImageResource(R.drawable.heart_red);
                }
                finalHolder.row_like.setText(ParsePostHelper.getNumberOfLike(postID) + " Likes");
            }
        });

        /*Follow*/
        //Initial
        if (arrTempPost.get(position).isFollowing())
            holder.imgfollow.setImageResource(R.drawable.star_yellow);
        else
            holder.imgfollow.setImageResource(R.drawable.star_black);
        holder.imgfollow.setTag(post.getPostid());

        holder.imgfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Information.email;
                String postID = (String) v.getTag();
                if (email.isEmpty())
                    return;
                if (Utils.isfollowing(email, postID)) {
                    //unlike process4
                    //update DB
                    ParseUserHelper.removePostInFollowing(email, postID);
                    finalHolder.imgfollow.setImageResource(R.drawable.star_black);
                } else {
                    //like process
                    //update DB
                    ParseUserHelper.addPostInFollowing(email, postID);
                    finalHolder.imgfollow.setImageResource(R.drawable.star_yellow);
                }
            }
        });

        //Add Edit button
        if (arrTempPost.get(position).isEdit())
            holder.imgedit.setVisibility(View.VISIBLE);
        else
            holder.imgedit.setVisibility(View.INVISIBLE);
        holder.imgedit.setTag(post);
            holder.imgedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Post post = (Post) v.getTag();
                    if (!post.getOwner_email().equals(Information.email))
                        return;
                    Intent intent = new Intent(context,PostActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Source","ListViewAdapter");
                    bundle.putString("PostID",post.getPostid());
                    bundle.putString("PostTitle",post.getPosttitle());
                    bundle.putString("PostContent",post.getPostcontent());
                    bundle.putStringArrayList("ArrayTag", (ArrayList<String>) post.getTags());
                    intent.putExtra("bundle",bundle);
                    context.startActivity(intent);
                }
            });
        holder.row_time.setText(Utils.getDate(post.getDateTimeCreated(), "dd/MM/yyyy hh:mm:ss"));
        return convertView;
    }


}
