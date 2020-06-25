package com.softxpert.SoftXpertTask.data.webservice;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.softxpert.SoftXpertTask.data.models.Car;

public class ItemDataSourceFactory extends DataSource.Factory {
    private MutableLiveData<PageKeyedDataSource<Integer, Car>> itemLiveDataSource = new MutableLiveData<>();

    @Override
    public DataSource create() {
        ItemDataSource itemDataSource = new ItemDataSource();
        itemLiveDataSource.postValue(itemDataSource);
        return itemDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Car>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}