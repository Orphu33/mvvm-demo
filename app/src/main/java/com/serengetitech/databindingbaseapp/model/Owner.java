package com.serengetitech.databindingbaseapp.model;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.library.baseAdapters.BR;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.SerializedName;
import com.serengetitech.databindingbaseapp.R;

import java.util.Date;

public class Owner extends BaseObservable {
    private int id;
    private String login;
    private String name;

    @SerializedName("public_repos")
    private int publicRepos;

    @SerializedName("public_gists")
    private int publicGists;

    @SerializedName("created_at")
    private Date created;

    @SerializedName("updated_at")
    private Date updated;

    @SerializedName("avatar_url")
    private String avatarURL;

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
        notifyPropertyChanged(BR.login);
    }

    @Bindable
    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Bindable
    public int getPublicRepos() {
        return publicRepos;
    }

    public void setPublicRepos(int publicRepos) {
        this.publicRepos = publicRepos;
        notifyPropertyChanged(BR.updated);
    }

    @Bindable
    public int getPublicGists() {
        return publicGists;
    }

    public void setPublicGists(int publicGists) {
        this.publicGists = publicGists;
        notifyPropertyChanged(BR.updated);
    }

    @Bindable
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
        notifyPropertyChanged(BR.updated);
    }

    @Bindable
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
        notifyPropertyChanged(BR.updated);
    }

    @BindingAdapter("profileImage")
    public static void loadImage(ImageView view, String imageUrl) {
        RequestOptions options = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.image_error);

        Glide.with(view.getContext())
                .load(imageUrl)
                .apply(options)
                .into(view);
    }
}
