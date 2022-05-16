package Screens.ScreenComponents.TopAppBar.CreateClass

import ScreenItems.bigTextFieldWithErrorMessage
import Utils.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.local.CurrentUser
import data.local.RolUser
import data.remote.Class
import org.jetbrains.skia.impl.Log

@Composable
fun mainCreateClass(
    onClickCancel: () -> Unit,
    onCreateClass: (Class) -> Unit
){
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .width(470.dp),
        content = {

            //Help variables
            val composableScope = rememberCoroutineScope()

            //Texts
            var (textName,onValueChangeNameText) = remember{ mutableStateOf("Name class test") }
            var (nameError,nameErrorChange) = remember { mutableStateOf(false) }
            val messageNameClassError by remember { mutableStateOf("El nombre debe de contener únicamente caracteres alfabeticos") }

            var (textDescription,onValueChangeDescriptionText) = remember{ mutableStateOf("Name class test") }
            var (nameDescriptionError,nameDescriptionErrorChange) = remember { mutableStateOf(false) }
            val messageDescriptionError by remember { mutableStateOf("El nombre debe de contener únicamente caracteres alfanuméricos") }


            Text(
                text = "Crear nueva clase",
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 5.dp),
                fontFamily = FontFamily.Monospace
            )

            bigTextFieldWithErrorMessage(
                text = "Nombre de la clase",
                value = textName,
                onValueChange = onValueChangeNameText,
                validateError = { isValidName(it) },
                errorMessage = CommonErrors.notValidName,
                changeError = nameErrorChange,
                error = nameError,
                mandatory = true,
                KeyboardType = KeyboardType.Text,
                enabled = true
            )

            Spacer(modifier = Modifier.padding(5.dp))

            bigTextFieldWithErrorMessage(
                text = "Descripción de la clase",
                value = textDescription,
                onValueChange = onValueChangeDescriptionText,
                validateError = { isValidDescription(it) },
                errorMessage = CommonErrors.notValidDescription,
                changeError = nameDescriptionErrorChange,
                error = nameDescriptionError,
                mandatory = false,
                KeyboardType = KeyboardType.Text,
                enabled = true
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp, end = 10.dp),
                content = {
                    Button(
                        contentPadding = PaddingValues(
                            start = 10.dp,
                            top = 6.dp,
                            end = 10.dp,
                            bottom = 6.dp
                        ),
                        modifier = Modifier
                            .padding(start = 10.dp, end = 20.dp),
                        onClick = {
                            onClickCancel()
                        },
                        content = {
                            Text(text = "Cancelar")
                        }
                    )
                    Button(
                        contentPadding = PaddingValues(
                            start = 10.dp,
                            top = 6.dp,
                            end = 10.dp,
                            bottom = 6.dp
                        ),
                        modifier = Modifier
                            .padding(start = 10.dp, end = 20.dp),
                        onClick = {
                            if(isValidDescription(textDescription) && isValidName(textName)) {
                                val newClass = Class(
                                    id = "",
                                    name = textName,
                                    description = textDescription,
                                    idPractices = arrayListOf(),
                                    users = arrayListOf(
                                        RolUser(
                                            id = CurrentUser.currentUser.id,
                                            rol = "admin"
                                        )
                                    ),
                                    idOfCourse = "Sin Asignar",
                                    img = "gs://class-manager-58dbf.appspot.com/user/defaultUserImg.png"
                                )

                                ViewModelCreateClass.createNewClass(
                                    composableScope = composableScope,
                                    uploadClass = newClass,
                                    onFinished = {
                                        onCreateClass(it)
                                        Log.debug("Se ha creado una clase")
                                    }
                                )
                            }

                        },
                        content = {
                            Text(text = "Crear")
                        }
                    )
                }
            )
        }
    )

}