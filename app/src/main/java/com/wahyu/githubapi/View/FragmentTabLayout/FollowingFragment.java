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
import com.wahyu.githubapi.ViewModel.FollowingViewModel;

import java.util.Objects;

public class FollowingFragment extends Fragment {

    private RecyclerView recyclerView;
    private ListAdapter adapter;
    private FollowingViewModel followingViewModel;
    private ProgressBar progressBar;

    //buat cek
    TextView message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following, container, false);

        recyclerView = view.findViewById(R.id.rv_following);
        progressBar = view.findViewById(R.id.progress_circular_following);
        message = view.findViewById(R.id.tv_message_following);

        setRecyclerView();

        String username = Objects.requireNonNull(getActivity()).getIntent().getStringExtra(Base.DATA_KEY);
        followingViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel.class);

        //setData
        followingViewModel.setFollowingData(username);

        getData();

        return view;
    }

    private void getData(){
        followingViewModel.getFollowingData().observe(Objects.requireNonNull(getActivity()), git_user -> {
            if (git_user.isEmpty()){
                adapter.clearList(git_user);

                progressBar.setVisibility(View.GONE);
                message.setText(R.string.str_followingnull);
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