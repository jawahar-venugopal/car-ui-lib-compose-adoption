/*
 * Copyright (C) 2019 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.car.ui.paintbooth.caruirecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.car.ui.paintbooth.R;

import java.util.List;

/**
 * Implementation of {@link RecyclerViewAdapter} that can be used with RecyclerViews.
 */
public class RecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private final Context mContext;
    private final List<String> mData;

    public RecyclerViewAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.mData = data;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(mContext);
        View view = inflator.inflate(R.layout.car_ui_recycler_view_list_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        String title = mData.get(position);
        holder.mTextTitle.setText(title);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    /**
     * Holds views for each element in the list.
     */
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView mTextTitle;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextTitle = itemView.findViewById(R.id.textTitle);
        }
    }
}

