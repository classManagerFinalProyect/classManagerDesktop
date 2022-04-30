package Screens.Course.Components.MainBody.Classes

import ScreenItems.bigTextFieldWithErrorMessage
import Screens.Course.ViewModelCourse
import Screens.ScreenComponents.TopAppBar.CreateClass.ViewModelCreateClass
import Utils.isAlphabetic
import Utils.isAlphanumeric
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
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
fun addNewClass(
    onClickCancel: () -> Unit,
    reload: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize(),
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
                validateError = ::isAlphabetic,
                errorMessage = messageNameClassError,
                changeError = nameErrorChange,
                error = nameError,
                mandatory = true,
                KeyboardType = KeyboardType.Text
            )
            Spacer(modifier = Modifier.padding(5.dp))

            bigTextFieldWithErrorMessage(
                text = "Descripción de la clase",
                value = textDescription,
                onValueChange = onValueChangeDescriptionText,
                validateError = ::isAlphanumeric,
                errorMessage = messageDescriptionError,
                changeError = nameDescriptionErrorChange,
                error = nameDescriptionError,
                mandatory = false,
                KeyboardType = KeyboardType.Text
            )
            /*
            selectedDropDownMenuCurseItem(
                textOfRow = "Curso",
                suggestions = CurrentUser.myCourses,
                onValueChangeTextSelectedItem = onValueChangeItemSelectedCurse
            )
            */


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
                                idOfCourse = ViewModelCourse.selectedCourse.id
                            )

                            ViewModelCourse.addNewClass(
                                composableScope = composableScope,
                                uploadClass = newClass,
                                onFinished = {
                                    reload()
                                    Log.debug("Se ha creado la clase")
                                }
                            )
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