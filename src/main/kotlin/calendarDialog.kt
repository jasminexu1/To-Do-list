
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog



@Composable
fun calendarDialog(
    setShowDialog: (Boolean) -> Unit,
    dayTaskList: SnapshotStateList<Tisk>,
    day: String,
    darktheme: Int
) {
    Dialog(onCloseRequest = {setShowDialog(false)}) {
        Surface (shape = RoundedCornerShape(16.dp),
            color = if (darktheme == 1) Color(0xff2A2A29) else if (darktheme == 2) Color.White else Color(0xff560bbc),
            modifier = Modifier.padding(top = 10.dp, end = 10.dp, start = 10.dp, bottom = 10.dp)
                ,){
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()){
                Column (modifier = Modifier.fillMaxHeight()){
                    Row (modifier = Modifier.fillMaxWidth()){
                        SingleTiskDisplay(dayTaskList, day, darktheme, 0)
                    }
                }
            }
        }

    }
}
