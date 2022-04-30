package Screens.ScreenComponents.TopAppBar.CreateCourse

import ScreenItems.bigTextFieldWithErrorMessage
import Screens.ScreenComponents.TopAppBar.Profile.ViewModelProfile
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
import data.remote.Course


@Composable
fun mainCreateCourse(
    onClickCancel: () -> Unit,
    onChangeGetDates: (Boolean) -> Unit
) {

    //Texts
    var (textNameCourse,onValueChangeNameCourseText) = remember{ mutableStateOf("Name course test") }
    var (nameCourseError,nameCourseErrorChange) = remember { mutableStateOf(false) }
    val messageNameCourseError by remember { mutableStateOf("El nombre debe de contener únicamente caracteres alfanuméricos") }

    var (textDescriptionCourse,onValueChangeDescriptionCourseText) = remember{ mutableStateOf("Description courese test") }
    var (nameDescriptionError,nameDescriptionErrorChange) = remember { mutableStateOf(false) }
    val messageDescriptionCourseError by remember { mutableStateOf("El nombre debe de contener únicamente caracteres alfanuméricos") }

    //Help variables
    val composableScope = rememberCoroutineScope()


    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize(),

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
                validateError = ::isAlphabetic,
                errorMessage = messageNameCourseError,
                changeError = nameCourseErrorChange,
                error = nameCourseError,
                mandatory = true,
                KeyboardType = KeyboardType.Text
            )

            bigTextFieldWithErrorMessage(
                text = "Descripción del curso",
                value = textDescriptionCourse,
                onValueChange = onValueChangeDescriptionCourseText,
                validateError = ::isAlphanumeric,
                errorMessage = messageDescriptionCourseError,
                changeError = nameDescriptionErrorChange,
                error = nameDescriptionError,
                mandatory = false,
                KeyboardType = KeyboardType.Text
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
                         id = ""
                    )
                    ViewModelCreateCourse.createNewCourse(
                        composableScope = composableScope,
                        uploadCourse = newCourse,
                        onFinished =  {
                            onChangeGetDates(true)
                        },
                    )
                },
                content = {
                    Text(text = "Crear")
                }
            )
        }
    )
}