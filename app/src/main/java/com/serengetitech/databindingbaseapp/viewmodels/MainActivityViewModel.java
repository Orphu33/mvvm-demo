package com.serengetitech.databindingbaseapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.serengetitech.databindingbaseapp.model.Repo;
import com.serengetitech.databindingbaseapp.network.resource.Resource;
import com.serengetitech.databindingbaseapp.repositories.GitHubRepoRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private GitHubRepoRepository repoRepository;
    private MutableLiveData<Resource<List<Repo>>> itemsList;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repoRepository = new GitHubRepoRepository(application);
    }

    public MutableLiveData<Resource<List<Repo>>> getReposList(String query) {
        return repoRepository.getRepositoryList(query);
    }

    public void setReposList(Resource<List<Repo>> list) {
        repoRepository.setReposLiveData(list);
    }
}
