package com.serengetitech.databindingbaseapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.serengetitech.databindingbaseapp.model.Owner;
import com.serengetitech.databindingbaseapp.model.Repo;
import com.serengetitech.databindingbaseapp.network.resource.Resource;
import com.serengetitech.databindingbaseapp.repositories.GitHubRepoRepository;

import java.util.List;

public class OwnerActivityViewModel extends AndroidViewModel {
    private GitHubRepoRepository repoRepository;
    private LiveData<Owner> owner;

    public OwnerActivityViewModel(@NonNull Application application) {
        super(application);
        repoRepository = new GitHubRepoRepository(application);
    }

    public LiveData<Resource<Owner>> getOwner(String login) {
        return repoRepository.getOwner(login);
    }

    public MutableLiveData<Resource<List<Repo>>> getOwnerRepos(String login) { return repoRepository.getOwnerRepositoryList(login); }
}
