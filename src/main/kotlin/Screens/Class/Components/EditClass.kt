package Screens.Class.Components

import ScreenItems.bigTextFieldWithErrorMessage
import Screens.Class.ViewModelClass
import Screens.Course.Components.editCourse
import Screens.Course.ViewModelCourse
import Screens.ScreenItems.Dialogs.defaultDialog
import Screens.ScreenItems.confirmAlertDialog
import Utils.CommonErrors
import Utils.isAlphanumeric
import Utils.isValidDescription
import Utils.isValidName
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import data.remote.Class

@Composable
 fun editClass(
    editClass: (Boolean) -> Unit,
    onClickBeginning: () -> Unit
 ){
    var composableScope = rememberCoroutineScope()
    var deleteClass by remember { mutableStateOf(false) }

    //Texts
    var textName by remember{ mutableStateOf(ViewModelClass.selectedClass.name) }
    var nameError by remember { mutableStateOf(false) }
    val messageNameClassError by remember { mutableStateOf("Debes usar caracteres alfanumérico") }

    var textDescription by remember{ mutableStateOf(ViewModelClass.selectedClass.description) }
    var descriptionError by remember { mutableStateOf(false) }
    val messageDescriptionClassError by remember { mutableStateOf("Debes usar caracteres caracter alfanumérico") }



    defaultDialog(
        title = "Editar Clase",
        onClose = { editClass(false)},
        resizable = false,
        state = rememberDialogState(
            position = WindowPosition(Alignment.Center),
            size = DpSize(550.dp, 500.dp)
        ),
        content = {

            if(deleteClass) {
                confirmAlertDialog(
                    title = "Desea eliminar la clase seleccionada",
                    subtitle = "No podrás volver a recuperarla",
                    onValueChangeGoBack = { deleteClass = false },
                    onClickAccept = {
                        ViewModelClass.deleteCurrentClass(
                            composableScope = composableScope,
                            onFinished = {
                                onClickBeginning()
                            }
                        )
                    }
                )
            }

            Column(
                content = {
                    Spacer(modifier = Modifier.padding(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        content = {
                            Image(
                                painter = painterResource(resourcePath = "books.jpg"),
                                contentDescription = "logo",
                                modifier = Modifier.size(100.dp)
                            )
                        }
                    )


                    bigTextFieldWithErrorMessage(
                        text = "Nombre de la clase",
                        value = textName,
                        onValueChange = { textName = it },
                        validateError = ::isValidName,
                        errorMessage = CommonErrors.notValidName,
                        changeError = { nameError = it},
                        error = nameError,
                        mandatory = false,
                        KeyboardType = KeyboardType.Text,
                        enabled = true
                    )

                    bigTextFieldWithErrorMessage(
                        text = "Descipción de la clase",
                        value = textDescription,
                        onValueChange = { textDescription = it },
                        validateError = ::isValidDescription,
                        errorMessage = CommonErrors.notValidDescription,
                        changeError = { descriptionError = it},
                        error = descriptionError,
                        mandatory = false,
                        KeyboardType = KeyboardType.Text,
                        enabled = true
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 40.dp, end = 40.dp),
                        content = {
                            if(ViewModelCourse.currentUser.rol == "admin") {
                                Button(
                                    modifier = Modifier.width(200.dp),
                                    contentPadding = PaddingValues(
                                        start = 10.dp,
                                        top = 6.dp,
                                        end = 10.dp,
                                        bottom = 6.dp
                                    ),
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = Color.White,
                                        backgroundColor = Color.Red
                                    ),
                                    onClick = {

                                        deleteClass = true

                                    },
                                    content = {
                                        Text(text = "Eliminar Clase")
                                    }
                                )

                                Button(
                                    modifier = Modifier.width(200.dp),
                                    contentPadding = PaddingValues(
                                        start = 10.dp,
                                        top = 6.dp,
                                        end = 10.dp,
                                        bottom = 6.dp
                                    ),
                                    onClick = {

                                        if(isValidName(textName) && isValidDescription(textDescription)) {
                                            ViewModelClass.updateCurrentClass(
                                                newName = textName,
                                                composableScope = composableScope,
                                                newDescription = textDescription,
                                                onFinished = {
                                                    editClass(false)
                                                }
                                            )
                                        }
                                    },
                                    content = {
                                        Text(text = "Guardar cambios")
                                    }
                                )
                            }
                            else {
                                Button(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentPadding = PaddingValues(
                                        start = 10.dp,
                                        top = 6.dp,
                                        end = 10.dp,
                                        bottom = 6.dp
                                    ),
                                    onClick = {
                                        if(isValidDescription(textDescription) && isValidName(textName)) {
                                            ViewModelCourse.updateCurrentCourse(
                                                newName = textName,
                                                composableScope = composableScope,
                                                newDescription = textDescription,
                                                onFinished = {
                                                    editClass(false)
                                                }
                                            )
                                        }
                                    },
                                    content = {
                                        Text(text = "Guardar cambios")
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