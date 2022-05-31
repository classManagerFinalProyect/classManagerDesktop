package Screens.ScreenComponents.TopAppBar.Profile

import Screens.ScreenItems.Inputs.bigTextFieldWithErrorMessage
import Screens.ScreenItems.Buttons.longButton
import Utils.CommonErrors
import Utils.isValidDescription
import Utils.isValidName
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import data.local.CurrentUser

@Composable
fun mainProfile() {
    //Text
    val (userText,onValueChangeUserText) = remember{ mutableStateOf(CurrentUser.currentUser.name) }
    val (userError,userErrorChange) = remember { mutableStateOf(false) }

    val (descriptionText,onValueChangeDescription) = remember{ mutableStateOf(CurrentUser.currentUser.description) }
    val (descriptionError,descriptionErrorChange) = remember { mutableStateOf(false) }

    //Help variables
    val composableScope = rememberCoroutineScope()


    Scaffold(
        topBar = {
             TopAppBar(
                 title = {
                     Text(text = "Mi profile")
                 }
             )
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    LazyColumn (
                        modifier = Modifier.fillMaxSize(0.9f),
                        content = {
                            item {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            PaddingValues(
                                                top = 10.dp,
                                                bottom = 10.dp
                                            )
                                        ),
                                    content = {
                                        Image(
                                            painter = painterResource(resourcePath = "defaultUserImg.png"),
                                            contentDescription = "userImg",
                                            modifier = Modifier
                                                .size(250.dp)
                                                .clip(CircleShape)
                                                .border(
                                                    width = 2.dp,
                                                    color = Color.Gray,
                                                    shape = CircleShape
                                                ),
                                            contentScale = ContentScale.Crop,
                                        )
                                    }
                                )
                            }
                            item {
                                bigTextFieldWithErrorMessage(
                                    text = "Nombre de usuario",
                                    value = userText,
                                    onValueChange = onValueChangeUserText,
                                    validateError = ::isValidName,
                                    errorMessage = CommonErrors.notValidName,
                                    changeError = userErrorChange,
                                    error = userError,
                                    mandatory = false,
                                    KeyboardType = KeyboardType.Text,
                                    enabled = true
                                )
                            }

                            item {
                                bigTextFieldWithErrorMessage(
                                    text = "Descripción",
                                    value = descriptionText,
                                    onValueChange = onValueChangeDescription,
                                    validateError = ::isValidDescription,
                                    errorMessage = CommonErrors.notValidDescription,
                                    changeError = descriptionErrorChange,
                                    error = descriptionError,
                                    mandatory = false,
                                    KeyboardType = KeyboardType.Text,
                                    enabled = true
                                )
                            }

                            item {
                                longButton(
                                    text = "Guardar cambios",
                                    onClick = {
                                        CurrentUser.currentUser.name = userText
                                        CurrentUser.currentUser.description = descriptionText
                                        ViewModelProfile.saveChange(
                                            newUser = CurrentUser.currentUser,
                                            composableScope = composableScope,
                                            onFinish = {
                                                //Avisar de que ha funcionado
                                            }
                                        )
                                    }
                                )
                            }
                        }
                    )
                }
            )

        }
    )
}

