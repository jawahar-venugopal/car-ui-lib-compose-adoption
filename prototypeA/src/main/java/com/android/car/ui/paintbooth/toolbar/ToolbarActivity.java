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
package com.android.car.ui.paintbooth.toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.car.ui.FocusArea;
import com.android.car.ui.baselayout.Insets;
import com.android.car.ui.baselayout.InsetsChangedListener;
import com.android.car.ui.core.CarUi;
import com.android.car.ui.paintbooth.R;
import com.android.car.ui.recyclerview.CarUiRecyclerView;
import com.android.car.ui.toolbar.MenuItem;
import com.android.car.ui.toolbar.NavButtonMode;
import com.android.car.ui.toolbar.SearchMode;
import com.android.car.ui.toolbar.Toolbar;
import com.android.car.ui.toolbar.ToolbarController;

import java.util.ArrayList;
import java.util.List;

public class ToolbarActivity extends AppCompatActivity implements InsetsChangedListener {

    private final List<MenuItem> mMenuItems = new ArrayList<>();
    private final List<Pair<CharSequence, View.OnClickListener>> mButtons = new ArrayList<>();
    private final RecyclerView.Adapter<ViewHolder> mAdapter =
            new RecyclerView.Adapter<ViewHolder>() {
                @Override
                public int getItemCount() {
                    return mButtons.size();
                }

                @Override
                public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
                    View item =
                            LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,
                                    parent, false);
                    return new ViewHolder(item);
                }

                @Override
                public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                    Pair<CharSequence, View.OnClickListener> pair = mButtons.get(position);
                    holder.bind(pair.first, pair.second);
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_ui_recycler_view_activity);

        ToolbarController toolbar = CarUi.requireToolbar(this);
        toolbar.setTitle(getTitle());
        toolbar.setNavButtonMode(NavButtonMode.BACK);
        toolbar.setLogo(R.drawable.ic_launcher);
        boolean[] isSearching = new boolean[]{false};
        toolbar.registerBackListener(
                () -> {
                    if (toolbar.getState() == Toolbar.State.SEARCH
                            || toolbar.getState() == Toolbar.State.EDIT) {
                        toolbar.setState(Toolbar.State.SUBPAGE);
                        return true;
                    } else if (isSearching[0]) {
                        toolbar.setSearchMode(SearchMode.DISABLED);
                        isSearching[0] = false;
                        return true;
                    }
                    return false;
                });

        mMenuItems.add(MenuItem.builder(this)
                .setToSearch()
                .setOnClickListener(i -> {
                    isSearching[0] = true;
                    toolbar.setSearchMode(SearchMode.SEARCH);
                })
                .build());

        toolbar.setMenuItems(mMenuItems);

        mButtons.add(Pair.create("Toggle progress bar", v -> {
            toolbar.getProgressBar().setVisible(!toolbar.getProgressBar().isVisible());
        }));

        mButtons.add(Pair.create("Change title", v ->
                toolbar.setTitle(toolbar.getTitle() + " X")));

        mButtons.add(Pair.create("Add/Change subtitle", v -> {
            CharSequence subtitle = toolbar.getSubtitle();
            if (TextUtils.isEmpty(subtitle)) {
                toolbar.setSubtitle("Subtitle");
            } else {
                toolbar.setSubtitle(subtitle + " X");
            }
        }));

        mButtons.add(Pair.create(getString(R.string.toolbar_cycle_nav_button), v -> {
            NavButtonMode mode = toolbar.getNavButtonMode();
            if (mode == NavButtonMode.DISABLED) {
                toolbar.setNavButtonMode(NavButtonMode.BACK);
            } else if (mode == NavButtonMode.BACK) {
                toolbar.setNavButtonMode(NavButtonMode.CLOSE);
            } else if (mode == NavButtonMode.CLOSE) {
                toolbar.setNavButtonMode(NavButtonMode.DOWN);
            } else {
                toolbar.setNavButtonMode(NavButtonMode.DISABLED);
            }
        }));

        Mutable<Boolean> hasLogo = new Mutable<>(true);
        mButtons.add(Pair.create(getString(R.string.toolbar_toggle_logo), v -> {
            toolbar.setLogo(hasLogo.value ? 0 : R.drawable.ic_launcher);
            hasLogo.value = !hasLogo.value;
        }));

        mButtons.add(Pair.create(getString(R.string.toolbar_toggle_search_hint), v -> {
            if (toolbar.getSearchHint().toString().contentEquals("Foo")) {
                toolbar.setSearchHint("Bar");
            } else {
                toolbar.setSearchHint("Foo");
            }
        }));

        Mutable<Boolean> showingLauncherIcon = new Mutable<>(false);
        mButtons.add(Pair.create(getString(R.string.toolbar_toggle_search_icon), v -> {
            if (showingLauncherIcon.value) {
                toolbar.setSearchIcon(null);
            } else {
                toolbar.setSearchIcon(R.drawable.ic_launcher);
            }
            showingLauncherIcon.value = !showingLauncherIcon.value;
        }));

        mButtons.add(Pair.create(getString(R.string.toolbar_add_icon), v -> {
            mMenuItems.add(MenuItem.builder(this)
                    .setToSettings()
                    .setOnClickListener(i -> Toast.makeText(this, "Clicked",
                            Toast.LENGTH_SHORT).show())
                    .build());
            toolbar.setMenuItems(mMenuItems);
        }));


        mButtons.add(Pair.create(getString(R.string.toolbar_add_switch), v -> {
            mMenuItems.add(MenuItem.builder(this)
                    .setCheckable()
                    .setOnClickListener(
                            i ->
                                    Toast.makeText(this,
                                                    "Checked? " + i.isChecked(),
                                                    Toast.LENGTH_SHORT)
                                            .show())
                    .build());
            toolbar.setMenuItems(mMenuItems);
        }));

        mButtons.add(Pair.create(getString(R.string.toolbar_add_text), v -> {
            mMenuItems.add(MenuItem.builder(this)
                    .setTitle("Baz")
                    .setOnClickListener(
                            i -> Toast.makeText(this, "Clicked",
                                    Toast.LENGTH_SHORT).show())
                    .build());
            toolbar.setMenuItems(mMenuItems);
        }));

        mButtons.add(Pair.create(getString(R.string.toolbar_add_icon_text), v -> {
            mMenuItems.add(MenuItem.builder(this)
                    .setIcon(R.drawable.ic_tracklist)
                    .setTitle("Bar")
                    .setShowIconAndTitle(true)
                    .setOnClickListener(
                            i -> Toast.makeText(this, "Clicked",
                                    Toast.LENGTH_SHORT).show())
                    .build());
            toolbar.setMenuItems(mMenuItems);
        }));

        mButtons.add(Pair.create(getString(R.string.toolbar_add_activatable), v -> {
            mMenuItems.add(MenuItem.builder(this)
                    .setIcon(R.drawable.ic_tracklist)
                    .setActivatable()
                    .setOnClickListener(
                            i -> Toast.makeText(this, "Clicked",
                                    Toast.LENGTH_SHORT).show())
                    .build());
            toolbar.setMenuItems(mMenuItems);
        }));

        CarUiRecyclerView prv = requireViewById(R.id.list);
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

    private static class ViewHolder extends RecyclerView.ViewHolder {

        private final Button mButton;

        ViewHolder(View itemView) {
            super(itemView);
            mButton = itemView.requireViewById(R.id.button);
        }

        public void bind(CharSequence title, View.OnClickListener listener) {
            mButton.setText(title);
            mButton.setOnClickListener(listener);
        }
    }

    /**
     * For changing values from lambdas
     */
    private static final class Mutable<E> {

        public E value;

        Mutable() {
            value = null;
        }

        Mutable(E value) {
            this.value = value;
        }
    }

}
