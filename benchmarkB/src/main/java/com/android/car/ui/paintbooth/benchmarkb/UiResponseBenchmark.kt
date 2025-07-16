package com.android.car.ui.paintbooth.benchmarkb

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
    private val targetPackage = "com.android.car.compose.ui.paintbooth"
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
            device.wait(Until.hasObject(By.desc("car_ui_compose_lazy_list")), 1000)
            val recyclerView = device.findObject(By.desc("car_ui_compose_lazy_list"))
            val firstItem = recyclerView.children[i] ?: error("No items in the list")
            val button = firstItem.findObject(By.desc( "list_button"))
                ?: error("No button found in first list item")
            button.click()
            device.wait(Until.hasObject(By.desc("car_ui_alert_title")), 1000)
            device.waitForIdle()
            device.click(1300, 300)
            device.wait(Until.gone(By.desc("car_ui_alert_title")), 1000)
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
        device.wait(Until.hasObject(By.desc( "car_ui_compose_lazy_list")), 1000)
        for (i in 0 until 5) {
            val recyclerView = device.findObject(By.desc("car_ui_compose_lazy_list"))
            recyclerView.setGestureMargin(device.displayWidth / 4)
            val item = recyclerView.children[i]
                ?: error("No visible item after scrolling to $i")
            val button = item.findObject(By.desc( "list_button"))
                ?: error("No button found in first list item")
            button.click()
            device.waitForIdle()
            recyclerView.scroll(Direction.UP, 0.25f); device.waitForIdle()
            device.waitForIdle()
        }
    }
}