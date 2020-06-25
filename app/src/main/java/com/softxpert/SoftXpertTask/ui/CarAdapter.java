package com.softxpert.SoftXpertTask.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.softxpert.SoftXpertTask.R;
import com.softxpert.SoftXpertTask.data.models.Car;

public class CarAdapter extends PagedListAdapter<Car, CarAdapter.ItemViewHolder> {
    private static final String TAG = "CarAdapter";
    private static DiffUtil.ItemCallback<Car> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Car>() {
                @Override
                public boolean areItemsTheSame(Car oldItem, Car newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(Car oldItem, Car newItem) {
                    return oldItem.equals(newItem);
                }
            };
    private Context mCtx;

    public CarAdapter(Context mCtx) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.car_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {
        final Car car = getItem(position);

        if (car != null) {

            // issue in image url !! url returned not valid and i try that in browser and url open website page not photo location !!
            Log.d(TAG, "onBindViewHolder: image url " + car.getImageUrl());
            String url = null;
            try {
                url = car.getImageUrl().replace("http", "https");
            } catch (NullPointerException e) {

            } finally {
                Glide.with(mCtx)
                        .load(url)
                        .centerCrop()
                        .placeholder(R.drawable.no_img_found)
                        .into(holder.carImage);
            }

            holder.carBrand.setText(car.getBrand());
            if (car.getIsUsed())
                holder.carIsUsed.setText(R.string.used);
            else
                holder.carIsUsed.setText(R.string.not_used);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView carImage;
        TextView carBrand, carIsUsed;

        public ItemViewHolder(View itemView) {
            super(itemView);
            carImage = itemView.findViewById(R.id.car_image);
            carBrand = itemView.findViewById(R.id.car_brand);
            carIsUsed = itemView.findViewById(R.id.car_is_used);
        }
    }
}
