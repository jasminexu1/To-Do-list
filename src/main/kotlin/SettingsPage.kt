package pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gradient
import gradientEnergetic
import gradientLight
import kotlinx.coroutines.runBlocking
import navcommander.NavCommander
import settings
import signInDialog
import signUpDialog
import signedInState


//var GlobalThemeColourButtoninSettings: Boolean = false
var GlobalThemeColourButtoninSettings: Int = 1
@Composable
fun SettingPage(
    navCommander: NavCommander

) {
    val signUpDialog = remember { mutableStateOf(false)  }
    val signInDialog = remember { mutableStateOf(false)  }
    val signedInStateSettings = remember { mutableStateOf(settings.getBoolean("loggedInState", false))  }

    val darktheme = remember { mutableStateOf(1) }
    val i = 0;
    Column () {
        Row(modifier = Modifier.padding(start = 90.dp, top = 40.dp, bottom = 40.dp, end = 20.dp)) {
            Column(
                modifier = Modifier.fillMaxHeight(.5F).fillMaxWidth(0.5F)

                    .background(
                        brush = if (GlobalThemeColourButtoninSettings == 1) gradient else if
                                (GlobalThemeColourButtoninSettings == 2) gradientLight
                        else gradientEnergetic,
                        RoundedCornerShape(16.dp)
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {
                val colortheme: ArrayList<String> = arrayListOf("Dark", "Light", "NightSky")
                var myExpanded by remember { mutableStateOf(false) }
                var savecolor by remember { mutableStateOf("") }
                val icon = if (myExpanded)
                    Icons.Filled.KeyboardArrowUp
                else
                    Icons.Filled.KeyboardArrowDown
                Row {
                    Text(
                        modifier = Modifier.padding(15.dp),
                        text = "Current Colour Theme is: ",
                        fontSize = 25.sp,
                        color = if (GlobalThemeColourButtoninSettings == 1 || GlobalThemeColourButtoninSettings == 3) Color.White else Color.Black
                    )
                    if (GlobalThemeColourButtoninSettings == 1) {
                        Text(

                            "Dark",
                            modifier = Modifier.padding(15.dp),
                            fontSize = 25.sp,
                            color = if (GlobalThemeColourButtoninSettings == 1 || GlobalThemeColourButtoninSettings == 3) Color.White else Color.Black
                        )
                    } else if (GlobalThemeColourButtoninSettings == 2) {
                        Text(
                            "Light",
                            modifier = Modifier.padding(15.dp),
                            fontSize = 25.sp,
                            color = if (GlobalThemeColourButtoninSettings == 1 || GlobalThemeColourButtoninSettings == 3) Color.White else Color.Black
                        )
                    } else if (GlobalThemeColourButtoninSettings == 3) {
                        Text(
                            "NightSky",
                            modifier = Modifier.padding(15.dp),
                            fontSize = 25.sp,
                            color = if (GlobalThemeColourButtoninSettings == 1 || GlobalThemeColourButtoninSettings == 3) Color.White else Color.Black
                        )
                    }
                }
                Box(contentAlignment = Alignment.Center) {

                    TextField(
                        value = savecolor,
                        onValueChange = { savecolor = it },
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
                            textColor = if (GlobalThemeColourButtoninSettings == 1 || GlobalThemeColourButtoninSettings == 3) Color.White else Color.Black,
                            //focusedLabelColor = Color.White,
                            backgroundColor = if (GlobalThemeColourButtoninSettings == 2) Color.Gray else if (GlobalThemeColourButtoninSettings == 1) Color.Gray else Color(
                                0xff864bd7
                            )
                        ),
                        label = {
                            Text(
                                text = "Colour Theme",
                                color = if (GlobalThemeColourButtoninSettings == 1 || GlobalThemeColourButtoninSettings == 3) Color.White else Color.Black
                            )

                        },

                        trailingIcon = {
                            Icon(
                                icon, "contentDescription",
                                Modifier.clickable { myExpanded = !myExpanded },
                                tint = if (GlobalThemeColourButtoninSettings == 1 || GlobalThemeColourButtoninSettings == 3) Color.White else Color.Black
                            )
                        }
                    )
                    DropdownMenu(
                        expanded = myExpanded,
                        onDismissRequest = { myExpanded = false },
                    ) {
                        colortheme.forEach { label ->
                            DropdownMenuItem(onClick = {
//                            val newArrayList = db.getItemsPriority(label)?.Items
                                savecolor = label
                            }) {
                                Text(text = label)
                            }
                        }
                    }
                }
                if (savecolor == "Dark") {
                    GlobalThemeColourButtoninSettings = 1
                } else if (savecolor == "Light") {
                    GlobalThemeColourButtoninSettings = 2
                } else if (savecolor == "NightSky") {
                    GlobalThemeColourButtoninSettings = 3
                }
                /*Button(
            onClick = {
                GlobalThemeColourButtoninSettings = !GlobalThemeColourButtoninSettings
            }
        ) {
            Icon(
                contentDescription = "add text",
                imageVector = Icons.Outlined.Info,
                tint = Color.White
            )
        }*/
            }
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Column(
                modifier = Modifier.fillMaxHeight(.5F).fillMaxWidth()
                    .background(
                        brush = if (GlobalThemeColourButtoninSettings == 1) gradient
                        else if (GlobalThemeColourButtoninSettings == 2) gradientLight
                        else gradientEnergetic,
                        RoundedCornerShape(16.dp)
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Text(navCommander.currPage.value)
                if (signedInStateSettings.value) {
                    Button(onClick = {
                        signedInStateSettings.value = false
                        signedInState = signedInStateSettings.value
                        settings.clear()
                    }) {
                        Text(
                            text = "Log Out",
                            color = Color.White
                        )
                    }
                } else {
                    Button(onClick = {
                        signUpDialog.value = true
                    }) {
                        Text(
                            text = "Sign up",
                            color = Color.White
                        )
                    }

                    Button(onClick = {
                        runBlocking {
                            signInDialog.value = true
                        }

                    }) {
                        Text(
                            text = "Sign in",
                            color = Color.White
                        )
                    }
                }

            }

            if (signUpDialog.value) {
                signUpDialog(setShowDialog = {
                    signUpDialog.value = it
                }, darktheme = GlobalThemeColourButtoninSettings)
            }
            if (signInDialog.value) {
                signInDialog(setShowDialog = {
                    signInDialog.value = it
                }, setSignedInState = {
                    signedInStateSettings.value = it
                    signedInState = signedInStateSettings.value
                }, darktheme = GlobalThemeColourButtoninSettings)
            }

        }
        Row(modifier = Modifier.padding(start = 90.dp, top = 40.dp, bottom = 40.dp, end = 20.dp)) {
            LazyColumn(
                modifier = Modifier.fillMaxHeight().fillMaxWidth()
                    .background(
                        brush = if (GlobalThemeColourButtoninSettings == 1) gradient
                        else if (GlobalThemeColourButtoninSettings == 2) gradientLight
                        else gradientEnergetic,
                        RoundedCornerShape(16.dp)
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {


                    Text(
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 15.dp),
                        text = "This is a group project for CS346 group 202. We are building a to-do list app that has various features included. This app can be run on Windows/macOS/Linux. Some problems we want our app to solve for people are: multitasking; keeping track of what is due when; knowing how to budge your time; efficiency; organization.",
                        fontSize = 20.sp,
                        color = if (GlobalThemeColourButtoninSettings == 1 ||
                            GlobalThemeColourButtoninSettings == 3
                        ) Color.White else Color.Black
                    )
                }
            }
        }
    }
}

