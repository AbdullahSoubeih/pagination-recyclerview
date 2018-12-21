package com.androidwave.recyclerviewpagination;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;


    private Callback mCallback;
    private List<PostItem> mPostItems;

    public PostRecyclerAdapter(List<PostItem> postItems) {
        this.mPostItems = postItems;
    }

    public interface Callback {
        void onRepoEmptyViewRetryClick();
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false));
            case VIEW_TYPE_EMPTY:
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (mPostItems != null && mPostItems.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public int getItemCount() {
        if (mPostItems != null && mPostItems.size() > 0) {
            return mPostItems.size();
        } else {
            return 1;
        }
    }

    public void addItems(List<PostItem> followers) {
        mPostItems = followers;
        notifyDataSetChanged();
    }

    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.textViewTitle)
        TextView textViewTitle;
        @BindView(R.id.textViewDescription)
        TextView textViewDescription;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {

        }

        public void onBind(int position) {
            super.onBind(position);
            PostItem item = mPostItems.get(position);


            textViewTitle.setText(item.getTitle());
            textViewDescription.setText(item.getDescription());
        }
    }

    public class EmptyViewHolder extends BaseViewHolder {

        @BindView(R.id.progressBar)
        ProgressBar mProgressBar;


        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void clear() {

        }


    }

}
