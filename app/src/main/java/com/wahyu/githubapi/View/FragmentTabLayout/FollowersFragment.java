package com.wahyu.githubapi.View.FragmentTabLayout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wahyu.githubapi.adapter.ListAdapter;
import com.wahyu.githubapi.constant.Base;
import com.wahyu.githubapi.R;
import com.wahyu.githubapi.ViewModel.FollowersViewModel;

import java.util.Objects;

public class FollowersFragment extends Fragment {

    private RecyclerView recyclerView;
    private ListAdapter adapter;
    private FollowersViewModel followersViewModel;
    private ProgressBar progressBar;

    //buat cek
    TextView message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followers, container, false);

        recyclerView = view.findViewById(R.id.rv_followers);
        progressBar = view.findViewById(R.id.progress_circular_followers);
        message = view.findViewById(R.id.tv_message_followers);

        setRecyclerView();

        String username = Objects.requireNonNull(getActivity()).getIntent().getStringExtra(Base.DATA_KEY);
        followersViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel.class);

        //setData
        followersViewModel.setFollowersData(username);

        getData();

        return view;
    }

    private void getData(){
        followersViewModel.getFollowersData().observe((Objects.requireNonNull(getActivity())), git_user -> {
            if (git_user.isEmpty()){
                adapter.clearList(git_user);

                progressBar.setVisibility(View.GONE);
                message.setText(R.string.str_followersnull);
                message.setVisibility(View.VISIBLE);
            }else{
                adapter.setData(git_user);
                recyclerView.setAdapter(adapter);

                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void setRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.smoothScrollToPosition(0);
        adapter = new ListAdapter(getContext());
        adapter.notifyDataSetChanged();
    }
}