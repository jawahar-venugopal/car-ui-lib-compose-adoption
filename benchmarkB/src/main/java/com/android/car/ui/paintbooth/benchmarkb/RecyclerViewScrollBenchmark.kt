package com.android.car.ui.paintbooth.benchmarkb

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
    private val targetPackage = "com.android.car.compose.ui.paintbooth"
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
        scrollList(8)
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
        scrollList(25)
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
        scrollList(19)
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
        scrollList(4)
    }

    private fun MacrobenchmarkScope.scrollList(repats: Int = 5) {
        device.wait(Until.hasObject(By.desc("car_ui_compose_lazy_list")), 2000)
        val listView = device.findObject(By.desc("car_ui_compose_lazy_list"))
        listView.setGestureMargin(device.displayHeight / 2)
        repeat(repats) {
            listView.scroll(Direction.UP, 1f)
            device.waitForIdle()
        }
    }
}
