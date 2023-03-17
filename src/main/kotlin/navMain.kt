package navcommander

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable


class NavCommander(
    private val originPage: String,
    private var backStackPages: MutableSet<String> = mutableSetOf()
) {
    var currPage: MutableState<String> = mutableStateOf(originPage)

    fun navigate(path: String) {
        if (path != currPage.value) {
            if (currPage.value != originPage && backStackPages.contains(currPage.value)) {
                backStackPages.remove(currPage.value)
            }

            if (originPage == path) {
                backStackPages = mutableSetOf()
            } else {
                backStackPages.add(currPage.value)
            }

            currPage.value = path
        }
    }


}


@Composable
fun rememberNavCommander(
    startDestination: String,
    backStackScreens: MutableSet<String> = mutableSetOf()
): MutableState<NavCommander> = rememberSaveable {
    mutableStateOf(NavCommander(startDestination, backStackScreens))
}

