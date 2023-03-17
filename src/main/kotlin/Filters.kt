
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.runBlocking
import navcommander.NavCommander
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.runtime.Composable


var globalDelete: Boolean = false
var globalChange: String = ""
@Composable
fun TaskFieldTags(filterStateList: SnapshotStateList<Tisk>, darktheme: Int) {
    var myExpanded by remember { mutableStateOf(false) }

    val mycolors = user.filters
    if (globalDelete) {
        user.filters.remove(globalChange)
        globalDelete = false
        globalChange = ""
    }
    var mySelectedText by remember { mutableStateOf("Filters") }

    val icon = if (myExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column {
        Row {
            Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                if (mySelectedText in mycolors) {
                    SingleTiskDisplay(filterStateList, mySelectedText, darktheme, 1)
                } else {
                    val newArrayList : MutableList<Tisk>
                    runBlocking {
                        newArrayList = daodb.getTisksByTag("untagged")
                        filterStateList.removeAll(filterStateList)
                    }
                    filterStateList.addAll(newArrayList)
                    mySelectedText = "untagged"
                    SingleTiskDisplay(filterStateList, mySelectedText, darktheme,1)
                }

            }
            TextField(
                value = mySelectedText,
                onValueChange = { mySelectedText = it },
                modifier = Modifier
                    .height(65.dp)
                    .width(200.dp)
                    .padding(8.dp),
                //.onGloballyPositioned { coordinates ->
                // This value is used to assign to
                // the DropDown the same width
                //    mTextFieldSize = 20.dp
                //},
                colors = TextFieldDefaults.textFieldColors(
                    textColor = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black,
                    //focusedLabelColor = Color.White,
                    backgroundColor = if (darktheme == 1) Color.Gray else if (darktheme == 2) Color.Gray else Color(0xff864bd7)
                ),
                label = {
                    Text(
                        text = "Tag",
                        color = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black
                    )
                },
                trailingIcon = {
                    Icon(icon, "contentDescription",
                        Modifier.clickable { myExpanded = !myExpanded },
                        tint = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black
                    )
                }
            )
            Box(
                contentAlignment = Alignment.Center
            ) {
                DropdownMenu(
                    expanded = myExpanded,
                    onDismissRequest = { myExpanded = false }

                ) {
                    mycolors.forEach { label ->
                        DropdownMenuItem(onClick = {
//                            val newArrayList = db.getItemsTag(label)?.Items
                            val newArrayList : MutableList<Tisk>
                            runBlocking {
                                newArrayList = daodb.getTisksByTag(label)
                                filterStateList.removeAll(filterStateList)
                            }
                            filterStateList.addAll(newArrayList)
                            mySelectedText = label
                            myExpanded = false
                        }) {
                            if (label == "red") {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = "Icon",
                                    tint = Color.Red
                                )
                            } else if (label == "yellow") {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = "Icon",
                                    tint = Color.Yellow
                                )
                            } else if (label == "blue") {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = "Icon",
                                    tint = Color.Blue
                                )
                            } else if (label == "green") {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = "Icon",
                                    tint = Color.Green
                                )
                            } else {
                                Text(text = label)
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun Filters(
    navCommander: NavCommander,
    darktheme: Int
) {
    Row(modifier = Modifier.padding(start = 90.dp, top = 40.dp, bottom = 40.dp, end = 20.dp)) {
        var filtersList : MutableList<Tisk>
//        val today = remember {
//            db.getItemsTag("untagged")?.Items?.toMutableStateList() ?: mutableStateListOf<Tisk>()
//        }
        runBlocking {
            filtersList = daodb.getTisksByTag("untagged").toMutableStateList()
        }
    Column(
        modifier = Modifier.fillMaxHeight().fillMaxWidth(0.9f)
            .background(brush = if (darktheme == 1) gradient else if (darktheme == 2) gradientLight else gradientEnergetic, shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 0.dp,
                bottomStart = 20.dp,
                bottomEnd = 0.dp
            )),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            TaskFieldTags(filtersList as SnapshotStateList<Tisk>, darktheme) // from archives
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(0.5F).background(brush = if (darktheme == 1) gradient else if (darktheme == 2) gradientLight else gradientEnergeticEnd, shape = RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 0.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            )).fillMaxHeight(1F).padding(top = 120.dp)

        ) {
            val beg = 0

            val len = filtersList.size
            item {
                for (i in filtersList.indices) {
                    if (len - 1 == i) {
                        Button(
                            modifier = Modifier.padding(top = 1.dp, bottom = 3.dp)
                                .size(width = 20.dp, height = 20.dp),
                            onClick = {
                                filtersList.swapMine(i, beg)
                            },
                            contentPadding = PaddingValues(0.dp),
                            ) {
                            Icon(
                                contentDescription = "add text",
                                imageVector = Icons.Outlined.KeyboardArrowDown,
                                tint = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black,
                                modifier = Modifier.fillMaxSize(1.0F)
                            )
                        }
                    } else {
                        Button(
                            modifier = Modifier.padding(top = 1.dp, bottom = 3.dp)
                                .size(width = 20.dp, height = 20.dp),
                            onClick = {
                                filtersList.swapMine(i, i + 1)
                            },
                            contentPadding = PaddingValues(0.dp),
                        ) {
                            Icon(
                                contentDescription = "add text",
                                imageVector = Icons.Outlined.KeyboardArrowDown,
                                tint = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black,
                                modifier = Modifier.fillMaxSize(1.0F)
                            )
                        }
                    }
                }
                Spacer(Modifier.size(18.dp))
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(1F).background(
                brush = if (darktheme == 1) gradient else if (darktheme == 2) gradientLight else gradientEnergeticEnd, shape = RoundedCornerShape(
                    topEnd = 20.dp, bottomEnd = 20.dp
                )
            ).fillMaxHeight(1f).padding(top = 120.dp)

        ) {
            var beg = 0
            var end = filtersList.size - 1
            val len = filtersList.size
            item {
                for (i in filtersList.indices) {
                    if (i >= 1) {
                        Button(
                            modifier = Modifier.padding(top = 1.dp, bottom = 3.dp)
                                .size(width = 20.dp, height = 20.dp),
                            onClick = {
                                filtersList.swapMine(i, i - 1)
                            },
                            contentPadding = PaddingValues(0.dp),
                            ) {
                            Icon(
                                contentDescription = "add text",
                                imageVector = Icons.Outlined.KeyboardArrowUp,
                                tint = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black,
                                modifier = Modifier.fillMaxSize(1.0F)
                            )
                        }
                    }
                    else if (i == 0) {
                        Button(
                            modifier = Modifier.padding(top = 1.dp, bottom = 3.dp)
                                .size(width = 20.dp, height = 20.dp),
                            onClick = {
                                filtersList.swapMine(i, end)
                            },
                            contentPadding = PaddingValues(0.dp),
                            ) {
                            Icon(
                                contentDescription = "add text",
                                imageVector = Icons.Outlined.KeyboardArrowUp,
                                tint = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black,
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

