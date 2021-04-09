package com.kevin.github.view.following;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kevin.github.R;
import com.kevin.github.constant.Base;

import java.util.Objects;

public class FollowingFragment extends Fragment {

    private RecyclerView recyclerView;
    private FollowingListsAdapter adapter;
    private FollowingViewModel followingViewModel;
    private ProgressBar progressBar;

    private TextView message;

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

        getDataFollowing();

        return view;
    }

    private void getDataFollowing() {
        followingViewModel.getFollowingData().observe(Objects.requireNonNull(getActivity()), git_user -> {
            if (git_user.isEmpty()) {
                adapter.clearList(git_user);

                progressBar.setVisibility(View.GONE);
                message.setText(R.string.str_followingnull);
                message.setVisibility(View.VISIBLE);
            } else {
                adapter.setData(git_user);
                recyclerView.setAdapter(adapter);

                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.smoothScrollToPosition(0);
        adapter = new FollowingListsAdapter(getContext());
        adapter.notifyDataSetChanged();
    }
}