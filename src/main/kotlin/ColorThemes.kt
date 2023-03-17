import androidx.compose.material.*
import androidx.compose.runtime.*
import com.github.tkuenneth.nativeparameterstoreaccess.MacOSDefaults
import com.github.tkuenneth.nativeparameterstoreaccess.NativeParameterStoreAccess
import com.github.tkuenneth.nativeparameterstoreaccess.WindowsRegistry


fun isSystemInDarkTheme(): Boolean {
    return when {
        NativeParameterStoreAccess.IS_WINDOWS -> {
            val result = WindowsRegistry.getWindowsRegistryEntry(
                "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize",
                "AppsUseLightTheme"
            )
            result == 0x0
        }
        NativeParameterStoreAccess.IS_MACOS -> {
            val result = MacOSDefaults.getDefaultsEntry("AppleInterfaceStyle")
            result == "Dark"
        }
        else -> false
    }
}

private fun colors(): Colors = if (isInDarkMode) {
    darkColors()
} else {
    lightColors()
}

@Composable
fun TKDupeFinderContent() {
    var colors by remember { mutableStateOf(colors()) }
    onIsInDarkModeChanged = { _, _ ->
        colors = colors()
    }

    MaterialTheme(colors = colors) {
        Surface {
            Nav()
        }
    }
}