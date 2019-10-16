package com.serengetitech.databindingbaseapp.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Repo extends BaseObservable {
    private int id;
    private String name;
    private String language;
    private String description;

    @SerializedName("html_url")
    private String url;

    private boolean fork;
    private Owner owner;

    @SerializedName("created_at")
    private Date created;

    @SerializedName("updated_at")
    private Date updated;

    @SerializedName("watchers_count")
    private int watchers;

    @SerializedName("forks_count")
    private int forks;

    @SerializedName("open_issues_count")
    private int issues;

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
        notifyPropertyChanged(BR.language);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(BR.url);
    }

    @Bindable
    public boolean isFork() {
        return fork;
    }

    public void setFork(boolean fork) {
        this.fork = fork;
    }

    @Bindable
    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
        notifyPropertyChanged(BR.owner);
    }

    @Bindable
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
        notifyPropertyChanged(BR.created);
    }

    @Bindable
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
        notifyPropertyChanged(BR.updated);
    }

    @Bindable
    public int getWatchers() {
        return watchers;
    }

    public void setWatchers(int watchers) {
        this.watchers = watchers;
        notifyPropertyChanged(BR.watchers);
    }

    @Bindable
    public int getForks() {
        return forks;
    }

    public void setForks(int forks) {
        this.forks = forks;
        notifyPropertyChanged(BR.forks);
    }

    @Bindable
    public int getIssues() {
        return issues;
    }

    public void setIssues(int issues) {
        this.issues = issues;
        notifyPropertyChanged(BR.issues);
    }
}
