package com.serengetitech.databindingbaseapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.serengetitech.databindingbaseapp.R;
import com.serengetitech.databindingbaseapp.databinding.RepoListSmallItemBinding;
import com.serengetitech.databindingbaseapp.model.Repo;

import java.util.ArrayList;

public class RepoListSmallAdapter extends RecyclerView.Adapter<RepoListSmallAdapter.RepoViewHolder> {
    private ArrayList<Repo> repos;
    private OnItemClickListener listener;

    @NonNull
    @Override
    public RepoListSmallAdapter.RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RepoListSmallItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.repo_list_small_item, parent, false);
        return new RepoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        Repo repo = repos.get(position);
        holder.mBinding.setRepo(repo);
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public void setRepos(ArrayList<Repo> repos) {
        this.repos = repos;
    }

    public class RepoViewHolder extends RecyclerView.ViewHolder {
        private RepoListSmallItemBinding mBinding;

        public RepoViewHolder(@NonNull RepoListSmallItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.repoName.setOnClickListener(v -> {
                int pos = getAdapterPosition();

                if (listener != null && pos != RecyclerView.NO_POSITION) {
                    listener.onItemClick(repos.get(pos), v);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Repo repo, View v);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
