import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.util.*

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun CustomDialog(
    message: MutableState<String>,
    openDialog: MutableState<Boolean>,
    editMessage: MutableState<String>,
    today: SnapshotStateList<Tisk>,
    tomorrow: SnapshotStateList<Tisk>,
    darktheme: Int
) {

    var descriptionText by remember { mutableStateOf(TextFieldValue("")) }
    var headerText by remember { mutableStateOf(TextFieldValue("")) }
    val maxChar = 150
    val maxCharDescript = 500
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(brush = if (darktheme == 1) gradient else if (darktheme == 2) gradientLight else gradientEnergetic, shape = RoundedCornerShape(20.dp))
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "Input Message",
                color = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))

            //Header
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
                        color = if (darktheme == 1) Color.Gray else Color.Black,
                        fontSize = 15.sp
                    )
                },
                placeholder = {
                    Text(
                        text = "HEADER NAME",
                        color = if (darktheme == 1) Color.Gray else Color.Black,
                        fontSize = 23.sp
                    )
                },
                shape = RoundedCornerShape(8f),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black
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
                            tint = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black
                        )
                    }
                }

            )
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
                        color = if (darktheme == 1) Color.Gray else Color.Black,
                    )
                },
                placeholder = {
                    Text(
                        text = "TODAY'S TISK",
                        color = if (darktheme == 1) Color.Gray else Color.Black
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

        var myExpanded by remember { mutableStateOf(false) }
        val mycolors = user.filters
        var myTag by remember { mutableStateOf("") }

        var myExpanded2 by remember { mutableStateOf(false) }
        val priority = user.priorities
        var myPriority by remember { mutableStateOf("") }

        val nowyear = LocalDateTime.now().year.toString()
        val nowmonth = LocalDateTime.now().monthValue.toString()
        val nowdate = LocalDateTime.now().dayOfMonth.toString()
        var yearText by remember { mutableStateOf(TextFieldValue(nowyear)) }
        var monthText by remember { mutableStateOf(TextFieldValue(nowmonth)) }
        var dayText by remember { mutableStateOf(TextFieldValue(nowdate)) }


        //var mTextFieldSize by remember { mutableStateOf(Size.Zero)}
        val icon = if (myExpanded)
            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown

        val icon2 = if (myExpanded2)
            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown

        Row {
            //Outlined
            TextField(
                value = myTag,
                onValueChange = { myTag = it },
                modifier = Modifier
                    .height(65.dp)
                    .width(125.dp)
                    .padding(8.dp),
                //.onGloballyPositioned { coordinates ->
                // This value is used to assign to
                // the DropDown the same width
                //    mTextFieldSize = 20.dp
                //},
                colors = TextFieldDefaults.textFieldColors(
                    textColor = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black,
                    //focusedLabelColor = Color.White,
                ),
                label = {
                    Text("Tag", color = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black)
                },
                trailingIcon = {
                    Icon(
                        icon, "contentDescription",
                        Modifier.clickable { myExpanded = !myExpanded },
                        tint = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black
                    )
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

            TextField(
                value = myPriority,
                onValueChange = { myPriority = it },
                modifier = Modifier
                    .height(65.dp)
                    .width(148.dp)
                    .padding(8.dp),
                //.onGloballyPositioned { coordinates ->
                // This value is used to assign to
                // the DropDown the same width
                //    mTextFieldSize = 20.dp
                //},
                colors = TextFieldDefaults.textFieldColors(
                    textColor = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black,
                    //focusedLabelColor = Color.White,
                ),
                label = { Text("Priority", color = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black) },
                trailingIcon = {
                    Icon(
                        icon2, "contentDescription",
                        Modifier.clickable { myExpanded2 = !myExpanded2 },
                        tint = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black
                    )
                }
            )
            //priority drop down
            DropdownMenu(
                expanded = myExpanded2,
                onDismissRequest = { myExpanded2 = false }
            ) {
                priority.forEach { label ->
                    DropdownMenuItem(onClick = {
                        myPriority = label
                        myExpanded2 = false
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
            modifier = Modifier.align(Alignment.End)
        ) {

            Button(
                onClick = {
                    val duedate = String.format("%02d", monthText.text.toInt()) + "-" + String.format("%02d", dayText.text.toInt()) + "-" + yearText.text
                    val task = Tisk(
                        uuid = UUID.randomUUID().toString(),
                        tag = if (myTag != "") myTag else "untagged",
                        priority = if (myPriority != "") myPriority else "1",
                        description = descriptionText.text,
                        text = headerText.text,
                        duedate = duedate
                    )

                    if (!user.filters.contains(myTag)) {
                        user.filters.add(myTag)
                    }
                    if (!user.priorities.contains(myPriority)) {
                        user.priorities.add(myPriority)
                    }
                    runBlocking {
                        launch {
                            daodb.addTisk(task)
                            if (signedInState){
                                user.syncToDB()
                            }
                        }

                    }
                    if (duedate == current){
                        today.add(task)
                    } else if (duedate == nextday){
                        tomorrow.add(task)
                    }
                    descriptionText = TextFieldValue("")
                    headerText = TextFieldValue("")

                }
            ) {
                Text("Save")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    openDialog.value = false
                }
            ) {
                Text("Cancel")
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}


