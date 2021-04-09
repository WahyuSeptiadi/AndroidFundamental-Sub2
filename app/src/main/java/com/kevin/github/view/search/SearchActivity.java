package com.kevin.github.view.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
    private EditText etUsername;
    private SearchViewModel searchViewModel;
    private ProgressBar progressBar;

    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.rv_search);
        ImageView btnSearch = findViewById(R.id.btnCari);
        etUsername = findViewById(R.id.editTextSearch);
        ImageView changeConfig = findViewById(R.id.imgSetting);
        message = findViewById(R.id.tv_message);

        progressBar = findViewById(R.id.progress_circular);
        progressBar.setProgress(0);

        setRecyclerView();

        searchViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(SearchViewModel.class);

        if (savedInstanceState != null) {
            String data = savedInstanceState.getString("key");

            searchViewModel.setSearchData(data);
            getDataUsers();
            progressBar.setVisibility(View.GONE);
        }

        btnSearch.setOnClickListener(v -> {
            if (TextUtils.isEmpty(etUsername.getText().toString())) {
                Toast.makeText(this, getResources().getString(R.string.toast_enter_key), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            } else {
                Toast.makeText(this, getResources().getString(R.string.toast_searching), Toast.LENGTH_SHORT).show();
                searchViewModel.setSearchData(etUsername.getText().toString());
                getDataUsers();
            }

            // auto hide after search keyword
            hideSoftKeyboard();
        });

        etUsername.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String username = etUsername.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(this, getResources().getString(R.string.toast_enter_key), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(this, getResources().getString(R.string.toast_searching), Toast.LENGTH_SHORT).show();
                    searchViewModel.setSearchData(username);
                    getDataUsers();
                }

                hideSoftKeyboard();
                return true;
            }
            return false;
        });

        changeConfig.setOnClickListener(v -> {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        });
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("key", etUsername.getText().toString());
    }

    private void getDataUsers() {
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
            etUsername.setText("");
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