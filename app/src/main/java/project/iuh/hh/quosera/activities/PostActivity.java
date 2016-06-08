package project.iuh.hh.quosera.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

import project.iuh.hh.quosera.myadapter.MyTagAdapter;
import project.iuh.hh.quosera.information.Information;
import project.iuh.hh.quosera.R;
import project.iuh.hh.quosera.entites.Post;
import project.iuh.hh.quosera.entites.Tag_Item;
import project.iuh.hh.quosera.parse.ParsePostHelper;
import project.iuh.hh.quosera.parse.ParseTagHelper;
import project.iuh.hh.quosera.parse.ParseUserHelper;
import project.iuh.hh.quosera.utils.Utils;

public class PostActivity extends AppCompatActivity {

    private ListView listview;
    private MyTagAdapter Tag_Adapter;
    private ArrayList<Tag_Item> list_tag_item;
    private EditText edttitle,edtcontent;
    private ArrayList<String> arrayListTag;
    private Button btnPost;
    private String ID_POST_UPDATE;
    private ArrayList<String> sourceItem = new ArrayList<String>()
    {{
            add("C/C++");
            add("C#");
            add("Java");
            add("Android");
            add("Html");
            add("Graphic Design");
            add("Other");
        }};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_post);
        edttitle = (EditText)findViewById(R.id.edttitle);
        edtcontent = (EditText)findViewById(R.id.edtcontent);
        btnPost = (Button)findViewById(R.id.btnPost);
        listview = (ListView)findViewById(R.id.listviewtag);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle.get("Source").equals("ListViewAdapter"))
        {
            //can render
            ID_POST_UPDATE = bundle.getString("PostID");
            edttitle.setText(bundle.getString("PostTitle"));
            edtcontent.setText(bundle.getString("PostContent"));
            arrayListTag = new ArrayList<String>();
            arrayListTag = bundle.getStringArrayList("ArrayTag");
            prepare_list_tag(69);//this is stupid function
            btnPost.setText("Update");
            btnPost.setTag("update");
        }
        else
        {
            prepare_list_tag();
            btnPost.setText("Post");
            btnPost.setTag("post");
        }


        Tag_Adapter = new MyTagAdapter(this,R.layout.tag_item_layout,list_tag_item);

        listview.setAdapter(Tag_Adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tag_Item item = (Tag_Item) parent.getItemAtPosition(position);
                item.setIschecked(!item.ischecked());
                Tag_Adapter.notifyDataSetChanged();
                Log.w("A = ", position + "");
            }
        });
    }
    private void prepare_list_tag() {
        list_tag_item = new ArrayList<Tag_Item>();
        for (String str: sourceItem)
            list_tag_item.add(new Tag_Item(str,false));
    }
    private void prepare_list_tag(int i) {
        list_tag_item = new ArrayList<Tag_Item>();

        for (String str: sourceItem)
        {
            list_tag_item.add(new Tag_Item(str,arrayListTag.contains(str)));
        }

    }
    public void doPost(View v)
    {
        List<String> listtag = new ArrayList<String>();
        for (Tag_Item i : list_tag_item) {
            if (i.ischecked()) {
                listtag.add(i.getContent());
            }
        }
        if (listtag.size()==0 || !isvalid())
        {
            Toast.makeText(this, "Invalid Input", Toast.LENGTH_LONG).show();
            return;
        }

        if (v.getTag().equals("update"))
        {
            Post post = new Post(ID_POST_UPDATE,edttitle.getText().toString(),edtcontent.getText().toString(),Information.email,listtag);
            if (ParsePostHelper.updatePost(post))
                Toast.makeText(this, "Update Success", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "Update Fail", Toast.LENGTH_LONG).show();
        }
        else {
            Post post = new Post(edttitle.getText().toString(), edtcontent.getText().toString(), Information.email, listtag);
            ParsePostHelper parsePostHelper = new ParsePostHelper();
            if (parsePostHelper.postObject(post) && ParseUserHelper.addPostInFollowing(Information.email, post.getPostid()))
                Toast.makeText(this, "Post Success", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "Post Fail", Toast.LENGTH_LONG).show();
        }


    }
    public void doCancel(View v)
    {
        this.finish();
    }
    private boolean isvalid()
    {
        if (edttitle.getText().toString().isEmpty() || edtcontent.getText().toString().isEmpty())
            return false;
        return  true;
    }
}
