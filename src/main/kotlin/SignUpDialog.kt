import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun signUpDialog(
    setShowDialog: (Boolean) -> Unit,
    darktheme: Int
) {
    Dialog(onCloseRequest = { setShowDialog(false) }) {
        var userName by remember { mutableStateOf(TextFieldValue("")) }
        var confirmCode by remember { mutableStateOf(TextFieldValue("")) }
        var confirmState by remember { mutableStateOf(false) }
        var password by remember { mutableStateOf(TextFieldValue("")) }
        var passwordVisible by rememberSaveable { mutableStateOf(false) }
        val maxChar = 150
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight().background(
                    brush = if (darktheme == 1) gradient else if (darktheme == 2) gradientLight else gradientEnergetic,
                )
            ) {
                if (confirmState) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Enter Confirmation Code",
                            fontSize = 25.sp,
                            color = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black
                        )
                        TextField(
                            value = confirmCode,
                            onValueChange = {
                                if (it.text.length <= maxChar) {
                                    confirmCode = it
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
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            placeholder = {
                                Text(
                                    text = "Confirmation Code",
                                    color = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black
                                    //color = Color.Black
                                )
                            },
                            shape = RoundedCornerShape(8f),
                            singleLine = false,
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black
                            )
                        )
                        Button(onClick = {
                            runBlocking {
                                launch {
                                    user.confirmSignUp(confirmCode.text, userName.text)
                                }
                                setShowDialog(false)
                            }

                        }) {
                            Text(
                                text = "Enter Code",
                                color = Color.White
                            )
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Sign Up",
                            fontSize = 25.sp,
                            color = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black
                        )
                        // username
                        TextField(
                            value = userName,
                            onValueChange = {
                                if (it.text.length <= maxChar) {
                                    userName = it
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
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            label = {
                                Text(
                                    text = "Email",
                                    color = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black
                                    //color = Color.Black
                                )
                            },
                            placeholder = {
                                Text(
                                    text = "Your Email",
                                    color = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black
                                    //color = Color.Black
                                )
                            },
                            shape = RoundedCornerShape(8f),
                            singleLine = false,
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black
                            )
                        )

                        // password
                        TextField(
                            value = password,
                            onValueChange = { password = it },
                            modifier = Modifier.background(
                                brush = if (darktheme == 1) gradient else if (darktheme == 2) gradientLight else gradientEnergetic,
                            ).onKeyEvent { keyEvent ->
                                if (keyEvent.key == Key.Enter) {
                                    // have to have this check or else dialog will close
                                }
                                true
                            }
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            label = {
                                Text(
                                    "Password",
                                    color = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black
                                )
                            },
                            singleLine = true,
                            placeholder = {
                                Text(
                                    "Password",
                                    color = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black
                                )
                            },
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            trailingIcon = {
                                val image = if (passwordVisible)
                                    Icons.Filled.CheckCircle
                                else Icons.Filled.AddCircle

                                // Please provide localized description for accessibility services
                                val description =
                                    if (passwordVisible) "Hide password" else "Show password"

                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector = image,
                                        contentDescription = description,
                                        tint = Color.White
                                    )
                                }
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black
                            )
                        )
                        Text(
                            "Password must be at least 8 characters, lower & upper-case, numbers & a special character",
                            color = if (darktheme == 1 || darktheme == 3) Color.White else Color.Black,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                            fontSize = 12.sp
                        )
                        Button(onClick = {
                            runBlocking {
//                                println(userName.text)
//                                println(password.text)
                                launch {
                                    user.signUpUser(userName.text, password.text)
                                }
                                confirmState = true
                            }

                        }) {
                            Text(
                                text = "Sign Up",
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

    }
}
