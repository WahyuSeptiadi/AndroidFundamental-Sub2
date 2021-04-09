package com.kevin.github.view.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.kevin.github.constant.BaseConst;
import com.kevin.github.R;
import com.kevin.github.view.followers.FollowersFragment;
import com.kevin.github.view.following.FollowingFragment;
import com.kevin.github.view.search.SearchActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity {

    private DetailViewModel detailViewModel;
    private CircleImageView imgAvatarDetail;
    private TextView username, name, repository, follower, following, company, location;

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //setComponent
        ImageView btnBack = findViewById(R.id.btnBack);
        imgAvatarDetail = findViewById(R.id.imgAvatar);
        username = findViewById(R.id.usernameValue);
        name = findViewById(R.id.nameValue);
        repository = findViewById(R.id.repositoryValue);
        follower = findViewById(R.id.followerValue);
        following = findViewById(R.id.followingValue);
        company = findViewById(R.id.companyValue);
        location = findViewById(R.id.locationValue);

        // for tabLayout
        mContext = getBaseContext();

        SectionPagerAdapter mSection = new SectionPagerAdapter(getSupportFragmentManager());
        ViewPager mViewPager = findViewById(R.id.vp_profile);
        mViewPager.setAdapter(mSection);

        TabLayout mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        String username = getIntent().getStringExtra(BaseConst.DATA_KEY);
        detailViewModel = new ViewModelProvider(this,
                          new ViewModelProvider.NewInstanceFactory()).get(DetailViewModel.class);
        detailViewModel.setDetailUser(username);

        getData();

        btnBack.setOnClickListener(v -> {
            Intent toSearch = new Intent(DetailActivity.this, SearchActivity.class);
            startActivity(toSearch);

            finish();
        });
    }

    private void getData() {
        detailViewModel.getDetailUser().observe(this, git_user -> {
            Picasso.get().load(git_user.getAvatar_url()).into(imgAvatarDetail);
            username.setText(git_user.getLogin());
            repository.setText(String.valueOf(git_user.getPublic_repos()));
            follower.setText(String.valueOf(git_user.getFollowers()));
            following.setText(String.valueOf(git_user.getFollowing()));

            if(git_user.getName() == null){
                name.setText(R.string.str_null);
            }else{
                name.setText(git_user.getName());
            }

            if(git_user.getLocation() == null && git_user.getCompany() == null){
                location.setText(R.string.str_null);
                company.setText(R.string.str_null);
            }else if (git_user.getCompany() == null || git_user.getLocation() == null){
                if (git_user.getCompany() == null){
                    company.setText(R.string.str_null);
                    location.setText(git_user.getLocation());
                }else if (git_user.getLocation() == null){
                    location.setText(R.string.str_null);
                    company.setText(git_user.getCompany());
                }
            }else{
                company.setText(git_user.getCompany());
                location.setText(git_user.getLocation());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.tab_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public static class SectionPagerAdapter extends FragmentPagerAdapter {

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position){
                case 0 :
                    fragment = new FollowersFragment();
                    break;
                case 1 :
                    fragment = new FollowingFragment();
                    break;
            }
            assert fragment != null;
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 :
                    return mContext.getResources().getString(R.string.tab_followers);
                case 1 :
                    return mContext.getResources().getString(R.string.tab_following);
            }
            return null;
        }
    }
}