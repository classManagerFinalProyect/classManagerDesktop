package Screens.ScreenComponents.TopAppBar.Profile

import ScreenItems.bigTextFieldWithErrorMessage
import Screens.ScreenComponents.TopAppBar.Profile.ViewModelProfile.Companion.isValidDescription
import Screens.ScreenItems.longButton
import Utils.AsyncImage
import Utils.loadImageBitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import data.local.CurrentUser
import data.remote.appUser
import java.awt.SystemColor.text
import java.util.regex.Pattern

@Composable
fun mainProfile() {
    //Text
    var (userText,onValueChangeUserText) = remember{ mutableStateOf(CurrentUser.currentUser.name) }
    var (userError,userErrorChange) = remember { mutableStateOf(false) }
    val nameOfUserError = "El nombre solo puede contener caracteres alfabeticos"

    var (descriptionText,onValueChangeUserdescription) = remember{ mutableStateOf(CurrentUser.currentUser.description) }
    var (descriptionError,descriptionErrorChange) = remember { mutableStateOf(false) }
    val nameOfDescriptionError = "La descripción solo puede contener caracteres alfanuméricos"

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
                                        AsyncImage(
                                            load = { loadImageBitmap(CurrentUser.currentUser.imgPath) },
                                            painterFor = { remember { BitmapPainter(it) } },
                                            contentDescription = "User Image",
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
                                    validateError = ViewModelProfile::isValidName,
                                    errorMessage = nameOfUserError,
                                    changeError = userErrorChange,
                                    error = userError,
                                    mandatory = false,
                                    KeyboardType = KeyboardType.Text
                                )
                            }

                            item {
                                bigTextFieldWithErrorMessage(
                                    text = "Descripción",
                                    value = descriptionText,
                                    onValueChange = onValueChangeUserdescription,
                                    validateError = ViewModelProfile::isValidDescription,
                                    errorMessage = nameOfDescriptionError,
                                    changeError = descriptionErrorChange,
                                    error = descriptionError,
                                    mandatory = false,
                                    KeyboardType = KeyboardType.Text
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

