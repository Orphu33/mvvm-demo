package com.serengetitech.databindingbaseapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.serengetitech.databindingbaseapp.R;
import com.serengetitech.databindingbaseapp.databinding.RepoListItemBinding;
import com.serengetitech.databindingbaseapp.model.Owner;
import com.serengetitech.databindingbaseapp.model.Repo;

import java.util.ArrayList;

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.RepositoryViewHolder> {
    private ArrayList<Repo> repos;
    private OnItemClickListener listener;

    @NonNull
    @Override
    public RepoListAdapter.RepositoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RepoListItemBinding repoListItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.repo_list_item, parent, false);
        return new RepositoryViewHolder(repoListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoryViewHolder holder, int position) {
        Repo repo = repos.get(position);
        holder.binding.setRepo(repo);
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public void setRepos(ArrayList<Repo> repos) {
        this.repos = repos;
        notifyItemRangeChanged(0, getItemCount());
    }

    public class RepositoryViewHolder extends RecyclerView.ViewHolder {
        private RepoListItemBinding binding;


        public RepositoryViewHolder(@NonNull RepoListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.tableLayout.setOnClickListener(v -> {
                int position = getAdapterPosition();

                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(repos.get(position), v);
                }
            });

            binding.ownerImage.setOnClickListener(v -> {
                int position = getAdapterPosition();

                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onImageClick(repos.get(position).getOwner(), v);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Repo repo, View v);
        void onImageClick(Owner owner, View v);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
