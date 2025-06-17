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

package com.android.car.ui.paintbooth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.car.ui.FocusArea;
import com.android.car.ui.baselayout.Insets;
import com.android.car.ui.baselayout.InsetsChangedListener;
import com.android.car.ui.core.CarUi;
import com.android.car.ui.paintbooth.caruirecyclerview.CarUiListItemActivity;
import com.android.car.ui.paintbooth.caruirecyclerview.CarUiRecyclerViewActivity;
import com.android.car.ui.paintbooth.caruirecyclerview.GridCarUiRecyclerViewActivity;
import com.android.car.ui.paintbooth.dialogs.DialogsActivity;
import com.android.car.ui.paintbooth.preferences.PreferenceActivity;
import com.android.car.ui.paintbooth.toolbar.ToolbarActivity;
import com.android.car.ui.recyclerview.CarUiRecyclerView;
import com.android.car.ui.toolbar.ToolbarController;

import java.util.Arrays;
import java.util.List;

/**
 * Paint booth app
 */
public class MainActivity extends Activity implements InsetsChangedListener {

    /**
     * List of all sample activities.
     */
    private final List<ListElement> mActivities = Arrays.asList(
            new ActivityElement("List sample", CarUiRecyclerViewActivity.class),
            new ActivityElement("Grid sample", GridCarUiRecyclerViewActivity.class),
            new ActivityElement("ListItem sample", CarUiListItemActivity.class),
            new ActivityElement("Preferences sample", PreferenceActivity.class),
            new ActivityElement("Toolbar sample", ToolbarActivity.class),
            new ActivityElement("Dialogs sample", DialogsActivity.class)
          );
    private final RecyclerView.Adapter<ViewHolder> mAdapter =
            new RecyclerView.Adapter<ViewHolder>() {
                @NonNull
                @Override
                public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                    if (viewType == ListElement.TYPE_ACTIVITY) {
                        return new ActivityViewHolder(
                                inflater.inflate(R.layout.list_item, parent, false));
                    } else {
                        throw new IllegalArgumentException("Unknown viewType: " + viewType);
                    }
                }

                @Override
                public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                    holder.bind(mActivities.get(position));
                }

                @Override
                public int getItemCount() {
                    return mActivities.size();
                }

                @Override
                public int getItemViewType(int position) {
                    return mActivities.get(position).getType();
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_ui_recycler_view_activity);

        ToolbarController toolbar = CarUi.requireToolbar(this);
        toolbar.setLogo(R.drawable.ic_launcher);
        toolbar.setTitle(getTitle());

        CarUiRecyclerView prv = findViewById(R.id.list);
        prv.setAdapter(mAdapter);

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

    private abstract static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public abstract void bind(ListElement element);
    }

    private abstract static class ListElement {
        static final int TYPE_ACTIVITY = 0;

        private final String mText;

        ListElement(String text) {
            mText = text;
        }

        String getText() {
            return mText;
        }

        abstract int getType();
    }

    private static class ActivityElement extends ListElement {
        private final Class<? extends Activity> mActivityClass;

        ActivityElement(String text, Class<? extends Activity> activityClass) {
            super(text);
            mActivityClass = activityClass;
        }

        Class<? extends Activity> getActivity() {
            return mActivityClass;
        }

        @Override
        int getType() {
            return TYPE_ACTIVITY;
        }
    }

    private class ActivityViewHolder extends ViewHolder {
        private final Button mButton;

        ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            mButton = itemView.requireViewById(R.id.button);
        }

        @Override
        public void bind(ListElement e) {
            if (!(e instanceof ActivityElement)) {
                throw new IllegalArgumentException("Expected an ActivityElement");
            }
            ActivityElement element = (ActivityElement) e;
            mButton.setText(element.getText());
            mButton.setOnClickListener(v ->
                    startActivity(new Intent(itemView.getContext(), element.getActivity())));
        }
    }

}
