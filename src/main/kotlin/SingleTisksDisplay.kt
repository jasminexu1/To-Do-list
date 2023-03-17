
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.runBlocking

@Composable
fun SingleTiskDisplay (todaysList: SnapshotStateList<Tisk>, title1: String, darktheme: Int, isFilter: Int){
    var myDelete by remember { mutableStateOf(false) }
    LazyColumn(
        Modifier
            .background(color = Color.Transparent)
            .fillMaxHeight(0.978f)
            .fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 25.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    title1,
                    style = MaterialTheme.typography.h4,
                    color = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black,
                )
            }
        }
        val priorityy = listOf<String>("1","2","3","4","5","6","7","8","9","10")
        if ((todaysList.isEmpty() && title1 != "red" && title1 != "yellow"
            && title1 !="blue" && title1 !="green" && title1 !="untagged" && isFilter == 1) ||
            (todaysList.isEmpty() && !(title1 in priorityy) && isFilter == 2)) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentHeight()
                        .padding(vertical = 25.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(.1f),
                        onClick = {
                            myDelete = true
                        },
                        contentPadding = PaddingValues(0.dp),
                    ) {
                        Icon(
                            contentDescription = "add text",
                            imageVector = Icons.Outlined.Close,
                            tint = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black,
                            modifier = Modifier.fillMaxSize(1.0F)
                        )
                    }
                    if (myDelete == true) {
                        globalChange = title1
                        globalDelete = true
                        myDelete = false
                    }
                }
            }
        }
        items(items = todaysList,
            key = {item: Tisk ->  item.uuid}){ tisk ->
            tisks(
                uuid = tisk.uuid,
                tag = tisk.tag,
                priority = tisk.priority,
                text = tisk.text,
                duedate = tisk.duedate,
                tiskList = todaysList,
                tisk = tisk,
                description = tisk.description,
                darktheme = darktheme
            )
        }
    }
}


