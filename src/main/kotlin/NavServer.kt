package navcommander

import androidx.compose.runtime.Composable

class NavigationHost(
    val navCommander: NavCommander,
    val items: @Composable NavigationGraphBuilder.() -> Unit
) {

    @Composable
    fun build() {
        NavigationGraphBuilder().renderContents()
    }

    inner class NavigationGraphBuilder(
        val navCommander: NavCommander = this@NavigationHost.navCommander
    ) {
        @Composable
        fun renderContents() {
            this@NavigationHost.items(this)
        }
    }
}

@Composable
fun NavigationHost.NavigationGraphBuilder.composable(
    path: String,
    items: @Composable () -> Unit
) {
    if (navCommander.currPage.value == path) {
        items()
    }
}