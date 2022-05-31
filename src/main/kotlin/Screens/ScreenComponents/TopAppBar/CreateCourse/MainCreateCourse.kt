package Screens.ScreenComponents.TopAppBar.CreateCourse

import Screens.ScreenItems.Inputs.bigTextFieldWithErrorMessage
import Utils.*
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
import data.remote.Course


@Composable
fun mainCreateCourse(
    onClickCancel: () -> Unit,
    onChangeGetDates: (Boolean) -> Unit,
    onCreateCourse: (Course) -> Unit
) {

    //Texts
    val (textNameCourse,onValueChangeNameCourseText) = remember{ mutableStateOf("Name course test") }
    val (nameCourseError,nameCourseErrorChange) = remember { mutableStateOf(false) }

    val (textDescriptionCourse,onValueChangeDescriptionCourseText) = remember{ mutableStateOf("Description courese test") }
    val (nameDescriptionError,nameDescriptionErrorChange) = remember { mutableStateOf(false) }

    //Help variables
    val composableScope = rememberCoroutineScope()


    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .width(470.dp),
        content = {

            Text(
                text = "Crear nuevo curso",
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 5.dp),
                fontFamily = FontFamily.Monospace
            )

            bigTextFieldWithErrorMessage(
                text = "Nombre del curso",
                value = textNameCourse,
                onValueChange = onValueChangeNameCourseText,
                validateError = { isValidName(it) },
                errorMessage = CommonErrors.notValidName,
                changeError = nameCourseErrorChange,
                error = nameCourseError,
                mandatory = true,
                KeyboardType = KeyboardType.Text,
                enabled = true
            )

            bigTextFieldWithErrorMessage(
                text = "Descripción del curso",
                value = textDescriptionCourse,
                onValueChange = onValueChangeDescriptionCourseText,
                validateError = { isValidDescription(it) },
                errorMessage = CommonErrors.notValidDescription,
                changeError = nameDescriptionErrorChange,
                error = nameDescriptionError,
                mandatory = false,
                KeyboardType = KeyboardType.Text,
                enabled = true
            )
            Spacer(modifier = Modifier.padding(5.dp))


        }
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
                    if(isValidName(textNameCourse) && isValidDescription(textDescriptionCourse)) {
                        val newCourse = Course(
                            users = arrayListOf(
                                RolUser(
                                    id = CurrentUser.currentUser.id,
                                    rol = "admin"
                                )
                            ),
                            classes = arrayListOf(),
                            events = arrayListOf(),
                            name = textNameCourse,
                            description = textDescriptionCourse,
                            id = "",
                            img = "gs://class-manager-58dbf.appspot.com/user/defaultUserImg.png"
                        )
                        ViewModelCreateCourse.createNewCourse(
                            composableScope = composableScope,
                            uploadCourse = newCourse,
                            onFinished =  {
                                onChangeGetDates(true)
                                onCreateCourse(it)
                            },
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