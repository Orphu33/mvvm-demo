package com.serengetitech.databindingbaseapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.serengetitech.databindingbaseapp.databinding.ActivityGithubRepositoryBinding;
import com.serengetitech.databindingbaseapp.model.Repo;
import com.serengetitech.databindingbaseapp.network.resource.Resource;
import com.serengetitech.databindingbaseapp.viewmodels.GithubRepositoryActivityViewModel;

public class GithubRepositoryActivity extends AppCompatActivity {
    ActivityGithubRepositoryBinding binding;
    GithubRepositoryActivityViewModel viewModel;
    GitHubActivityClickHandlers handlers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_repository);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_github_repository);

        handlers = new GitHubActivityClickHandlers();
        binding.setHandlers(handlers);

        viewModel = ViewModelProviders.of(this).get(GithubRepositoryActivityViewModel.class);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String login = getIntent().getStringExtra(MainActivity.OWNER_LOGIN);
        String repoName = getIntent().getStringExtra(MainActivity.REPO_FULL_NAME);

        binding.contentGithub.repoName.setText(repoName);

        getData(login, repoName);

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

    private void getData(String login, String repoName) {
        viewModel.getRepository(login, repoName).observe(this, repo -> {
            Log.i("STATUS", repo.status.name());
            updateUI(repo);

        });
    }

    private void updateUI(Resource<Repo> repo) {
        switch (repo.status) {
            case SUCCESS:
                binding.contentGithub.progressBar.setVisibility(View.GONE);
                binding.setRepo(repo.data);
                Glide.with(this)
                        .load(repo.data.getOwner().getAvatarURL())
                        .into(binding.contentGithub.profile);
                break;
            case LOADING:
                binding.contentGithub.progressBar.setVisibility(View.VISIBLE);
                break;
            case ERROR:
                break;
        }
    }

    public class GitHubActivityClickHandlers {

        public void openExternalLink(View v) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(binding.getRepo().getUrl()));
            startActivity(browserIntent);
        }

        public void onProfileSelected(View v) {
            Intent intent = new Intent(GithubRepositoryActivity.this, OwnerActivity.class);
            intent.putExtra(MainActivity.AVATAR_URL, binding.getRepo().getOwner().getAvatarURL());
            intent.putExtra(MainActivity.OWNER_LOGIN, binding.getRepo().getOwner().getLogin());

            ImageView iv = (ImageView) v;

            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(GithubRepositoryActivity.this, iv, getString(R.string.transition_profile_pic));

            startActivity(intent, options.toBundle());
            finish();
        }
    }

}
