package com.serengetitech.databindingbaseapp.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.serengetitech.databindingbaseapp.model.BaseResponse;
import com.serengetitech.databindingbaseapp.model.Owner;
import com.serengetitech.databindingbaseapp.model.Repo;
import com.serengetitech.databindingbaseapp.network.Api;
import com.serengetitech.databindingbaseapp.network.GitHubService;
import com.serengetitech.databindingbaseapp.network.resource.Resource;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GitHubRepoRepository {
    private MutableLiveData<Resource<List<Repo>>> reposLiveData = new MutableLiveData<>();
    private MutableLiveData<Resource<Owner>> ownerLiveData = new MutableLiveData<>();
    private MutableLiveData<Resource<Repo>> repoLiveData = new MutableLiveData<>();
    private Application application;

    public GitHubRepoRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<Resource<List<Repo>>> getRepositoryList(String query) {
        GitHubService gitHubService = Api.getService();

        Call<BaseResponse<List<Repo>>> call = gitHubService.searchRepos(query);

        repoLiveData.setValue(Resource.loading(null));

        call.enqueue(new Callback<BaseResponse<List<Repo>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Repo>>> call, Response<BaseResponse<List<Repo>>> response) {
                List<Repo> repos = response.body().getItems();

                if (repos != null) {
                    reposLiveData.setValue(Resource.success(repos));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Repo>>> call, Throwable t) {
                reposLiveData.setValue(Resource.error("Could not fetch data", null));
            }
        });

        return reposLiveData;
    }

    public void setReposLiveData(Resource<List<Repo>> reposLiveData) {
        this.reposLiveData.postValue(reposLiveData);
    }

    public LiveData<Resource<Owner>> getOwner(String login) {
        GitHubService service = Api.getService();

        Call<Owner> call = service.getOwner(login);
        ownerLiveData.setValue(Resource.loading(null));

        call.enqueue(new Callback<Owner>() {
            @Override
            public void onResponse(Call<Owner> call, Response<Owner> response) {
                if(response.body() != null) ownerLiveData.setValue(Resource.success(response.body()));
            }

            @Override
            public void onFailure(Call<Owner> call, Throwable t) {
                Log.e("ERROR_GET_OWNER", t.getLocalizedMessage());
            }
        });

        return ownerLiveData;
    }

    public LiveData<Resource<Repo>> getRepository(String login, String repoName) {
        GitHubService service = Api.getService();

        Call<Repo> call = service.getRepository(login, repoName);
        repoLiveData.setValue(Resource.loading(null));

        call.enqueue(new Callback<Repo>() {
            @Override
            public void onResponse(Call<Repo> call, Response<Repo> response) {
                if(response.body() != null) repoLiveData.setValue(Resource.success(response.body()));
            }

            @Override
            public void onFailure(Call<Repo> call, Throwable t) {
                Log.e("ERROR_GET_REPO", t.getLocalizedMessage());
            }
        });

        return repoLiveData;
    }

    public MutableLiveData<Resource<List<Repo>>> getOwnerRepositoryList(String login) {
        GitHubService gitHubService = Api.getService();

        Call<List<Repo>> call = gitHubService.listUserRepos(login);

        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                List<Repo> repos = response.body();

                ArrayList<Repo> filter = filterRepos(repos);

                reposLiveData.setValue(Resource.success(filter));
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Log.e("ERROR_GET_REPOS", t.getLocalizedMessage());
            }
        });

        return reposLiveData;
    }

    private ArrayList<Repo> filterRepos(List<Repo> list) {
        ArrayList<Repo> filter = new ArrayList<>();
        for (Repo r : list) {
            if (!r.isFork()) filter.add(r);
        }
        return filter;
    }


}