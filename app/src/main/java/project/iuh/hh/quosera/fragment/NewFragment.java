package project.iuh.hh.quosera.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import project.iuh.hh.quosera.R;
import project.iuh.hh.quosera.activities.MainActivity;
import project.iuh.hh.quosera.activities.PostDetailCandidate;
import project.iuh.hh.quosera.activities.ProfileActivity;
import project.iuh.hh.quosera.entites.Post;
import project.iuh.hh.quosera.information.Information;
import project.iuh.hh.quosera.myadapter.ListViewAdapter;
import project.iuh.hh.quosera.parse.ParsePostHelper;
import project.iuh.hh.quosera.parse.ParseUserHelper;
import project.iuh.hh.quosera.utils.SavingArrayList;

/**
 * Created by Nguyen Hoang on 18/04/2016.
 */

public class NewFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    public  ArrayList<Post> arrPostNew;
    public  ArrayList<Post> arrPostHot;
    public  ArrayList<Post> arrPostFollowing;


    public ProgressDialog progressDialog;
    public GridView gridView;

    private SwipeRefreshLayout swipeRefreshLayout;

    public ListViewAdapter listViewAdapterNew;
    private int mNum;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.new_swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        return inflater.inflate(R.layout.fragment_new,null);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt("type") : 1;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = (GridView)view.findViewById(R.id.gridview);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post post = (Post) parent.getItemAtPosition(position);
                Log.wtf("New",post.getPostid());
                Intent intent = new Intent(getActivity(), PostDetailCandidate.class);
                Bundle bundle = new Bundle();
                bundle.putString("postID",post.getPostid());
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });
        arrPostNew = new ArrayList<Post>();
        arrPostHot = new ArrayList<Post>();
        arrPostFollowing = new ArrayList<Post>();
        new Load(mNum).execute();
    }

    @Override
    public void onRefresh() {
        new Load(mNum).execute();
    }

    private class Load extends AsyncTask<Void, Void, Void> {
        private ArrayList<Post> arrPost = new ArrayList<Post>();
        private ListViewAdapter listViewAdapter;
        int n;
        public Load(int n){
            this.n = n;
            Log.wtf("NewFr",n + "");
        }

        @Override
        protected Void doInBackground(Void... params) {
            ParsePostHelper parsePostHelper = new ParsePostHelper();
            if (n==0) {
                arrPostNew =  parsePostHelper.getArrayListFrom(0);
                SavingArrayList.arrNew = arrPostNew;
                Collections.sort(arrPostNew, new TimeCreatedCompare());
                }
            else if (n==1) {
                arrPostHot = parsePostHelper.getArrayListFrom(0);
                Collections.sort(arrPostHot, new NumberOfCommentCompare());
            } else if (n==2){
                List<String> arrFollowing = ParseUserHelper.getFollowPost(Information.email);
                if (arrFollowing == null)
                    arrPostFollowing = new ArrayList<Post>();
                else
                {
                    int cnt = 0;
                    for (String str : arrFollowing) {
                        cnt++;
                        Post post = new ParsePostHelper().getObjectById(str);
                        arrPostFollowing.add(post);
                        if (cnt==5)
                            break;
                    }
                    SavingArrayList.arrFollowing = arrPostFollowing;
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
            progressDialog.setMessage("Loading Data ...");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(n==0 && arrPostNew!=null){
                listViewAdapterNew = new ListViewAdapter(getActivity(),R.layout.row_post, arrPostNew);
                gridView.setAdapter(listViewAdapterNew);
            }
            else if (n==1 && arrPostHot!=null) {
                listViewAdapterNew = new ListViewAdapter(getActivity(),R.layout.row_post, arrPostHot);
                gridView.setAdapter(listViewAdapterNew);
            }
            else if (n==2 && arrPostFollowing!=null)
            {
                listViewAdapterNew = new ListViewAdapter(getActivity(),R.layout.row_post, arrPostFollowing);
                gridView.setAdapter(listViewAdapterNew);
            }
            progressDialog.dismiss();
        }
    }

    private class TimeCreatedCompare implements Comparator<Post> {
        public int compare(Post left, Post right) {
            return (left.getDateTimeCreated() > right.getDateTimeCreated()) ? -1 : 1;
        }
    }

    private class NumberOfCommentCompare implements Comparator<Post> {

        @Override
        public int compare(Post left, Post right) {
            int numberOfLeft = (left.getComments() == null) ? 0 : left.getComments().size();
            int numberOfRight = (right.getComments() == null) ? 0 : right.getComments().size();
            return (numberOfLeft > numberOfRight) ? -1 : 1;
        }
    }
}
