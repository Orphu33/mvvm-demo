package com.serengetitech.databindingbaseapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.serengetitech.databindingbaseapp.model.Repo;
import com.serengetitech.databindingbaseapp.network.resource.Resource;
import com.serengetitech.databindingbaseapp.repositories.GitHubRepoRepository;

public class GithubRepositoryActivityViewModel extends AndroidViewModel {
    private GitHubRepoRepository repoRepository;
    private LiveData<Repo> repo;

    public GithubRepositoryActivityViewModel(@NonNull Application application) {
        super(application);
        repoRepository = new GitHubRepoRepository(application);
    }

    public LiveData<Resource<Repo>> getRepository(String login, String repoName) {
        return repoRepository.getRepository(login, repoName);
    }
}
