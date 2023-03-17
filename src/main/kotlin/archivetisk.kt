
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun archiveTisks(
    uuid: String,
    tag: String,
    priority: String,
    text: String,
    duedate: String,
    tiskList: SnapshotStateList<Tisk>,
    tisk: Tisk,
    description: String,
    darktheme: Int
) {
    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier.clickable {
            isExpanded = !isExpanded
        },
        backgroundColor = Color.Transparent
    ) {
        var headerText by remember { mutableStateOf(TextFieldValue(text)) }
        var descriptionText by remember { mutableStateOf(TextFieldValue(description)) }
        val maxChar = 150
        val maxCharDescript = 500

        var myExpanded by remember { mutableStateOf(false) }
        val mycolors = user.filters
        var myTag by remember { mutableStateOf(tag) }

        var myExpandedPriority by remember { mutableStateOf(false) }
        val priorityList = user.priorities
        var myPriority by remember { mutableStateOf(priority) }

        val myDuedate = duedate.split("-")
        val nowyear = myDuedate[2]
        val nowmonth = myDuedate[0]
        val nowdate = myDuedate[1]
        var yearText by remember { mutableStateOf(TextFieldValue(nowyear)) }
        var monthText by remember { mutableStateOf(TextFieldValue(nowmonth)) }
        var dayText by remember { mutableStateOf(TextFieldValue(nowdate)) }
        Column(
            modifier = Modifier.fillMaxSize()
                .background(if (darktheme == 1) gradient else if (darktheme == 2) gradientLight else gradientEnergetic)
        ) {
            if (isExpanded) {

            } else {
                Row {
                    Button(
                        onClick = {
                            runBlocking {
                                tiskList.remove(tisk)
                                runBlocking {
                                    archivedb.deleteTisk(uuid)
                                    if (signedInState){
                                        user.syncToDB()
                                    }
                                }
                            }
                        },
                        // Uses ButtonDefaults.ContentPadding by default
                        contentPadding = PaddingValues(
                            start = 5.dp,
                            top = 5.dp,
                            end = 5.dp,
                            bottom = 5.dp
                        ),
                        modifier = Modifier.height(20.dp).width(20.dp)
                    ) {}
                    Text(
                        headerText.text,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = if (darktheme == 1|| darktheme == 3) Color.White else Color.Black,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }
            androidx.compose.animation.AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(brush = if (darktheme == 1) gradient else if (darktheme == 2) gradientLight else gradientEnergetic, shape = RoundedCornerShape(20.dp))
                        .padding(8.dp),
                ) {
                    Column {
                        Row {
                            Button(
                                onClick = {
                                    runBlocking {
                                        tiskList.remove(tisk)
                                        launch {
                                            archivedb.deleteTisk(uuid)
                                            if (signedInState){
                                                user.syncToDB()
                                            }
                                        }
                                    }

                                },
                                // Uses ButtonDefaults.ContentPadding by default
                                contentPadding = PaddingValues(
                                    start = 5.dp,
                                    top = 5.dp,
                                    end = 5.dp,
                                    bottom = 5.dp
                                ),
                                modifier = Modifier.height(20.dp).width(20.dp)
                                    .align(Alignment.CenterVertically)
                            ) {}
                            TextField(
                                value = headerText,
                                onValueChange = {
                                    if (it.text.length <= maxChar) {
                                        headerText = it
                                    }
                                },
                                modifier = Modifier.background(
                                    brush = if (darktheme == 1) gradient else if (darktheme == 2) gradientLight else gradientEnergetic,

                                    )
                                    .onKeyEvent { keyEvent ->
                                        if (keyEvent.key == Key.Enter) {
                                            // have to have this check or else dialog will close
                                        }
                                        true
                                    }
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp),
                                label = {
                                    Text(
                                        text = "HEADER",
                                        color = if (darktheme == 1|| darktheme == 3) Color.White else Color.Black,
                                        fontSize = 15.sp
                                        //color = Color.Black
                                    )
                                },
                                placeholder = {
                                    Text(
                                        text = "HEADER NAME",
                                        color = if (darktheme == 1|| darktheme == 3) Color.White else Color.Black,
                                        fontSize = 23.sp
                                        //color = Color.Black
                                    )
                                },
                                shape = RoundedCornerShape(8f),
                                singleLine = true,
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = if (darktheme == 1|| darktheme == 3) Color.White else Color.Black
                                ),
                                trailingIcon = {
                                    if (headerText.text.isNotEmpty()) {

                                        Icon(
                                            contentDescription = "clear text",
                                            imageVector = Icons.Outlined.Close,
                                            tint = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black,
                                            modifier = Modifier
                                                .clickable {
                                                    headerText = TextFieldValue("")
                                                }
                                        )
                                    } else {
                                        Icon(
                                            contentDescription = "add text",
                                            imageVector = Icons.Filled.Edit,
                                            tint = if (darktheme == 1|| darktheme == 3) Color.White else Color.Black
                                        )
                                    }
                                }
                            )
                        }
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp, end = 25.dp),
                            textAlign = TextAlign.End,
                            text = "${headerText.text.length}  / $maxChar",
                            color = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black,
                            maxLines = 1
                        )
                        //Tisk
                        TextField(
                            value = descriptionText,
                            onValueChange = {
                                if (it.text.length <= maxCharDescript) {
                                    descriptionText = it
                                }
                            },
                            modifier = Modifier.background(
                                brush = if (darktheme == 1) gradient else if (darktheme == 2) gradientLight else gradientEnergetic,
                            )
                                .onKeyEvent { keyEvent ->
                                    if (keyEvent.key == Key.Enter) {
                                        // have to have this check or else dialog will close
                                    }
                                    true
                                }
                                .fillMaxWidth()
                                .fillMaxHeight(0.5f)
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            label = {
                                Text(
                                    text = "TISK",
                                    color = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black,
                                    //color = Color.Black
                                )
                            },
                            placeholder = {
                                Text(
                                    text = "TODAY'S TISK",
                                    color = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black
                                    //color = Color.Black
                                )
                            },
                            shape = RoundedCornerShape(8f),
                            singleLine = false,
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black
                            ),
                            trailingIcon = {
                                if (descriptionText.text.isNotEmpty()) {
                                    Icon(
                                        contentDescription = "clear text",
                                        imageVector = Icons.Outlined.Close,
                                        tint = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black,
                                        modifier = Modifier
                                            .clickable {
                                                descriptionText = TextFieldValue("")
                                            }
                                    )
                                } else {
                                    Icon(
                                        contentDescription = "add text",
                                        imageVector = Icons.Filled.Edit,
                                        tint = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black
                                    )
                                }
                            }
                        )
                    }
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp, end = 25.dp),
                        textAlign = TextAlign.End,
                        text = "${descriptionText.text.length}  / $maxCharDescript",
                        color = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black,
                        maxLines = 1
                    )
                    //var mTextFieldSize by remember { mutableStateOf(Size.Zero)}
                    val icon = if (myExpanded)
                        Icons.Filled.KeyboardArrowUp
                    else
                        Icons.Filled.KeyboardArrowDown
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        //Outlined
                        TextField(
                            value = myTag,
                            onValueChange = { myTag = it },
                            modifier = Modifier
                                .height(50.dp)
                                .width(150.dp)
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
                            label = { Text(tag, color = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black)},
                            trailingIcon = {
                                Icon(icon, "contentDescription",
                                    Modifier.clickable { myExpanded = !myExpanded })
                            }
                        )
                        DropdownMenu(
                            expanded = myExpanded,
                            onDismissRequest = { myExpanded = false }
                        ) {
                            mycolors.forEach { label ->
                                DropdownMenuItem(onClick = {
                                    myTag = label
                                    myExpanded = false
                                }) {
                                    Text(text = label)
                                }
                            }
                        }
                        val icon2 = if (myExpandedPriority)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown
                        TextField(
                            value = myPriority,
                            onValueChange = { myPriority = it },
                            modifier = Modifier
                                .height(50.dp)
                                .width(100.dp)
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
                            label = { Text(priority, color = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black) },
                            trailingIcon = {
                                Icon(icon2, "contentDescription",
                                    Modifier.clickable { myExpandedPriority = !myExpandedPriority })
                            }
                        )
                        //priority drop down
                        DropdownMenu(
                            expanded = myExpandedPriority,
                            onDismissRequest = { myExpandedPriority = false }
                        ) {
                            priorityList.forEach { label ->
                                DropdownMenuItem(onClick = {
                                    myPriority = label
                                    myExpandedPriority = false
                                }) {
                                    Text(text = label)
                                }
                            }
                        }

                        val openDatePicker = remember { mutableStateOf(false) }
                        Button(
                            modifier = Modifier
                                .height(65.dp)
                                .width(125.dp)
                                .padding(8.dp),
                            onClick = {
                                openDatePicker.value = true
                            }) {
                            //Text("Date Picker")
                            Icon(
                                imageVector = Icons.Filled.DateRange,
                                contentDescription = "Icon",
                                tint = Color.White
                            )
                        }
                        if (openDatePicker.value) {
                            AlertDialog(
                                onDismissRequest = {
                                    openDatePicker.value = false
                                },
                                text = {
                                    val year = remember { mutableStateOf(nowyear) }
                                    val month = remember { mutableStateOf(nowmonth) }
                                    val day = remember { mutableStateOf(nowdate) }
                                    val show = remember { mutableStateOf(false) }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Row {
                                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))

                                            TextField(
                                                value = yearText,
                                                onValueChange = {
                                                    yearText = it
                                                },
                                                modifier = Modifier.fillMaxWidth(.25f),
                                                label = { Text(text = "Year") },
                                                placeholder = { Text(text = "Input Year") },
                                            )
                                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                            TextField(
                                                value = monthText,
                                                onValueChange = {
                                                    monthText = it
                                                },
                                                modifier = Modifier.fillMaxWidth(1 / 3f),
                                                label = { Text(text = "Month") },
                                                placeholder = { Text(text = "Input Month") },
                                            )
                                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                            TextField(
                                                value = dayText,
                                                onValueChange = {
                                                    dayText = it
                                                },
                                                modifier = Modifier.fillMaxWidth(1 / 2f),
                                                label = { Text(text = "Day") },
                                                placeholder = { Text(text = "Input Day") },
                                            )
                                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                            var yearrecord = "2022"
                                            var monthrecord = "11"
                                            var dayrecord = "20"
                                            var entered by remember { mutableStateOf(false) }

                                            Button(
                                                onClick = {
                                                    entered = !entered
                                                    yearrecord = year.value
                                                    monthrecord = month.value
                                                    dayrecord = day.value
                                                    year.value = yearText.text
                                                    month.value = monthText.text
                                                    day.value = dayText.text
                                                    if (yearText == TextFieldValue("") || monthText == TextFieldValue(
                                                            ""
                                                        )
                                                        || monthText == TextFieldValue("") || !isNumeric(day.value)
                                                        || !isNumeric(year.value) || !isNumeric(month.value)
                                                    ) {
                                                        if (yearrecord == "") {
                                                            year.value = nowyear
                                                            month.value = nowmonth
                                                            day.value = nowdate
                                                        } else {
                                                            year.value = yearrecord
                                                            month.value = monthrecord
                                                            day.value = dayrecord
                                                        }
                                                    } else if (month.value.toInt() < 1 || month.value.toInt() > 12 || year.value.toInt() < 1 || year.value.toInt() > 9999) {
                                                        year.value = nowyear
                                                        month.value = nowmonth
                                                    }
                                                    if ((month.value.toInt() == 1 || month.value.toInt() == 3 || month.value.toInt() == 5 || month.value.toInt() == 7 ||
                                                                month.value.toInt() == 8 || month.value.toInt() == 10 || month.value.toInt() == 12) && (day.value.toInt() < 1 || day.value.toInt() > 31)
                                                    ) {
                                                        day.value = nowdate
                                                    } else if ((month.value.toInt() == 4 || month.value.toInt() == 6 || month.value.toInt() == 9 || month.value.toInt() == 11)
                                                        && (day.value.toInt() < 1 || day.value.toInt() > 30)
                                                    ) {
                                                        day.value = nowdate
                                                    } else if (month.value.toInt() == 2) {
                                                        if ((year.value.toInt() % 4 == 0 && year.value.toInt() % 100 != 0) &&
                                                            (day.value.toInt() < 0 || day.value.toInt() > 29)
                                                        ) {
                                                            day.value = nowdate
                                                        } else if ((year.value.toInt() % 4 != 0 || year.value.toInt() % 100 == 0) &&
                                                            (day.value.toInt() < 0 || day.value.toInt() > 28)
                                                        ) {
                                                            day.value = nowdate
                                                        }
                                                    }
                                                    openDatePicker.value = false
                                                },
                                                modifier = Modifier.fillMaxWidth(0.88f)
                                            ) {
                                                Text("Enter")
                                            }
                                        }
                                    }
                                },
                                buttons = {
                                    Row {
                                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                                        Button(
                                            onClick = { openDatePicker.value = false }
                                        ) {
                                            Text("Cancel")
                                        }
                                    }
                                }
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {

                        Button(
                            onClick = {
                                val newDuedate = monthText.text + "-" + dayText.text + "-" + yearText.text
                                val task = Tisk(
                                    uuid = uuid,
                                    tag = if (myTag != "") myTag else "untagged",
                                    priority = if (myPriority != "") myPriority else "1",
                                    description = descriptionText.text,
                                    text = headerText.text,
                                    duedate = newDuedate
                                )


                                if (!user.filters.contains(myTag)) {
                                    user.filters.add(myTag)
                                }
                                if (!user.priorities.contains(myPriority)) {
                                    user.priorities.add(myPriority)
                                }

                                runBlocking {
                                    tiskList.remove(tisk)
                                    isExpanded = false
                                    launch {
                                        archivedb.deleteTisk(uuid)
                                        daodb.addTisk(task)
                                        if (signedInState){
                                            user.syncToDB()
                                        }
                                    }

                                }
                            }
                        ) {
                            Text("Restore")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                isExpanded = false
                            }
                        ) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
    }
}



