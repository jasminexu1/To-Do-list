import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SimpleTextField(today: SnapshotStateList<Tisk>, tomorrow: SnapshotStateList<Tisk>, darktheme: Int) {
    val message = remember { mutableStateOf("Edit Me") }

    val openDialog = remember { mutableStateOf(false) }
    val editMessage = remember { mutableStateOf("") }

    var text by remember { mutableStateOf(TextFieldValue("")) }
    val maxChar = 150
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(.976f)
                .background(
                    brush = if (darktheme == 1) gradient else if (darktheme == 2) gradientLight else gradientEnergetic, shape = RoundedCornerShape(20.dp)
                )
                .padding(end = 8.dp)
        ) {
            TextField(
                value = text,
                onValueChange = {
                    if (it.text.length <= maxChar) {
                        text = it
                    }
                },
                modifier = Modifier.background(
                    brush = if (darktheme == 1) gradient else if (darktheme == 2) gradientLight else gradientEnergetic, shape = RoundedCornerShape(20.dp)
                )
                    .onKeyEvent { keyEvent ->
                        if (keyEvent.key == Key.Enter && keyEvent.type == KeyEventType.KeyUp) {
                            text = TextFieldValue(
                                text.text
                                    .replace("\n", "")
                                    .replace("\r", "")
                            )
                            val task = Tisk(
                                uuid = UUID.randomUUID().toString(),
                                tag = "untagged",
                                priority = "1",
                                description = "",
                                text = text.text,
                                duedate = current
                            )
                            runBlocking {
                                launch {
                                    daodb.addTisk(task)
                                    if (signedInState){
                                        user.syncToDB()
                                    }
                                }
                                today.add(task)
                            }

                            text = TextFieldValue("")
                        }
                        true
                    }
                    .fillMaxWidth(0.90f)
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                label = {
                    Text(
                        text = "TISK",
                        color = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black
                    )
                },
                placeholder = {
                    Text(
                        text = "TODAY'S TISK",
                        color = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black
                    )
                },
                shape = RoundedCornerShape(8f),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black
                ),
                trailingIcon = {
                    if (text.text.isNotEmpty()) {
                        Icon(
                            contentDescription = "clear text",
                            imageVector = Icons.Outlined.Close,
                            tint = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black,
                            modifier = Modifier
                                .clickable {
                                    text = TextFieldValue("")
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

            Column {
                Button(
                    modifier = Modifier.align(Alignment.End)
                        .padding(top = 4.dp, end = 8.dp),
                    onClick = {
                        editMessage.value = message.value
                        openDialog.value = true
                    }
                ) {
                    Icon(
                        contentDescription = "add text",
                        imageVector = Icons.Outlined.Add,
                        tint = Color.White
                    )
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, end = 25.dp),
                    textAlign = TextAlign.End,
                    text = "${text.text.length}  / $maxChar",
                    color = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black,
                    maxLines = 1
                )
            }
            Spacer(modifier = Modifier.width(9.dp))
        }
    }
    if (openDialog.value) {
        Column {
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Box(

                    modifier = Modifier
                        .padding(end = 8.dp)
                        .fillMaxSize()
                        .background(
                            color = Color.White
                                .copy(alpha = 0.6f)
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                openDialog.value = false
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    CustomDialog(message, openDialog, editMessage, today, tomorrow, darktheme)
                }
            }
        }

    }
}
