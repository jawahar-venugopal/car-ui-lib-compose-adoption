package com.android.car.compose.ui.paintbooth.toolbar

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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.android.car.compose.ui.paintbooth.R
import com.android.car.ui.recyclerview.CarUiRecyclerView
import com.android.car.ui.theme.CarUiTheme
import com.android.car.ui.toolbar.CarUiToolbar
import com.android.car.ui.toolbar.CarUiToolbarMenuItem
import com.android.car.ui.toolbar.CarUiToolbarNavIconType
import com.android.car.ui.toolbar.SearchMode

class ToolbarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarUiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    ToolbarActivityDemoScreen()
                }
            }
        }
    }
}

@Composable
fun ToolbarActivityDemoScreen() {
    val context = LocalContext.current
    var showProgressBar by remember { mutableStateOf(false) }
    var toolbarTitle by remember { mutableStateOf(context.getString(R.string.app_name)) }
    var toolbarSubtitle: String? by remember { mutableStateOf(null) }
    var navButtonMode by remember { mutableStateOf(CarUiToolbarNavIconType.Back) }
    var showLogo by remember { mutableStateOf(true) }
    var searchMode by remember { mutableStateOf(SearchMode.DISABLED) }
    var searchHint by remember { mutableStateOf("Search...") }
    val menuItemsList = remember { mutableStateListOf<CarUiToolbarMenuItem>() }
    var showSearchIcon by remember { mutableStateOf(true) }
    var customSearchIcon by remember { mutableStateOf(false) }
    var switchChecked by remember { mutableStateOf(false) }
    var activatableOn by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    fun toast(msg: String) = Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

    val demoButtons: List<Pair<String, () -> Unit>> = listOf(
        "Toggle progress bar" to {
            showProgressBar = !showProgressBar
        },

        "Change title" to {
            toolbarTitle = "$toolbarTitle X"
        },

        "Add/Change subtitle" to {
            toolbarSubtitle =
                if (toolbarSubtitle.isNullOrEmpty()) "Subtitle" else "$toolbarSubtitle X"
        },

        context.getString(R.string.toolbar_cycle_nav_button) to {
            navButtonMode = when (navButtonMode) {
                CarUiToolbarNavIconType.Disabled -> CarUiToolbarNavIconType.Back
                CarUiToolbarNavIconType.Back -> CarUiToolbarNavIconType.Close
                CarUiToolbarNavIconType.Close -> CarUiToolbarNavIconType.Down
                CarUiToolbarNavIconType.Down -> CarUiToolbarNavIconType.Disabled
            }
        },

        context.getString(R.string.toolbar_toggle_logo) to {
            showLogo = !showLogo
        },

        context.getString(R.string.toolbar_toggle_search_hint) to {
            searchHint = if (searchHint == "Foo") "Bar" else "Foo"
        },

        context.getString(R.string.toolbar_toggle_search_icon) to {
            customSearchIcon = !customSearchIcon
        },

        context.getString(R.string.toolbar_add_icon) to {
            menuItemsList.add(
                CarUiToolbarMenuItem.settings(
                    onClick = { toast("Settings icon clicked") })
            )
        },
        context.getString(R.string.toolbar_add_switch) to {
            menuItemsList.add(
                CarUiToolbarMenuItem.checkable(
                    checked = switchChecked,
                    onCheckedChange = {
                        switchChecked = it
                        toast("Switch checked: $it")
                    })
            )
        },
        context.getString(R.string.toolbar_add_text) to {
            menuItemsList.add(
                CarUiToolbarMenuItem(
                    title = "Baz", onClick = { toast("Baz clicked") })
            )
        },
        context.getString(R.string.toolbar_add_icon_text) to {
            menuItemsList.add(
                CarUiToolbarMenuItem(
                    iconRes = R.drawable.ic_tracklist,
                    title = "Bar",
                    showIconAndTitle = true,
                    onClick = { toast("Bar clicked") })
            )
        },
        context.getString(R.string.toolbar_add_activatable) to {
            menuItemsList.add(
                CarUiToolbarMenuItem.activatable(
                    iconRes = R.drawable.ic_tracklist,
                    activated = activatableOn,
                    onActivatedChange = {
                        activatableOn = it
                        toast("Activated: $it")
                    })
            )
        },
    )

    val menuItems by remember(showSearchIcon, customSearchIcon, menuItemsList) {
        derivedStateOf {
            buildList {
                if (showSearchIcon) {
                    add(
                        CarUiToolbarMenuItem(
                            isSearch = true,
                            iconRes = if (customSearchIcon) R.drawable.ic_launcher else R.drawable.car_ui_icon_search
                        )
                    )
                }
                addAll(menuItemsList)
            }
        }
    }


    Column(modifier = Modifier.fillMaxSize()) {
        CarUiToolbar(
            title = toolbarTitle,
            subtitle = toolbarSubtitle,
            navIconType = navButtonMode,
            logo = if (showLogo) painterResource(id = R.drawable.ic_launcher) else null,
            showProgressBar = showProgressBar,
            searchMode = searchMode,
            searchHint = searchHint,
            menuItems = menuItems,
            searchQuery = searchQuery,
            onSearchQueryChanged = { searchQuery = it },
            onSearchModeChanged = { searchMode = it },
        )
        CarUiRecyclerView(
            items = demoButtons, itemContent = { (label, action) ->
                DemoButton(label, action)
            })
    }
}

@Composable
fun DemoButton(label: String, action: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp), contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                action.invoke()
            }, modifier = Modifier.wrapContentWidth().semantics { contentDescription = "list_button" }
        ) {
            Text(text = label, modifier = Modifier.padding(start = 16.dp, end = 16.dp))
        }
    }
}