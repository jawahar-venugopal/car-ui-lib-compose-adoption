package com.android.car.ui.paintbooth.benchmarka

import android.content.Intent
import androidx.benchmark.macro.FrameTimingMetric
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
class UiResponseBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()
    private val targetPackage = "com.android.car.ui.paintbooth"
    private val iterations = 25

    private fun launchIntent(activity: String) = Intent().apply {
        setClassName(targetPackage, "$targetPackage.$activity")
    }

    @Test
    fun showDialog() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(FrameTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.COLD,
        setupBlock = {
            pressHome()
        }) {
        startActivityAndWait(launchIntent("dialogs.DialogsActivity"))
        for (i in 0 until 5) {
            device.wait(Until.hasObject(By.res(targetPackage, "car_ui_internal_recycler_view")), 1000)
            val recyclerView = device.findObject(By.res(targetPackage, "car_ui_internal_recycler_view"))
            val firstItem = recyclerView.children[i] ?: error("No items in the list")
            val button = firstItem.findObject(By.res(targetPackage, "button"))
                ?: error("No button found in first list item")
            button.click()
            device.wait(Until.hasObject(By.res(targetPackage,"car_ui_alert_title")), 1000)
            device.waitForIdle()
            device.click(1300, 300)
            device.wait(Until.gone(By.res(targetPackage,"car_ui_alert_title")), 1000)
            device.waitForIdle()
        }
    }

    @Test
    fun toolbarTest() = benchmarkRule.measureRepeated(
        packageName = targetPackage,
        metrics = listOf(FrameTimingMetric()),
        iterations = iterations,
        startupMode = StartupMode.COLD,
        setupBlock = {
            pressHome()
        }) {
        startActivityAndWait(launchIntent("toolbar.ToolbarActivity"))
        device.wait(Until.hasObject(By.res(targetPackage, "car_ui_internal_recycler_view")), 1000)
        for (i in 0 until 6) {
            val recyclerView = device.findObject(By.res(targetPackage, "car_ui_internal_recycler_view"))
            recyclerView.setGestureMargin(device.displayWidth / 4)
            val item = recyclerView.children[i]
                ?: error("No visible item after scrolling to $i")
            val button = item.findObject(By.res(targetPackage, "button"))
                ?: error("No button found in first list item")
            button.click()
            device.waitForIdle()
            recyclerView.scroll(Direction.UP, 1f); device.waitForIdle()
            device.waitForIdle()
        }
    }
}