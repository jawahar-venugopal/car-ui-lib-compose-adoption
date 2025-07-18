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

package com.android.car.ui.paintbooth.preferences;

import android.car.drivingstate.CarUxRestrictions;
import android.os.Bundle;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;

import com.android.car.ui.paintbooth.R;
import com.android.car.ui.preference.PreferenceFragment;
import com.android.car.ui.preference.UxRestrictablePreference;
import com.android.car.ui.utils.CarUxRestrictionsUtil;

/**
 * Fragment to load preferences
 */
public class PreferenceDemoFragment extends PreferenceFragment {

    CarUxRestrictionsUtil.OnUxRestrictionsChangedListener mOnUxRestrictionsChangedListener =
            restrictions -> {
                boolean isRestricted =
                        (restrictions.getActiveRestrictions()
                                & CarUxRestrictions.UX_RESTRICTIONS_NO_SETUP)
                                == CarUxRestrictions.UX_RESTRICTIONS_NO_SETUP;

                restrictPreference(getPreferenceScreen(), isRestricted);
            };

    private void restrictPreference(Preference preference, boolean restrict) {
        if (preference == null) {
            return;
        }

        if (preference instanceof UxRestrictablePreference) {
            ((UxRestrictablePreference) preference).setUxRestricted(restrict);
            ((UxRestrictablePreference) preference).setOnClickWhileRestrictedListener(p ->
                    Toast.makeText(getContext(), R.string.car_ui_restricted_while_driving,
                            Toast.LENGTH_LONG).show());
        }

        if (preference instanceof PreferenceGroup) {
            PreferenceGroup preferenceGroup = (PreferenceGroup) preference;
            for (int i = 0; i < preferenceGroup.getPreferenceCount(); i++) {
                restrictPreference(preferenceGroup.getPreference(i), restrict);
            }
        }
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference_samples, rootKey);
    }

    @Override
    public void onStart() {
        super.onStart();
        CarUxRestrictionsUtil.getInstance(requireContext())
                .register(mOnUxRestrictionsChangedListener);
    }

    @Override
    public void onStop() {
        CarUxRestrictionsUtil.getInstance(requireContext())
                .unregister(mOnUxRestrictionsChangedListener);
        super.onStop();
    }

}
