// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Brush.Companion.horizontalGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.awt.Dimension
import java.awt.Point
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt
import kotlin.properties.Delegates.observable


// Original
/*
val gradientLight = horizontalGradient(
    listOf(Color(0xffa18cd2), Color(0xfffbc2ec)),
    startX = 0.0f,
    endX = 1000.0f,
    tileMode = TileMode.Clamp
)*/

// Night sky

val gradientEnergetic = horizontalGradient(
    listOf(Color(0xff560bbc), Color(0xff864bd7)),
    startX = 0.0f,
    endX = 1000.0f,
    tileMode = TileMode.Clamp
)

val gradientEnergeticEnd = horizontalGradient(
    listOf(Color(0xff864bd7), Color(0xff864bd7)),
    startX = 0.0f,
    endX = 1000.0f,
    tileMode = TileMode.Clamp
)


// Energetic
/*
val gradient = horizontalGradient(
    listOf(Color(0xff560bbc), Color(0xffE71879)),
    startX = 0.0f,
    endX = 1000.0f,
    tileMode = TileMode.Clamp
)*/

// Grey, darkmode

val gradient = horizontalGradient(
    listOf(Color(0xff2A2A29), Color(0xff2A2A29)),
    startX = 0.0f,
    endX = 1000.0f,
    tileMode = TileMode.Clamp
)

//pure white weirdos
val gradientLight = horizontalGradient(
    listOf(Color(0xffffffff), Color(0xffffffff)),
    startX = 0.0f,
    endX = 1000.0f,
    tileMode = TileMode.Clamp
)
/*
val gradientEnergetic = horizontalGradient(
    listOf(Color(0xff560bbc), Color(0xffE71879)),
    startX = 0.0f,
    endX = 1000.0f,
    tileMode = TileMode.Clamp
)

val gradientEnergeticEnd = horizontalGradient(
    listOf(Color(0xffE71879), Color(0xffE71879)),
    startX = 0.0f,
    endX = 1000.0f,
    tileMode = TileMode.Clamp
)*/

//val gradient

val db = dynamodb()
val user = userHandler()
val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")

val daodb = DAOFacadeImpl()
val archivedb = ArchiveFacadeImpl()

val current = LocalDateTime.now().format(formatter)
val nextday = LocalDateTime.now().plusDays(1).format(formatter)



val settings: Settings = Settings()
var signedInState = settings.getBoolean("loggedInState", false)
val width = Settings()
val height = Settings()
val xpos = Settings()
val ypos = Settings()


var isInDarkMode by observable(false) { _, oldValue, newValue ->
    onIsInDarkModeChanged?.let { it(oldValue, newValue) }
}
var onIsInDarkModeChanged: ((Boolean, Boolean) -> Unit)? = null

@Composable
fun TaskField(today: SnapshotStateList<Tisk>, tomorrow: SnapshotStateList<Tisk>, darktheme: Int) {
    Column {
        TisksDisplay(today, "Today's Tasks", tomorrow, "Tomorrow's Tasks", darktheme)
    }
}





@OptIn(DelicateCoroutinesApi::class)
fun main() = application {
    val state = WindowState()
    GlobalScope.launch {
        while (isActive) {
            val newMode = isSystemInDarkTheme()
            if (isInDarkMode != newMode) {
                isInDarkMode = newMode
            }
        }
    }


    val w = width.getInt("width", -1)
    val h = height.getInt("height", 800)
    val px = width.getInt("xpos", 100)
    val py = height.getInt("ypos", 100)

    Window(onCloseRequest = ::exitApplication, state) {
        DatabaseFactory.init()
        ArchiveFactory.init()
        println(signedInState)
        if (signedInState){
            runBlocking {
                launch {
                    val jsonConverted = settings.getStringOrNull("username")?.let { db.getUser(it) }
                    println(jsonConverted)
                    if (jsonConverted != null) {
                        println(jsonConverted.Items[0].Tasks)
                        println(daodb.allTisks())
                        user.syncFromDB(jsonConverted.Items[0].Archive,
                            jsonConverted.Items[0].Tasks,
                            jsonConverted.Items[0].CustomTags,
                            jsonConverted.Items[0].CustomPriorities)
                    }
                }
            }
        }

        window.preferredSize = Dimension(w, h)
        window.location = Point(px,py)
        window.minimumSize = Dimension(600,600)

        TKDupeFinderContent()
        LaunchedEffect(state) {
            snapshotFlow { state.size }
                .onEach() {

                    width.putInt("width", it.width.value.toInt())
                    width["width"] = it.width.value.toInt()

                    height.putInt("height", it.height.value.toInt())
                    height["height"] = it.height.value.toInt()
                }
                .launchIn(this)

            snapshotFlow { state.position }
                .onEach() {
                    xpos.putInt("xpos", it.x.value.toInt())
                    xpos["xpos"] = it.x.value.toInt()

                    ypos.putInt("ypos", it.y.value.toInt())
                    ypos["ypos"] = it.y.value.toInt()
                }
                .launchIn(this)
        }
    }
}



