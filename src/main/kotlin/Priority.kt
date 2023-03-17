
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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


@Composable
fun TaskFieldPriority(priorityStateList: SnapshotStateList<Tisk>, darktheme : Int) {
    var myExpanded by remember { mutableStateOf(false) }
    val priority = user.priorities
    if (globalDelete) {
        user.priorities.remove(globalChange)
        globalDelete = false
        globalChange = ""
    }
    var myPriority by remember { mutableStateOf("Priority") }

    val icon = if (myExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


    Column {
        Row {
            Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                SingleTiskDisplay(priorityStateList, myPriority, darktheme,2)
            }

            TextField(
                value = myPriority,
                onValueChange = { myPriority = it },
                modifier = Modifier
                    .height(60.dp)
                    .width(200.dp)
                    .padding(top = 8.dp),

                //.onGloballyPositioned { coordinates ->
                // This value is used to assign to
                // the DropDown the same width
                //    mTextFieldSize = 20.dp
                //},
                colors = TextFieldDefaults.textFieldColors(
                    textColor = if (darktheme == 1|| darktheme == 3) Color.White else Color.Black,
                    //focusedLabelColor = Color.White,
                    backgroundColor = if (darktheme == 1) Color.Gray else if (darktheme == 2) Color.Gray else Color(0xff864bd7)
                ),
                label = {
                    Text(
                        text = "Priority",
                        color = if (darktheme == 1|| darktheme == 3) Color.White else Color.Black
                    )

                },

                trailingIcon = {
                    Icon(icon, "contentDescription",
                        Modifier.clickable { myExpanded = !myExpanded },
                        tint = if (darktheme == 1|| darktheme == 3) Color.White else Color.Black
                    )
                }
            )

            Box(
                contentAlignment = Alignment.Center,

            ) {


                DropdownMenu(
                    expanded = myExpanded,
                    onDismissRequest = { myExpanded = false },


                ) {
                    priority.forEach { label ->
                        DropdownMenuItem(onClick = {
//                            val newArrayList = db.getItemsPriority(label)?.Items
                            val newArrayList : MutableList<Tisk>
                            runBlocking {
                                newArrayList = daodb.getTisksByPriority(label)
                            }
                            priorityStateList.removeAll(priorityStateList)
                            priorityStateList.addAll(newArrayList)
                            myPriority = label
                            myExpanded = false
                        }) {
                            Text(text = label)
                        }
                    }
                }
            }

        }

    }
}


@Composable
fun Priority(
    navCommander: NavCommander,
    darktheme: Int
) {
    Row(modifier = Modifier.padding(start = 90.dp, top = 40.dp, bottom = 40.dp, end = 20.dp)) {
//        val sortedPriorityList = remember {
//            db.getItemsSortedPriority(current)?.Items?.toMutableStateList()
//                ?: mutableStateListOf<Tisk>()
//        }
        var sortedPriorityList : MutableList<Tisk>
        runBlocking {
            val sortedPriorities = daodb.allTisks().sortedBy { it.priority }
            sortedPriorityList = sortedPriorities.toMutableStateList()
        }

        Column(
            modifier = Modifier.fillMaxHeight(1F).fillMaxWidth(0.9F)
                .background(
                    brush = if (darktheme == 1) gradient else if (darktheme == 2) gradientLight else gradientEnergetic, shape = RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 0.dp,
                        bottomStart = 20.dp,
                        bottomEnd = 0.dp
                    )
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Text(navCommander.currPage.value)

            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            TaskFieldPriority(sortedPriorityList as SnapshotStateList<Tisk>, darktheme)
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            // SingleTiskDisplay(today, "Priority")
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(.5F).background(
                brush = if (darktheme == 1) gradient else if (darktheme == 2) gradientLight else gradientEnergeticEnd, shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            ).fillMaxHeight(1F).padding(top = 120.dp)

        ) {
            val beg = 0

            val len = sortedPriorityList.size
            item {
                for (i in sortedPriorityList.indices) {
                    if (len - 1 == i) {


                        Button(
                            modifier = Modifier.padding(top = 1.dp, bottom = 3.dp)
                                .size(width = 20.dp, height = 20.dp),


                            //modifier = Modifier.animateItemPlacement(),
                            onClick = {
                                sortedPriorityList.swapMine(i, beg)
                                /*val tmp = beg
                    beg = index1
                    index1 = tmp*/
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

                            //modifier = Modifier.animateItemPlacement(),
                            onClick = {
                                sortedPriorityList.swapMine(i, i + 1)


                                /*val tmp2 = index2
                    index2 = index1
                    index1 = tmp2*/
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
                modifier = Modifier.fillMaxWidth(1F).background(
                    brush = if (darktheme == 1) gradient else if (darktheme == 2) gradientLight else gradientEnergeticEnd, shape = RoundedCornerShape(
                        topEnd = 20.dp, bottomEnd = 20.dp
                    )
                ).fillMaxHeight(1f).padding(top = 120.dp)

            ) {
                var beg = 0
                val end = sortedPriorityList.size - 1
                val len = sortedPriorityList.size
                item {
                    for (i in sortedPriorityList.indices) {
                        if (i >= 1) {


                            Button(
                                modifier = Modifier.padding(top = 1.dp, bottom = 3.dp)
                                    .size(width = 20.dp, height = 20.dp),


                                //modifier = Modifier.animateItemPlacement(),
                                onClick = {
                                    sortedPriorityList.swapMine(i, i - 1)
                                    /*val tmp = beg
                    beg = index1
                    index1 = tmp*/
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
                        else if (i == 0) {
                            Button(
                                modifier = Modifier.padding(top = 1.dp, bottom = 3.dp)
                                    .size(width = 20.dp, height = 20.dp),


                                //modifier = Modifier.animateItemPlacement(),
                                onClick = {
                                    sortedPriorityList.swapMine(i, end)
                                    /*val tmp = beg
                        beg = index1
                        index1 = tmp*/
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


