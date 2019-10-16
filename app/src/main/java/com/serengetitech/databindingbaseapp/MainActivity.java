package com.serengetitech.databindingbaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.serengetitech.databindingbaseapp.adapters.RepoListAdapter;
import com.serengetitech.databindingbaseapp.databinding.ActivityMainBinding;
import com.serengetitech.databindingbaseapp.model.Owner;
import com.serengetitech.databindingbaseapp.model.Repo;
import com.serengetitech.databindingbaseapp.network.resource.Resource;
import com.serengetitech.databindingbaseapp.viewmodels.MainActivityViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainActivityViewModel mainActivityViewModel;
    private ActivityMainBinding activityMainBinding;
    private MainActivityClickHandlers handlers;
    private RecyclerView reposRecyclerView;
    private RepoListAdapter reposAdapter;
    private ArrayList<Repo> reposList;

    public final static String AVATAR_URL = "avatarUrl";
    public final static String OWNER_LOGIN = "login";
    public final static String REPO_FULL_NAME = "full name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(activityMainBinding.toolbar);

        handlers = new MainActivityClickHandlers();
        activityMainBinding.setClickHandler(handlers);

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_main, menu);

        MenuItem myActionMenuItem = menu.findItem( R.id.searchBar);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                activityMainBinding.contentMain.sortSpinner.setSelection(0);
                searchGithub(query);
                if( ! searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
                return false;
            }
        });
        return true;
    }

    private void searchGithub(String query) {
        mainActivityViewModel.setReposList(Resource.loading(null));

        mainActivityViewModel.getReposList(query).observe(MainActivity.this, listResource -> {
            updateUI(listResource);
            Log.i("STATUS", listResource.status.name());
        });
    }

    private void updateUI(Resource<List<Repo>> repos) {
        switch (repos.status) {
            case SUCCESS:
                activityMainBinding.progressBar.setVisibility(View.GONE);
                activityMainBinding.contentMain.sortSpinner.setVisibility(View.VISIBLE);
                reposList = (ArrayList<Repo>) repos.data;
                loadRecyclerView();
                setSpinnerListener();
                break;
            case ERROR:
                activityMainBinding.progressBar.setVisibility(View.GONE);
                break;
            case LOADING:
                activityMainBinding.progressBar.setVisibility(View.VISIBLE);
                activityMainBinding.contentMain.sortSpinner.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.searchBar) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setSpinnerListener() {
            activityMainBinding.contentMain.sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    activityMainBinding.contentMain.sortSpinner.setSelection(position);
                    switch (position) {
                        case 1:
                            Collections.sort(reposList, (o1, o2) -> o2.getWatchers() - o1.getWatchers());
                            break;
                        case 2:
                            Collections.sort(reposList, (o1, o2) -> o2.getForks() - o1.getForks());
                            break;
                        case 3:
                            Collections.sort(reposList, (o1, o2) -> o2.getIssues() - o1.getIssues());
                            break;
                        default:
                            break;
                    }
                    mainActivityViewModel.setReposList(Resource.success(reposList));
                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });


    }

    public void loadRecyclerView() {
        reposRecyclerView = activityMainBinding.contentMain.repoRecycler;

        LinearLayoutManager llm = new LinearLayoutManager(this);

        DividerItemDecoration divider = new DividerItemDecoration(reposRecyclerView.getContext(),
                llm.getOrientation());

        reposRecyclerView.setLayoutManager(llm);
        reposRecyclerView.setHasFixedSize(true);
        reposRecyclerView.setItemAnimator(new DefaultItemAnimator());
        reposRecyclerView.addItemDecoration(divider);
        reposAdapter = new RepoListAdapter();
        reposRecyclerView.setAdapter(reposAdapter);
        reposAdapter.setRepos(reposList);

        reposAdapter.setListener(new RepoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Repo repo, View v) {
                Intent intent = new Intent(MainActivity.this, GithubRepositoryActivity.class);
                // Pass data object in the bundle and populate details activity.
                intent.putExtra(MainActivity.REPO_FULL_NAME, repo.getName());
                intent.putExtra(MainActivity.OWNER_LOGIN, repo.getOwner().getLogin());
                startActivity(intent);
            }

            @Override
            public void onImageClick(Owner owner, View v) {
                Intent intent = new Intent(MainActivity.this, OwnerActivity.class);
                // Pass data object in the bundle and populate details activity.
                intent.putExtra(MainActivity.AVATAR_URL, owner.getAvatarURL());
                intent.putExtra(MainActivity.OWNER_LOGIN, owner.getLogin());
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MainActivity.this, v, getString(R.string.transition_profile_pic));
                startActivity(intent, options.toBundle());

            }
        });
    }

    public class MainActivityClickHandlers {

        public void onFabClicked(View view) {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}
