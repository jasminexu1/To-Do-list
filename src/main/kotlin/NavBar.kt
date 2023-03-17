import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import kotlinx.coroutines.launch
import navcommander.rememberNavCommander
import pages.GlobalThemeColourButtoninSettings


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Nav() {

    val pages = Pages.values().toList()
    val navCommander by rememberNavCommander(Pages.HomePage.name)
    val currentPage by remember { navCommander.currPage }

    val scroll = rememberScrollState(0)
    val darktheme = remember { mutableStateOf(1) }
    val requester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()
    MaterialTheme {
        Surface(
            modifier = Modifier.background(color = MaterialTheme.colors.background)
                .onKeyEvent { keyEvent ->
                    if (keyEvent.key == Key.One && keyEvent.isCtrlPressed) {
                        scope.launch {
                            navCommander.navigate(Pages.HomePage.name)
                        }

                    } else if (keyEvent.key == Key.Two && keyEvent.isCtrlPressed) {
                        scope.launch {
                            navCommander.navigate(Pages.Filters.name)
                        }

                    } else if (keyEvent.key == Key.Three && keyEvent.isCtrlPressed) {
                        scope.launch {
                            navCommander.navigate(Pages.Priority.name)
                        }
                    }  else if (keyEvent.key == Key.Four && keyEvent.isCtrlPressed) {
                        scope.launch {
                            navCommander.navigate(Pages.Archives.name)
                        }
                    } else if (keyEvent.key == Key.Five && keyEvent.isCtrlPressed) {
                        scope.launch {
                            navCommander.navigate(Pages.SettingPage.name)
                        }

                    }
                    true
                }.focusRequester(requester)
                .focusable()
        ) {
            LaunchedEffect(Unit) {
                requester.requestFocus()
            }
            Box(
                modifier = Modifier.fillMaxSize()
            ) {

                NavigationRail(
                    modifier = Modifier.align(Alignment.CenterStart).fillMaxHeight().verticalScroll(scroll)
                ) {
                    pages.forEach {
                        NavigationRailItem(
                            selected = currentPage == it.name,
                            icon = {
                                Icon(
                                    imageVector = it.icon,
                                    contentDescription = it.marker
                                )
                            },
                            label = {
                                Text(it.marker)
                            },
                            alwaysShowLabel = true,
                            onClick = {
                                navCommander.navigate(it.name)
                            }
                        )
                    }
                    if (GlobalThemeColourButtoninSettings == 1) {
                        darktheme.value = 1
                        //GlobalThemeColourButtoninSettings = 2
                    } else if (GlobalThemeColourButtoninSettings == 2) {
                        darktheme.value = 2
                        //GlobalThemeColourButtoninSettings = 3
                    } else if (GlobalThemeColourButtoninSettings == 3) {
                        darktheme.value = 3
                        //GlobalThemeColourButtoninSettings = 1
                    }

                }
                Box(
                    modifier = Modifier.fillMaxHeight()

                ) {

                    CustomNavigationHost(navCommander = navCommander,darktheme.value)

                }


            }

        }
    }
}
