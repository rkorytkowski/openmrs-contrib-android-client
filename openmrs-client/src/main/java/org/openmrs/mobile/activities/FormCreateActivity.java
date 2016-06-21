
/*Circular Viewpager indicator code obtained from:
http://www.androprogrammer.com/2015/06/view-pager-with-circular-indicator.html*/

package org.openmrs.mobile.activities;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import org.openmrs.mobile.R;
import org.openmrs.mobile.activities.fragments.FormPageFragment;
import org.openmrs.mobile.models.retrofit.form.Form;
import org.openmrs.mobile.models.retrofit.form.Page;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class FormCreateActivity extends ACBaseActivity implements ViewPager.OnPageChangeListener,View.OnClickListener{

    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;
    private Button btnNext, btnFinish;
    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    private Form form;
    List<Page> pagelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_create);

        Bundle b = getIntent().getExtras();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        if(b!=null)
        {
            String formname =(String) b.get("formname");
            getSupportActionBar().setTitle(formname);
        }

        btnNext = (Button) findViewById(R.id.btn_next);
        btnFinish = (Button) findViewById(R.id.btn_finish);
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);

        btnNext.setOnClickListener(this);
        btnFinish.setOnClickListener(this);


        String formjson=loadAssetTextAsString(FormCreateActivity.this,"openmrs-forms/vitals.json");
        //Toast.makeText(this,formjson,Toast.LENGTH_SHORT).show();

        Gson gson = new Gson();
        form=gson.fromJson(formjson,Form.class);
        pagelist = form.getPages();


        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        setUiPageViewController();


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
                break;

            case R.id.btn_finish:
                finish();
                break;
        }
    }


    String loadAssetTextAsString(Context context, String name) {
        BufferedReader in = null;
        try {
            StringBuilder buf = new StringBuilder();
            InputStream is = context.getAssets().open(name);
            in = new BufferedReader(new InputStreamReader(is));

            String str;
            boolean isFirst = true;
            while ( (str = in.readLine()) != null ) {
                if (isFirst)
                    isFirst = false;
                else
                    buf.append('\n');
                buf.append(str);
            }
            return buf.toString();
        } catch (IOException e) {
            //Log.e(TAG, "Error opening asset " + name);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    //Log.e(TAG, "Error closing asset " + name);
                }
            }
        }

        return null;
    }

    private void setUiPageViewController() {

        dotsCount = mSectionsPagerAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

        if(dotsCount==1)
        {
            btnNext.setVisibility(View.GONE);
            btnFinish.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.nonselecteditem_dot));
        }

        dots[position].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.selecteditem_dot));

        if (position + 1 == dotsCount) {
            btnNext.setVisibility(View.GONE);
            btnFinish.setVisibility(View.VISIBLE);
        } else {
            btnNext.setVisibility(View.VISIBLE);
            btnFinish.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FormPageFragment.newInstance(position + 1,pagelist.get(position));
        }

        @Override
        public int getCount() {

            return pagelist.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pagelist.get(position).getLabel();
        }
    }
}
