package project.iuh.hh.quosera.activities;

import android.animation.ArgbEvaluator;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import project.iuh.hh.quosera.R;
import project.iuh.hh.quosera.utils.Utils;


/*
* Create by HieuNT 18/3/2016
* */

public class IntroActivity extends AppCompatActivity {

    SectionsPagerAdapter mSectionsPaperAdapter;

    private ViewPager mViewPager;
    ImageButton btn_next;
    Button btn_skip, btn_finish;

    ImageView page1, page2, page3;
    ImageView [] indicators;
    CoordinatorLayout mCoordinator;
    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_FULLSCREEN);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }
//        hidestatusbar();

        setContentView(R.layout.activity_intro);

        //primary sections of the activity
        mSectionsPaperAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        btn_next = (ImageButton) findViewById(R.id.intro_btn_next);

        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP)
            btn_next.setImageDrawable(Utils.tintMyDrawable(ContextCompat.getDrawable(this, R.drawable.ic_chevron_right_24dp), Color.WHITE));

        btn_skip = (Button) findViewById(R.id.intro_btn_skip);
        btn_finish = (Button) findViewById(R.id.intro_btn_finish);
        page1 = (ImageView) findViewById(R.id.intro_indicator_0);
        page2 = (ImageView) findViewById(R.id.intro_indicator_1);
        page3 = (ImageView) findViewById(R.id.intro_indicator_2);

        mCoordinator = (CoordinatorLayout) findViewById(R.id.main_content);

        indicators = new ImageView[]{page1, page2, page3};

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPaperAdapter);
        mViewPager.setCurrentItem(page);
        updateIndicators(page);

        final int color1 = ContextCompat.getColor(this, R.color.sky_light);
        final int color2 = ContextCompat.getColor(this, R.color.green_blue_light);
        final int color3 = ContextCompat.getColor(this, R.color.iron);
        final int[] colorList = new int[]{color1, color2, color3};
        final ArgbEvaluator evaluator = new ArgbEvaluator();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int colorUpdate = (Integer) evaluator.evaluate(positionOffset, colorList[position], colorList[position == 2 ? position : position + 1]);
                mViewPager.setBackgroundColor(colorUpdate);
            }

            @Override
            public void onPageSelected(int position) {
                page = position;
                updateIndicators(page);
                switch (position) {
                    case 0:
                        mViewPager.setBackgroundColor(color1);
                        break;
                    case 1:
                        mViewPager.setBackgroundColor(color2);
                        break;
                    case 2:
                        mViewPager.setBackgroundColor(color3);
                        break;
                }
                btn_next.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
                btn_finish.setVisibility(position == 2 ? View.VISIBLE : View.GONE);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page += 1;
                mViewPager.setCurrentItem(page, true);
            }
        });

        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //Utils.saveSharedSetting(IntroActivity.this, MainActivity.PREF_USER_FIRST_TIME, "false");
            }
        });

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //  update 1st time pref
                Utils.saveSharedSetting(IntroActivity.this, MainActivity.PREF_USER_FIRST_TIME, "false");
            }
        });
    }

   /* private void hidestatusbar(){
        View decorView = getWindow().getDecorView();
        int uiOption  =View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOption);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
    }
*/
    private void CallLogin ()
    {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    private void updateIndicators(int page) {
        for(int i=0; i<indicators.length; i++) {
            indicators[i].setBackgroundResource(i == page ? R.drawable.indicator_selected : R.drawable.indicator_unselected);
        }
    }

    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        ImageView img;

        int[]bg = new int[]{R.drawable.img_intro1, R.drawable.img_intro2, R.drawable.img_intro3};

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            String[]s = getResources().getStringArray(R.array.intro_content);
            View rootView = inflater.inflate(R.layout.fragment_intro, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //    textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            textView.setText(s[getArguments().getInt(ARG_SECTION_NUMBER)-1]);
            img = (ImageView) rootView.findViewById(R.id.section_img);
            img.setBackgroundResource(bg[getArguments().getInt(ARG_SECTION_NUMBER) - 1]);
            
            img.setSelected(false);
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends  FragmentPagerAdapter{

            public SectionsPagerAdapter(FragmentManager fm) {
                super (fm);
            }

            @Override
            public Fragment getItem(int position) {
                // getItem is called to instantiate the fragment for the given page.
                // Return a PlaceholderFragment (defined as a static inner class below).
                return PlaceholderFragment.newInstance(position + 1);
            }

            @Override
            public int getCount() {
                // Show 3 total pages.
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "SECTION 1";
                    case 1:
                        return "SECTION 2";
                    case 2:
                        return "SECTION 3";
                }
                return null;
            }
        }
    }

