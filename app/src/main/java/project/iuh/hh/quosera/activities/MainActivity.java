package project.iuh.hh.quosera.activities;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.parse.Parse;

import java.util.ArrayList;
import java.util.zip.Inflater;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import project.iuh.hh.quosera.R;
import project.iuh.hh.quosera.entites.Post;
import project.iuh.hh.quosera.entites.User;
import project.iuh.hh.quosera.fragment.NewFragment;
import project.iuh.hh.quosera.information.Information;
import project.iuh.hh.quosera.myadapter.ListViewAdapter;
import project.iuh.hh.quosera.parse.ParsePostHelper;
import project.iuh.hh.quosera.parse.ParseUserHelper;
import project.iuh.hh.quosera.utils.SavingArrayList;
import project.iuh.hh.quosera.utils.Utils;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MaterialTabListener {

    public static final String PREF_USER_FIRST_TIME = "user_first_time";
    boolean isUserFirstTime;
    TextView tv_nav_header_username, tv_nav_geader_email;
    public static Toolbar toolbar;
    public static MaterialTabHost tabHost;
    FloatingActionButton fab;
    User user = new User();
    ViewPager viewPager;
    ViewPagerAdapter androidAdapter;
    ImageView avatar;

    private ProgressDialog progressDialog;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);
        getControls();

    //    fab.setBackgroundColor(getResources().getColor(vivid_orange));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PostActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Source",MainActivity.class.getName());
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int tabpos) {
                tabHost.setSelectedNavigationItem(tabpos);
            }
        });

        viewPager.setOffscreenPageLimit(3);

        for(int i = 0; i < androidAdapter.getCount(); i++)
        {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(androidAdapter.getPageTitle(i))
                            .setTabListener(this)

            );
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        ParseUserHelper puh = new ParseUserHelper();
        user = puh.getObjectById(Information.email);
        Log.w("EMAIL = =", Information.email);
        setTextNavHeader();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void getControls() {
        //toolbar
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //float button
        fab = (FloatingActionButton) findViewById(R.id.fab);
        //tab host
        tabHost = (MaterialTabHost) this.findViewById(R.id.tabHost);
        viewPager = (ViewPager) this.findViewById(R.id.viewPager);
    //    tabHost.setGravity();
        //adapter view
        androidAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(androidAdapter);
        View nav_header = getLayoutInflater().inflate(R.layout.nav_header_main, null);
    }

    private void setTextNavHeader() {
        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        View header = nav_view.getHeaderView(0);
        tv_nav_header_username = (TextView) header.findViewById(R.id.nav_header_username);
        tv_nav_geader_email = (TextView) header.findViewById(R.id.nav_header_mail);
        avatar = (ImageView)header.findViewById(R.id.avatar);
        ParseUserHelper pah = new ParseUserHelper();
        user = pah.getObjectById(Information.email);
        tv_nav_header_username.setText(user.getFirstname() + " " +  user.getLastname());
        tv_nav_geader_email.setText(user.getEmail());

        if(user.getGender() == true)
            avatar.setImageResource(R.drawable.ic_avatar_male1);
        else
            avatar.setImageResource(R.drawable.ic_avatar_female1);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            public boolean doSearch(String s)
            {
                if (s.equals(""))
                {
                    fragment.listViewAdapterNew = new ListViewAdapter(MainActivity.this,R.layout.row_post, SavingArrayList.arrNew);
                    fragment.gridView.setAdapter(fragment.listViewAdapterNew);
                    return false;
                }
                ArrayList<Post> arrListPostNew = new ArrayList<Post>();
                for (Post post:SavingArrayList.arrNew)
                {
                    if (post.getPosttitle().equals(s))
                        arrListPostNew.add(post);
                    else
                    {
                        for (String str:post.getTags())
                        {
                            if (str.equals(s)) {
                                arrListPostNew.add(post);
                                break;
                            }
                        }
                    }
                }
                fragment.listViewAdapterNew = new ListViewAdapter(MainActivity.this,R.layout.row_post,arrListPostNew);
                fragment.gridView.setAdapter(fragment.listViewAdapterNew);
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String s) {
                return doSearch(s);
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return doSearch(s);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("email",Information.email);
            intent.putExtra("bundle",bundle);
            startActivity(intent);
        } else if (id == R.id.nav_tools) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            this.finish();
        } else if (id == R.id.nav_exit) {
            System.exit(0);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTabSelected(MaterialTab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }
    NewFragment fragment = null;
    private class ViewPagerAdapter extends FragmentStatePagerAdapter{
        // view pager adapter

            public ViewPagerAdapter(FragmentManager fragmentManager) {
                super(fragmentManager);
            }

        public Fragment getItem(int num) {
                switch (num)
                {
                    case 0:
                        fragment = new NewFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("type", 0);
                        fragment.setArguments(bundle);
                        //ko debug trong doinbackground duoc nhi ?
                        return fragment;
                    case 1:
                        fragment = new NewFragment();
                        bundle = new Bundle();
                        bundle.putInt("type", 1);
                        fragment.setArguments(bundle);
                        return fragment;
                    case 2:
                        fragment = new NewFragment();
                        bundle = new Bundle();
                        bundle.putInt("type",2);
                        fragment.setArguments(bundle);
                        return fragment;
                    default:
                        return null;

                }
        }
        @Override
        public int getCount() {
            return 3 ;
        }

        @Override
        public CharSequence getPageTitle(int tabposition) {
            switch (tabposition)
            {
                case 0:
                    return "New";
                case 1:
                    return "Hot";
                case 2:
                    return "Following";
                default:
                    break;
            }
            return null;
        }
    }





}






















