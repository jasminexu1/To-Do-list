package pages

import Tisk
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import archiveTisks
import archivedb
import gradient
import gradientEnergetic
import kotlinx.coroutines.runBlocking
import gradientLight
import navcommander.NavCommander


@Composable
fun ArchiveDisplay (todaysList: SnapshotStateList<Tisk>, title: String, darktheme: Int){
    LazyColumn(
        Modifier
            .background(brush = if (darktheme == 1) gradient else if (darktheme == 2) gradientLight else gradientEnergetic, shape = RoundedCornerShape(20.dp))
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
                    title,
                    style = MaterialTheme.typography.h4,
                    color = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black
                    //color = Color.Black
                )
            }
        }
        items(items = todaysList,
            key = {item: Tisk ->  item.uuid}){ tisk ->
            archiveTisks(
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

@Composable
fun Archives(
    navCommander: NavCommander,
    darktheme: Int
) {
    Row(modifier = Modifier.padding(start = 78.dp)){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var archiveList : MutableList<Tisk>
            runBlocking {
                archiveList = archivedb.allTisks().toMutableStateList()
            }
            Column {
                ArchiveDisplay(archiveList as SnapshotStateList<Tisk>, "Deleted Tasks", darktheme)
            }
        }
    }
}

