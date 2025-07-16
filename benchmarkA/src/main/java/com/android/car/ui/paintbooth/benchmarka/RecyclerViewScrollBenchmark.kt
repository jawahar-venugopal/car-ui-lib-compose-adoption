package com.android.car.ui.paintbooth.benchmarka

import android.content.Intent
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecyclerViewScrollBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()
    private val targetPackage = "com.android.car.ui.paintbooth"
    private val iterations = 25

    private fun launchIntent(activity: String) = Intent().apply {
        setClassName(targetPackage, "$targetPackage.$activity")
    }

    @Test
    fun scrollCarUiListItemActivity() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(FrameTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.COLD,
        setupBlock = {
            pressHome()
        }) {
        startActivityAndWait(launchIntent("caruirecyclerview.CarUiListItemActivity"))
        scrollList()
    }

    @Test
    fun scrollCarUiRecyclerViewActivity() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(FrameTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.COLD,
        setupBlock = {
            pressHome()
        }) {
        startActivityAndWait(launchIntent("caruirecyclerview.CarUiRecyclerViewActivity"))
        scrollList()
    }

    @Test
    fun scrollGridCarUiRecyclerViewActivity() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(FrameTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.COLD,
        setupBlock = {
            pressHome()
        }) {
        startActivityAndWait(launchIntent("caruirecyclerview.GridCarUiRecyclerViewActivity"))
        scrollList()
    }

    @Test
    fun scrollPreferencesActivity() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(FrameTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.COLD,
        setupBlock = {
            pressHome()
        }) {
        startActivityAndWait(launchIntent("preferences.PreferenceActivity"))
        scrollList()
    }

    private fun MacrobenchmarkScope.scrollList(percent: Float = 300f) {
        device.wait(Until.hasObject(By.res(targetPackage, "car_ui_internal_recycler_view")), 1000)
        val recyclerView = device.findObject(By.res(targetPackage, "car_ui_internal_recycler_view"))
        recyclerView.setGestureMargin(device.displayHeight / 2)
        recyclerView.scroll(Direction.UP, percent)
        device.waitForIdle()
    }
}
