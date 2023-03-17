import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import navcommander.NavCommander
import navcommander.NavigationHost
import navcommander.composable
import pages.Archives
import pages.HomePage
import pages.SettingPage


enum class Pages(
    val marker: String,
    val icon: ImageVector
) {
    HomePage(
        marker = "Home",
        icon = Icons.Filled.Home
    ),
    Filters(
        marker = "Filters",
        icon = Icons.Filled.FavoriteBorder
    ),
    Priority(
        marker = "Priority",
        icon = Icons.Filled.ThumbUp
    ),
    Archives(
        marker = "Archives",
        icon = Icons.Filled.PlayArrow
    ),
    SettingPage(
        marker = "Settings",
        icon = Icons.Filled.Settings
    )

}


@Composable
fun CustomNavigationHost(
    navCommander: NavCommander,
    darktheme: Int
) {
    NavigationHost(navCommander) {
        composable(Pages.HomePage.name) {
            HomePage(navCommander, darktheme)
        }

        composable(Pages.Filters.name) {
            Filters(navCommander, darktheme)
        }
        composable(Pages.Priority.name) {
            Priority(navCommander, darktheme)
        }
        composable(Pages.Archives.name) {
            Archives(navCommander, darktheme)
        }
        composable(Pages.SettingPage.name) {
            SettingPage(navCommander)
        }
    }.build()
}

