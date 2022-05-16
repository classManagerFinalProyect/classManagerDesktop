package Screens.Course.Components

import ScreenItems.bigTextFieldWithErrorMessage
import Screens.Class.Components.editClass
import Screens.Class.ViewModelClass
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

@Composable
fun editCourse(
    editCourse: (Boolean) -> Unit,
    onClickBeginning: () -> Unit
){
    var composableScope = rememberCoroutineScope()
    var deleteCourse by remember { mutableStateOf(false) }

    //Texts
    var textName by remember{ mutableStateOf(ViewModelCourse.selectedCourse.name) }
    var nameError by remember { mutableStateOf(false) }
    val messageNameClassError by remember { mutableStateOf("Debes usar caracteres alfanumérico") }

    var textDescription by remember{ mutableStateOf(ViewModelCourse.selectedCourse.description) }
    var descriptionError by remember { mutableStateOf(false) }
    val messageDescriptionClassError by remember { mutableStateOf("Debes usar caracteres caracter alfanumérico") }



    defaultDialog(
        title = "Editar Curse",
        onClose = { editCourse(false)},
        resizable = false,
        state = rememberDialogState(
            position = WindowPosition(Alignment.Center),
            size = DpSize(550.dp, 500.dp)
        ),
        content = {

            if(deleteCourse){
                confirmAlertDialog(
                    title = "Desea eliminar el curso seleccionado",
                    subtitle = "No podrás volver a recuperarlo",
                    onValueChangeGoBack = { deleteCourse = false },
                    onClickAccept = {
                        ViewModelCourse.deleteCurrentCourse(
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
                        text = "Nombre del curso",
                        value = textName,
                        onValueChange = { textName = it },
                        validateError = { isValidName(it) },
                        errorMessage = CommonErrors.notValidName,
                        changeError = { nameError = it},
                        error = nameError,
                        mandatory = false,
                        KeyboardType = KeyboardType.Text,
                        enabled = true
                    )

                    bigTextFieldWithErrorMessage(
                        text = "Descipción del curso",
                        value = textDescription,
                        onValueChange = { textDescription = it },
                        validateError = { isValidDescription(it) },
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

                                    deleteCourse = true

                                },
                                content = {
                                    Text(text = "Eliminar Curso")
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
                                    if(isValidDescription(textDescription) && isValidName(textName)) {
                                        ViewModelCourse.updateCurrentCourse(
                                            newName = textName,
                                            composableScope = composableScope,
                                            newDescription = textDescription,
                                            onFinished = {
                                                editCourse(false)
                                            }
                                        )
                                    }
                                },
                                content = {
                                    Text(text = "Guardar cambios")
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}