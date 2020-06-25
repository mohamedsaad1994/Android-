package com.softxpert.SoftXpertTask.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.softxpert.SoftXpertTask.R;
import com.softxpert.SoftXpertTask.data.models.Car;
import com.softxpert.SoftXpertTask.utils.Utils;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.refresh);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        setData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void setData() {
        if (Utils.isNetworkConnected(this)) {
            CarsViewModel mViewModel = ViewModelProviders.of(this).get(CarsViewModel.class);
            final CarAdapter adapter = new CarAdapter(this);
            mViewModel.itemPagedList.observe(this, new Observer<PagedList<Car>>() {
                @Override
                public void onChanged(@Nullable PagedList<Car> items) {
                    adapter.submitList(items);
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(this, R.string.no_connection, Toast.LENGTH_LONG).show();
        }
    }
}

