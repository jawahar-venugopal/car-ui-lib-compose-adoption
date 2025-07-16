package com.android.car.ui.paintbooth.benchmarka

import android.content.Intent
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ActivityStartupBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()
    private val targetPackage = "com.android.car.ui.paintbooth"
    private val iterations = 2

    private fun launchIntent(activity: String) =
        Intent().apply {
            setClassName(targetPackage, "$targetPackage.$activity")
        }


    // -------------------- MainActivity --------------------

    @Test
    fun coldStartMainActivity() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(StartupTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.COLD,
        setupBlock = { pressHome() }
    ) {
        startActivityAndWait(launchIntent("MainActivity"))
    }

    @Test
    fun warmStartMainActivity() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(StartupTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.WARM,
        setupBlock = { pressHome() }
    ) {
        startActivityAndWait(launchIntent("MainActivity"))
    }

    @Test
    fun hotStartMainActivity() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(StartupTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.HOT,
        setupBlock = { pressHome() }
    ) {
        startActivityAndWait(launchIntent("MainActivity"))
    }

    // -------------------- ToolbarActivity --------------------

    @Test
    fun coldStartToolbarActivity() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(StartupTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.COLD,
        setupBlock = { pressHome() }
    ) {
        startActivityAndWait(launchIntent("toolbar.ToolbarActivity"))
    }

    @Test
    fun warmStartToolbarActivity() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(StartupTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.WARM,
        setupBlock = { pressHome() }
    ) {
        startActivityAndWait(launchIntent("toolbar.ToolbarActivity"))
    }

    @Test
    fun hotStartToolbarActivity() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(StartupTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.HOT,
        setupBlock = { pressHome() }
    ) {
        startActivityAndWait(launchIntent("toolbar.ToolbarActivity"))
    }

    // -------------------- PreferencesActivity --------------------

    @Test
    fun coldStartPreferencesActivity() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(StartupTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.COLD,
        setupBlock = { pressHome() }
    ) {
        startActivityAndWait(launchIntent("preferences.PreferenceActivity"))
    }

    @Test
    fun warmStartPreferencesActivity() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(StartupTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.WARM,
        setupBlock = { pressHome() }
    ) {
        startActivityAndWait(launchIntent("preferences.PreferenceActivity"))
    }

    @Test
    fun hotStartPreferencesActivity() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(StartupTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.HOT,
        setupBlock = { pressHome() }
    ) {
        startActivityAndWait(launchIntent("preferences.PreferenceActivity"))
    }

    // -------------------- DialogsActivity --------------------

    @Test
    fun coldStartDialogsActivity() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(StartupTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.COLD,
        setupBlock = { pressHome() }
    ) {
        startActivityAndWait(launchIntent("dialogs.DialogsActivity"))
    }

    @Test
    fun warmStartDialogsActivity() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(StartupTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.WARM,
        setupBlock = { pressHome() }
    ) {
        startActivityAndWait(launchIntent("dialogs.DialogsActivity"))
    }

    @Test
    fun hotStartDialogsActivity() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(StartupTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.HOT,
        setupBlock = { pressHome() }
    ) {
        startActivityAndWait(launchIntent("dialogs.DialogsActivity"))
    }

    // -------------------- CarUiListItemActivity --------------------

    @Test
    fun coldStartCarUiListItemActivity() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(StartupTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.COLD,
        setupBlock = { pressHome() }
    ) {
        startActivityAndWait(launchIntent("caruirecyclerview.CarUiListItemActivity"))
    }

    @Test
    fun warmStartCarUiListItemActivity() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(StartupTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.WARM,
        setupBlock = { pressHome() }
    ) {
        startActivityAndWait(launchIntent("caruirecyclerview.CarUiListItemActivity"))
    }

    @Test
    fun hotStartCarUiListItemActivity() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(StartupTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.HOT,
        setupBlock = { pressHome() }
    ) {
        startActivityAndWait(launchIntent("caruirecyclerview.CarUiListItemActivity"))
    }

    // -------------------- CarUiRecyclerViewActivity --------------------

    @Test
    fun coldStartCarUiRecyclerViewActivity() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(StartupTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.COLD,
        setupBlock = { pressHome() }
    ) {
        startActivityAndWait(launchIntent("caruirecyclerview.CarUiRecyclerViewActivity"))
    }

    @Test
    fun warmStartCarUiRecyclerViewActivity() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(StartupTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.WARM,
        setupBlock = { pressHome() }
    ) {
        startActivityAndWait(launchIntent("caruirecyclerview.CarUiRecyclerViewActivity"))
    }

    @Test
    fun hotStartCarUiRecyclerViewActivity() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(StartupTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.HOT,
        setupBlock = { pressHome() }
    ) {
        startActivityAndWait(launchIntent("caruirecyclerview.CarUiRecyclerViewActivity"))
    }

    // -------------------- GridCarUiRecyclerViewActivity --------------------

    @Test
    fun coldStartGridCarUiRecyclerViewActivity() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(StartupTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.COLD,
        setupBlock = { pressHome() }
    ) {
        startActivityAndWait(launchIntent("caruirecyclerview.GridCarUiRecyclerViewActivity"))
    }

    @Test
    fun warmStartGridCarUiRecyclerViewActivity() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(StartupTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.WARM,
        setupBlock = { pressHome() }
    ) {
        startActivityAndWait(launchIntent("caruirecyclerview.GridCarUiRecyclerViewActivity"))
    }

    @Test
    fun hotStartGridCarUiRecyclerViewActivity() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(StartupTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.HOT,
        setupBlock = { pressHome() }
    ) {
        startActivityAndWait(launchIntent("caruirecyclerview.GridCarUiRecyclerViewActivity"))
    }
}