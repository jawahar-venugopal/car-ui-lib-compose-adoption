/*
 * Copyright (C) 2019 The Android Open Source Project
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

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.android.car.ui.FocusArea;
import com.android.car.ui.baselayout.Insets;
import com.android.car.ui.baselayout.InsetsChangedListener;
import com.android.car.ui.core.CarUi;
import com.android.car.ui.paintbooth.R;
import com.android.car.ui.recyclerview.CarUiRecyclerView;
import com.android.car.ui.toolbar.NavButtonMode;
import com.android.car.ui.toolbar.ToolbarController;

import java.util.ArrayList;

/**
 * Activity that shows GridCarUiRecyclerView example with sample data.
 */
public class GridCarUiRecyclerViewActivity extends Activity implements
        InsetsChangedListener {
    private final ArrayList<String> mData = new ArrayList<>();
    private final int mDataToGenerate = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_car_ui_recycler_view_activity);

        ToolbarController toolbar = CarUi.requireToolbar(this);
        toolbar.setTitle(getTitle());
        toolbar.setNavButtonMode(NavButtonMode.BACK);

        CarUiRecyclerView recyclerView = findViewById(R.id.list);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, generateSampleData());
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<String> generateSampleData() {
        for (int i = 1; i <= mDataToGenerate; i++) {
            mData.add("data" + i);
        }
        return mData;
    }

    @Override
    public void onCarUiInsetsChanged(@NonNull Insets insets) {
        FocusArea focusArea = requireViewById(R.id.focus_area);
        focusArea.setBoundsOffset(0, insets.getTop(), 0, insets.getBottom());
        focusArea.setHighlightPadding(0, insets.getTop(), 0, insets.getBottom());
        requireViewById(R.id.list)
                .setPadding(0, insets.getTop(), 0, insets.getBottom());
        requireViewById(android.R.id.content)
                .setPadding(insets.getLeft(), 0, insets.getRight(), 0);
    }
}
