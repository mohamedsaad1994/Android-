package com.softxpert.SoftXpertTask.data.webservice;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.softxpert.SoftXpertTask.data.models.Car;
import com.softxpert.SoftXpertTask.data.models.Cars;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemDataSource extends PageKeyedDataSource<Integer, Car> {
    private static final int FIRST_PAGE = 1;
    private static final String TAG = "ItemDataSource";

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull final LoadInitialCallback<Integer, Car> callback) {

        RetrofitClient.getInsance()
                .getApi()
                .getCars(FIRST_PAGE)
                .enqueue(new Callback<Cars>() {
                    @Override
                    public void onResponse(Call<Cars> call, Response<Cars> response) {
                        if (response.body() != null) {
                            Log.d(TAG, "onResponse: " + response.body().getStatus());
                            callback.onResult(response.body().getData(), null, FIRST_PAGE + 1);
                        }
                    }

                    @Override
                    public void onFailure(Call<Cars> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Car> callback) {
        RetrofitClient.getInsance()
                .getApi()
                .getCars(params.key)
                .enqueue(new Callback<Cars>() {
                    @Override
                    public void onResponse(Call<Cars> call, Response<Cars> response) {
                        if (response.body() != null) {
                            Integer key = (params.key > 1) ? params.key - 1 : null;
                            callback.onResult(response.body().getData(), key);
                        }
                    }

                    @Override
                    public void onFailure(Call<Cars> call, Throwable t) {
                    }
                });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Car> callback) {
        RetrofitClient.getInsance()
                .getApi()
                .getCars(params.key)
                .enqueue(new Callback<Cars>() {
                    @Override
                    public void onResponse(Call<Cars> call, Response<Cars> response) {
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                Integer key = (params.key <= 4) ? params.key + 1 : null;
                                callback.onResult(response.body().getData(), key);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Cars> call, Throwable t) {

                    }
                });
    }
}
