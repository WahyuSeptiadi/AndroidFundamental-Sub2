package com.kevin.github.view.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kevin.github.R;
import com.kevin.github.view.followers.FollowerListsAdapter;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FollowerListsAdapter adapter;
    private EditText et_username;
    private SearchViewModel searchViewModel;
    private ProgressBar progressBar;

    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.rv_search);
        ImageView btnSearch = findViewById(R.id.btnCari);
        et_username = findViewById(R.id.editTextSearch);
        ImageView cekProfile = findViewById(R.id.imgProfile);
        message = findViewById(R.id.tv_message);

        progressBar = findViewById(R.id.progress_circular);
        progressBar.setProgress(0);

        setRecyclerView();


        searchViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(SearchViewModel.class);

        if (savedInstanceState != null) {
            String data = savedInstanceState.getString("key");

            searchViewModel.setSearchData(data);
            getData();
            progressBar.setVisibility(View.GONE);
        }

        btnSearch.setOnClickListener(v -> {
            if (TextUtils.isEmpty(et_username.getText().toString())) {
                Toast.makeText(this, getResources().getString(R.string.toast_enter_key), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            } else {
                Toast.makeText(this, getResources().getString(R.string.toast_searching), Toast.LENGTH_SHORT).show();
                searchViewModel.setSearchData(et_username.getText().toString());
                getData();
            }

            // autohide after search keyword
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        });

        cekProfile.setOnClickListener(v -> {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("key", et_username.getText().toString());
    }

    private void getData() {
        progressBar.setVisibility(View.VISIBLE);
        message.setVisibility(View.INVISIBLE);
        searchViewModel.getSearchData().observe(this, git_user -> {
            if (git_user.getTotal_count() > 0) {
                adapter.setData(git_user.getItems());

                recyclerView.setAdapter(adapter);

                message.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.GONE);
            } else {
                adapter.clearList(git_user.getItems());

                progressBar.setVisibility(View.GONE);
                message.setText(R.string.str_message);
                message.setVisibility(View.VISIBLE);
            }
            et_username.setText("");
        });
    }

    private void setRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.smoothScrollToPosition(0);
        adapter = new FollowerListsAdapter(getBaseContext());
        adapter.notifyDataSetChanged();
    }
}