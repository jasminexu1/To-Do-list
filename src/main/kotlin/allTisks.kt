
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

fun <T> MutableList<T>.swapMine(index1: Int, index2: Int) {
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}




@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TisksDisplay(
    todaysList: SnapshotStateList<Tisk>,
    title1: String,
    tomorrowsList: SnapshotStateList<Tisk>,
    title2: String,
    darktheme: Int
) {
    val requester = remember { FocusRequester() }
    val scope = rememberCoroutineScope()

    Row(modifier = Modifier.onKeyEvent {
        if (it.key == Key.S && it.isCtrlPressed) {
            scope.launch {
                val newArrayList = todaysList.sortedBy { it.priority }

                todaysList.removeAll(todaysList)
                todaysList.addAll(newArrayList)
            }
            true
        } else if(it.key == Key.W && it.isCtrlPressed) {
            val newArrayList = tomorrowsList.sortedBy { it.priority }
            tomorrowsList.removeAll(tomorrowsList)
            tomorrowsList.addAll(newArrayList)
        }

         else {
            false
        }

    }
        .focusRequester(requester)
        .focusable()

    ) {
        LaunchedEffect(Unit) {
            requester.requestFocus()
        }
        LazyColumn(
            Modifier
                //.background(Color(0xffD4C6E7))
                .background(
                    brush = if (darktheme == 1) gradient else if (darktheme == 2) gradientLight else gradientEnergetic, shape = RoundedCornerShape(
                        topEnd = 0.dp,
                        bottomEnd = 0.dp,
                        topStart = 20.dp,
                        bottomStart = 20.dp
                    )
                )
                .fillMaxHeight(0.978f)
                .fillMaxWidth(0.5f)
                ,
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Column (
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentHeight()
                        .padding(vertical = 25.dp),
                ) {
                    Text(
                        title1 + "...",
                        style = MaterialTheme.typography.h4,
                        color = if (darktheme == 1|| darktheme == 3) Color.White else Color.Black
                        //color = Color.Black
                    )
                    Button(onClick = {
                        val newArrayList = todaysList.sortedBy { it.priority }
                        todaysList.removeAll(todaysList)
                        todaysList.addAll(newArrayList)
                    }){
                        Text("Sort by Priority")
                    }
                }
            }
            var inc = 0
            items(
                items = todaysList,
                key = { item: Tisk -> item.uuid },
            ) { tisk ->
                tisks(
                    uuid = tisk.uuid,
                    tag = tisk.tag,
                    priority = tisk.priority,
                    description = tisk.description,
                    text = tisk.text,
                    duedate = tisk.duedate,
                    tiskList = todaysList,
                    tisk = tisk,
                    darktheme = darktheme
                )
                inc += 1
            }
            item {
                Column (
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentHeight()
                        .padding(vertical = 25.dp),
                ) {
                    Text(
                        title2 + "...",
                        style = MaterialTheme.typography.h4,
                        color = if (darktheme == 1|| darktheme == 3) Color.White else Color.Black
                        //color = Color.Black
                    )
                    Button(onClick = {
                        val newArrayList = tomorrowsList.sortedBy { it.priority }
                        tomorrowsList.removeAll(tomorrowsList)
                        tomorrowsList.addAll(newArrayList)
                    }){
                        Text("Sort by Priority")
                    }
                }
            }
            items(
                items = tomorrowsList,
                key = { item: Tisk -> item.uuid }
            ) { tisk ->
                tisks(
                    uuid = tisk.uuid,
                    tag = tisk.tag,
                    priority = tisk.priority,
                    text = tisk.text,
                    duedate = tisk.duedate,
                    tiskList = tomorrowsList,
                    tisk = tisk,
                    description = tisk.description,
                    darktheme = darktheme
                )
            }
        }


        LazyColumn(
            modifier = Modifier.fillMaxWidth(0.08F).background(brush = if (darktheme == 1) gradient else if (darktheme == 2) gradientLight else gradientEnergeticEnd)
                .fillMaxHeight(0.978f).padding(top = 150.dp)

        ) {
            var beg = 0

            val len = todaysList.size
            item {
                for (i in todaysList.indices) {
                    if (len - 1 == i) {


                        Button(
                            modifier = Modifier.padding(top = 1.dp, bottom = 3.dp)
                                .size(width = 20.dp, height = 20.dp),
                            onClick = {
                                todaysList.swapMine(i, beg)
                            },
                            contentPadding = PaddingValues(0.dp),
                            ) {
                            Icon(
                                contentDescription = "add text",
                                imageVector = Icons.Outlined.KeyboardArrowDown,
                                tint = if (darktheme == 1|| darktheme == 3) Color.White else Color.Black,
                                modifier = Modifier.fillMaxSize(1.0F)
                            )
                        }
                    } else {
                        Button(
                            modifier = Modifier.padding(top = 1.dp, bottom = 3.dp)
                                .size(width = 20.dp, height = 20.dp),
                            onClick = {
                                todaysList.swapMine(i, i + 1)
                            },
                            contentPadding = PaddingValues(0.dp),
                        ) {
                            Icon(
                                contentDescription = "add text",
                                imageVector = Icons.Outlined.KeyboardArrowDown,
                                tint = if (darktheme == 1|| darktheme == 3) Color.White else Color.Black,
                                modifier = Modifier.fillMaxSize(1.0F)

                            )
                        }
                    }
                }
                Spacer(Modifier.size(135.dp))
                for (i in tomorrowsList.indices) {
                    if (len - 1 == i) {
                        Button(
                            modifier = Modifier.padding(top = 1.dp, bottom = 3.dp)
                                .size(width = 20.dp, height = 20.dp),
                            onClick = {
                                tomorrowsList.swapMine(i, beg)
                            },
                            contentPadding = PaddingValues(0.dp),
                            ) {
                            Icon(
                                contentDescription = "add text",
                                imageVector = Icons.Outlined.KeyboardArrowDown,
                                tint = if (darktheme == 1|| darktheme == 3) Color.White else Color.Black,
                                modifier = Modifier.fillMaxSize(1.0F)
                            )
                        }
                    } else {
                        Button(
                            modifier = Modifier.padding(top = 1.dp, bottom = 3.dp)
                                .size(width = 20.dp, height = 20.dp),
                            onClick = {
                                tomorrowsList.swapMine(i, i + 1)
                            },
                            contentPadding = PaddingValues(0.dp),
                        ) {
                            Icon(
                                contentDescription = "add text",
                                imageVector = Icons.Outlined.KeyboardArrowDown,
                                tint = if (darktheme == 1|| darktheme == 3) Color.White else Color.Black,
                                modifier = Modifier.fillMaxSize(1.0F)
                            )
                        }
                    }
                }
                Spacer(Modifier.size(18.dp))
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(0.08F).background(
                brush = if (darktheme == 1) gradient else if (darktheme == 2) gradientLight else gradientEnergeticEnd, shape = RoundedCornerShape(
                    topEnd = 20.dp, bottomEnd = 20.dp
                )
            ).fillMaxHeight(0.978f).padding(top = 150.dp)

        ) {
            var beg = 0
            var end = todaysList.size - 1
            val len = todaysList.size
            item {
                for (i in todaysList.indices) {
                    if (i >= 1) {
                        Button(
                            modifier = Modifier.padding(top = 1.dp, bottom = 3.dp)
                                .size(width = 20.dp, height = 20.dp),
                            onClick = {
                                todaysList.swapMine(i, i - 1)
                            },
                            contentPadding = PaddingValues(0.dp),
                            ) {
                            Icon(
                                contentDescription = "add text",
                                imageVector = Icons.Outlined.KeyboardArrowUp,
                                tint = if (darktheme == 1|| darktheme == 3) Color.White else Color.Black,
                                modifier = Modifier.fillMaxSize(1.0F)
                            )
                        }
                    } else if (i == 0) {
                        Button(
                            modifier = Modifier.padding(top = 1.dp, bottom = 3.dp)
                                .size(width = 20.dp, height = 20.dp),
                            onClick = {
                                todaysList.swapMine(i, end)
                            },
                            contentPadding = PaddingValues(0.dp),
                            ) {
                            Icon(
                                contentDescription = "add text",
                                imageVector = Icons.Outlined.KeyboardArrowUp,
                                tint = if (darktheme == 1|| darktheme == 3) Color.White else Color.Black,
                                modifier = Modifier.fillMaxSize(1.0F)
                            )
                        }
                    }
                }
                Spacer(Modifier.size(135.dp))
                for (i in tomorrowsList.indices) {
                    if (i >= 1) {
                        Button(
                            modifier = Modifier.padding(top = 1.dp, bottom = 3.dp)
                                .size(width = 20.dp, height = 20.dp),
                            onClick = {
                                tomorrowsList.swapMine(i, i - 1)
                            },
                            contentPadding = PaddingValues(0.dp),
                            ) {
                            Icon(
                                contentDescription = "add text",
                                imageVector = Icons.Outlined.KeyboardArrowUp,
                                tint = if (darktheme == 1|| darktheme == 3) Color.White else Color.Black,
                                modifier = Modifier.fillMaxSize(1.0F)
                            )
                        }
                    } else if (i == 0) {
                        Button(
                            modifier = Modifier.padding(top = 1.dp, bottom = 3.dp)
                                .size(width = 20.dp, height = 20.dp),
                            onClick = {
                                tomorrowsList.swapMine(i, end)
                            },
                            contentPadding = PaddingValues(0.dp),
                            ) {
                            Icon(
                                contentDescription = "add text",
                                imageVector = Icons.Outlined.KeyboardArrowUp,
                                tint = if (darktheme == 1|| darktheme == 3) Color.White else Color.Black,
                                modifier = Modifier.fillMaxSize(1.0F)
                            )
                        }
                    }
                }
                Spacer(Modifier.size(18.dp))
            }
        }
    }




}

