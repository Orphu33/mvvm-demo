package com.serengetitech.databindingbaseapp;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.serengetitech.databindingbaseapp.adapters.RepoListSmallAdapter;
import com.serengetitech.databindingbaseapp.databinding.ActivityOwnerBinding;
import com.serengetitech.databindingbaseapp.model.Owner;
import com.serengetitech.databindingbaseapp.model.Repo;
import com.serengetitech.databindingbaseapp.network.resource.Resource;
import com.serengetitech.databindingbaseapp.repositories.GitHubRepoRepository;
import com.serengetitech.databindingbaseapp.viewmodels.OwnerActivityViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class OwnerActivity extends AppCompatActivity {
    private ActivityOwnerBinding activityOwnerBinding;
    private OwnerActivityViewModel viewModel;
    private OwnerActivityClickHandlers handlers;
    private RepoListSmallAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);

        activityOwnerBinding = DataBindingUtil.setContentView(this, R.layout.activity_owner);

        viewModel = ViewModelProviders.of(this).get(OwnerActivityViewModel.class);

        setSupportActionBar(activityOwnerBinding.toolbar);

        String url = getIntent().getStringExtra(MainActivity.AVATAR_URL);
        String login = getIntent().getStringExtra(MainActivity.OWNER_LOGIN);

        Glide.with(this)
                .load(url)
                .into(activityOwnerBinding.contentOwner.imageView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getData(login);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getData(String login) {
        viewModel.getOwner(login).observe(this, o -> {
            Log.i("STATUS", o.status.name());
            activityOwnerBinding.setOwner(o.data);

        });

        viewModel.getOwnerRepos(login).observe(this, repos -> {
            if (repos.status == Resource.Status.LOADING) {
                activityOwnerBinding.contentOwner.progressBar.setVisibility(View.VISIBLE);
            } else {
                activityOwnerBinding.contentOwner.progressBar.setVisibility(View.GONE);
                setAdapter(repos.data);
            }
        });
    }

    private void setAdapter(List<Repo> repos) {
        RecyclerView recycler = activityOwnerBinding.contentOwner.repoList;

        LinearLayoutManager llm = new LinearLayoutManager(this);

        DividerItemDecoration divider = new DividerItemDecoration(recycler.getContext(),
                llm.getOrientation());

        recycler.setLayoutManager(llm);
        recycler.setHasFixedSize(true);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.addItemDecoration(divider);
        adapter = new RepoListSmallAdapter();
        recycler.setAdapter(adapter);
        recycler.setItemAnimator(new DefaultItemAnimator());
        adapter.setRepos((ArrayList<Repo>) repos);

        adapter.setListener((repo, v) -> {
            Intent intent = new Intent(OwnerActivity.this, GithubRepositoryActivity.class);
            intent.putExtra(MainActivity.REPO_FULL_NAME, repo.getName());
            intent.putExtra(MainActivity.OWNER_LOGIN, repo.getOwner().getLogin());
            startActivity(intent);
            finish();
        });
    }

    public class OwnerActivityClickHandlers {

        public void onFabClicked(View view) {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

}
