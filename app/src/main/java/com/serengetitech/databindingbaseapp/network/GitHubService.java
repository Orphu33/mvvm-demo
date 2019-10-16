package com.serengetitech.databindingbaseapp.network;

import com.serengetitech.databindingbaseapp.model.BaseResponse;
import com.serengetitech.databindingbaseapp.model.Owner;
import com.serengetitech.databindingbaseapp.model.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubService {

    @GET("users/{user}/repos")
    Call<List<Repo>> listUserRepos(@Path("user") String login);

    @GET("users/{user}")
    Call<Owner> getOwner(@Path("user") String user);

    @GET("repos/{owner}/{repoName}")
    Call<Repo> getRepository(@Path("owner") String owner, @Path("repoName") String name);

    @GET("search/repositories")
    Call<BaseResponse<List<Repo>>> searchRepos(@Query("q") String query);
}
