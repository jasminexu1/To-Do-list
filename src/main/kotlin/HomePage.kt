package pages

import CalendarField
import SimpleTextField
import TaskField
import Tisk
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import daodb
import kotlinx.coroutines.runBlocking
import navcommander.NavCommander
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")

val current = LocalDateTime.now().format(formatter)
val nextday = LocalDateTime.now().plusDays(1).format(formatter)

@Composable
fun HomePage(
    navCommander: NavCommander,
    darktheme: Int
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row( modifier = Modifier.padding(start = 78.dp)) {

            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Column {
//                val today = remember {
//                    db.getItemsDueDate(current)?.Items?.toMutableStateList() ?: mutableStateListOf<Tisk>()
//                }
                var today : MutableList<Tisk>
                var tomorrow : MutableList<Tisk>
                runBlocking {
                    today = daodb.getTisksByDueDate(current).toMutableStateList()
                    tomorrow = daodb.getTisksByDueDate(nextday).toMutableStateList()
                }
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                SimpleTextField(
                    today as SnapshotStateList<Tisk>,
                    tomorrow as SnapshotStateList<Tisk>,
                    darktheme
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Row {
                    TaskField(today as SnapshotStateList<Tisk>, tomorrow as SnapshotStateList<Tisk>, darktheme)
		            CalendarField(darktheme)
                }
            }
        }
    }
}


