package com.softxpert.SoftXpertTask.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.softxpert.SoftXpertTask.data.models.Car;
import com.softxpert.SoftXpertTask.data.webservice.ItemDataSourceFactory;

public class CarsViewModel extends ViewModel {
    LiveData<PagedList<Car>> itemPagedList;
    LiveData<PageKeyedDataSource<Integer, Car>> liveDataSource;

    public CarsViewModel() {
        ItemDataSourceFactory itemDataSourceFactory = new ItemDataSourceFactory();
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(10)
                        .build();

        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, config)).build();
    }
}
