
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import kotlin.math.sin


@Composable
fun showYearMonth(year: Int, month: Int) {
    val listMonth = mutableListOf("January","February","March","April","May","June",
                                    "July","August","September","October","November","December")
    val mymonth = listMonth[month-1]
    Text("$year $mymonth",
        textAlign = TextAlign.Center,
        fontSize = 40.sp)
}


@Composable
fun numDays(year: Int, month: Int): Int {
    if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 ||
        month == 10 || month == 12
    ) {
        return 31
    } else if (month == 4 || month == 6 || month == 9 || month == 11) {
        return 30
    } else {
        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
            return 29
        } else {
            return 28
        }
    }
}

@Composable
fun dayInWeek(year: Int, ourmonth: Int): Int {
    var month = ourmonth
    var yearList = listOf<Int>(0,3,2,5,0,3,5,1,4,6,2,4)
    var result = year
    if (month < 3) {
        result = result - 1
    }
    return (result + result/4 - result/100 + result/400 + yearList[month - 1] + 1) % 7
}


@Composable
fun CalendarField(darktheme: Int) {

    val curYear = LocalDateTime.now().year.toString()
    val curMonth = LocalDateTime.now().monthValue.toString()
    var yearInt by remember { mutableStateOf(curYear.toInt()) }
    var monthInt by remember { mutableStateOf(curMonth.toInt()) }

    val show = remember { mutableStateOf(false) }
    val nowyear = LocalDateTime.now().year
    val nowmonth = LocalDateTime.now().monthValue

    Column (horizontalAlignment = Alignment.CenterHorizontally) {
        if (!show.value) {
            showYearMonth(nowyear, nowmonth)
        } else {
            showYearMonth(yearInt, monthInt)
        }
        Row {
            var yearText by remember { mutableStateOf(TextFieldValue(curYear)) }
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            TextField(
                value = yearText,
                onValueChange = {
                    yearText = it
                },
                modifier = Modifier.fillMaxWidth(.4f),
                label = { Text(text = "Year") },
                placeholder = { Text(text = "Year") },
                singleLine = true
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            var monthText by remember { mutableStateOf(TextFieldValue(curMonth)) }
            TextField(
                value = monthText,
                onValueChange = {
                    monthText = it
                },
                modifier = Modifier.fillMaxWidth(4 / 6f),
                label = { Text(text = "Month") },
                placeholder = { Text(text = "Month") },
                singleLine = true
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Button(
                onClick = {
                    try {
                        val parsedYear = yearText.text.toInt()
                        val parsedMonth = monthText.text.toInt()

                        yearInt = parsedYear
                        monthInt = parsedMonth
                        show.value = true

                    } catch (nfe: NumberFormatException) {
                        // not a valid int
                    }
                },
                modifier = Modifier.fillMaxWidth(0.88f).height(55.dp)
                //.background(color = Color(0xff2A2A29))
            ) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Icon",
                    tint = Color.White
                )
            }
        }
        CalendarGrid(yearInt, monthInt, darktheme)
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun CalendarGrid(year: Int, month: Int, darktheme: Int) {
    val daysInMonth = numDays(year, month)
    val list = (1..daysInMonth).map { it.toString() }
    val openDialog = remember { mutableStateOf(false)  }
    var dayOfMonth = remember { mutableStateOf(0)  }
    var dayOfMonth2 = remember { mutableStateOf(0)  }
    val start = dayInWeek(year,month)
    val listempty = (1..start).map { it.toString() }
    val listday = mutableListOf<String>("Sun","Mon","Tue","Wed","Thu","Fri","Sat")

    LazyVerticalGrid(
        cells = GridCells.Fixed(7),
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        // content padding
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        content = {
            items(listday.size) { index ->
                Card(
                    backgroundColor = if (darktheme == 1) Color.DarkGray else if (darktheme == 3)Color(0xff560bbc) else Color(0xffa18cd2),
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    elevation = 8.dp,
                    onClick = {
                        dayOfMonth.value = 0
                        openDialog.value = false

                    }
                ) {
                    Text(
                        text = listday[index],
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = if (darktheme == 1|| darktheme == 3) Color(0xFFFFFFFF) else Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            items(listempty.size) { index ->
                Card(
                    backgroundColor = if (darktheme == 1) Color.Gray else if (darktheme == 3) Color(0xff864bd7) else Color(0xffa18cd2),
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    elevation = 8.dp,
                    onClick = {
                        dayOfMonth.value = 0
                        openDialog.value = false

                    }
                ) {
                    Text(
                        text = "",
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = if (darktheme == 1|| darktheme == 3) Color(0xFFFFFFFF) else Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            items(list.size) { index ->
                Card(
                    backgroundColor = if (darktheme == 1) Color.Gray else if (darktheme == 3) Color(0xff864bd7) else Color(0xfffbc2ec),
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    elevation = 8.dp,
                    onClick = {
                        dayOfMonth.value = index + 1
                        openDialog.value = true

                    }
                ) {
                    dayOfMonth2.value = index + 1
                    val dayCalander = String.format("%02d", month) + "-" + String.format("%02d", dayOfMonth2.value) + "-" + year
                    var dayTaskList : MutableList<Tisk>
                    runBlocking {
                        dayTaskList = daodb.getTisksByDueDate(dayCalander).toMutableStateList()
                    }
                    Text(
                        text = list[index],
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = if (dayTaskList.size > 0) {
                            if (darktheme == 1 || darktheme == 3) Color(0xff2A2A29) else Color(0xffa18cd2)
                        } else {
                            if (darktheme == 1 || darktheme == 3) Color(0xFFFFFFFF) else Color.Black
                        },
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    )

    if (openDialog.value){
        val day = String.format("%02d", month) + "-" + String.format("%02d", dayOfMonth.value) + "-" + year
//        val dayTaskList = remember {
//            db.getItemsDueDate(day)?.Items?.toMutableStateList() ?: mutableStateListOf<Tisk>()
//        }
        var dayTaskList : MutableList<Tisk>
        runBlocking {
            dayTaskList = daodb.getTisksByDueDate(day).toMutableStateList()
        }
        calendarDialog (setShowDialog = {
            openDialog.value = it
        }, dayTaskList as SnapshotStateList<Tisk>, day, darktheme)
    }
}

fun isNumeric(str: String?) = str?.toIntOrNull()?.let { true } ?: false


