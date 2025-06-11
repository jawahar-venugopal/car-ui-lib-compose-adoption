package com.android.car.compose.ui.paintbooth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.car.ui.recyclerview.CarUiRecyclerView
import com.android.car.ui.theme.CarUiTheme
import com.android.car.ui.toolbar.CarUiToolbar
import com.android.car.ui.toolbar.CarUiToolbarNavIconType

data class ActivityEntry(val label: String, val activityClass: Class<out Activity>)

class MainActivity : ComponentActivity() {

    private val activities = listOf(
        ActivityEntry("Dialogs sample", MainActivity::class.java),
        ActivityEntry("List sample", MainActivity::class.java),
        ActivityEntry("Grid sample", MainActivity::class.java),
        ActivityEntry("Preferences sample", MainActivity::class.java),
        ActivityEntry("Toolbar sample", MainActivity::class.java),
        ActivityEntry("ListItem sample", MainActivity::class.java)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarUiTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    MainScreen(activities)
                }
            }
        }
    }
}

@Composable
fun MainScreen(activities: List<ActivityEntry>) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        // Top toolbar
        CarUiToolbar(
            title = "Prototype B (Compose)",
            navIconType = CarUiToolbarNavIconType.None,
            logo = painterResource(id = R.drawable.ic_launcher)
        )
        // Main activity list
        CarUiRecyclerView(
            items = activities,
            itemContent = { entry ->
                ActivityButton(entry, context)
            }
        )

    }
}

@Composable
fun ActivityButton(entry: ActivityEntry, context: Context) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                try {
                    val intent = Intent(context, entry.activityClass)
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        "Activity not found: ${entry.label}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier.wrapContentWidth()
        ) {
            Text(text = entry.label, modifier = Modifier.padding(start = 16.dp, end = 16.dp))
        }
    }
}
