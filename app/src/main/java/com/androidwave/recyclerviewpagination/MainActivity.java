package com.androidwave.recyclerviewpagination;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private PostRecyclerAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    public static final int PAGE_START = 1;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    int itemCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        swipeRefresh.setOnRefreshListener(this);

        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PostRecyclerAdapter(new ArrayList<PostItem>());
        mRecyclerView.setAdapter(mAdapter);
        preparedListItem();
        mRecyclerView.addOnScrollListener(new HidingScrollListener(mLayoutManager) {
            @Override
            public void onHide() {
                mAdapter.removeHeader();
            }

            @Override
            public void onShow() {
                mAdapter.addHeader();
            }

            @Override
            public void onLoadMore(int current_page) {
                if (!isLastPage) {
                    preparedListItem();
                }

            }
        });
    }

    private void preparedListItem() {
        final ArrayList<PostItem> items = new ArrayList<>();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    itemCount++;
                    PostItem postItem = new PostItem();
                    postItem.setTitle("Fake Android Apps With Over 50,000 " + itemCount);
                    postItem.setDescription("Fake Android Apps With Over 50,000 Installations Found on Google Play, Quick Heal Claims");
                    items.add(postItem);

                }
                mAdapter.setItems(items);
                swipeRefresh.setRefreshing(false);

            }
        }, 1500);
    }

    @Override
    public void onRefresh() {
        itemCount = 0;
        currentPage = PAGE_START;
        isLastPage = false;
        mAdapter.clear();
        preparedListItem();


    }
}
