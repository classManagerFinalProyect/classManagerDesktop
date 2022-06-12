package Screens.ScreenComponents.TopAppBar

import Screens.ScreenComponents.TopAppBar.items.dropDownMenuButton
import Screens.ScreenItems.DropDownMenu.dropDownMenuClassTransparent
import Screens.ScreenComponents.TopAppBar.items.dropDownMenuUserImg
import Screens.ScreenComponents.TopAppBar.items.dropDownMenuCourses
import Screens.ScreenItems.Dialogs.infoDialog
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import data.local.CurrentUser
import data.remote.Course
import data.remote.Class

@Composable
fun topBar(
    onClickCourse: (Course) -> Unit,
    onClickClass: (Class) -> Unit,
    onClickBeginning: () -> Unit,
    onCloseSession: () -> Unit,
    onChangeGetDates: (Boolean) -> Unit
) {

    val showToast = remember { mutableStateOf(false) }
    var toastTitle by remember{ mutableStateOf("Title") }
    var toastText by remember{ mutableStateOf("Text") }

    if(showToast.value) {
        infoDialog(
            showToast = showToast,
            title = toastTitle,
            text = toastText
        )

    }

    Column (
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(MaterialTheme.colors.primary),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Image(
                        painter = painterResource("logoDani.png"),
                        contentDescription = "logo",
                        modifier = Modifier.clickable {onClickBeginning()  }
                    )
                    dropDownMenuCourses(
                        suggestions = CurrentUser.myCourses,
                        nameOfMenu = "Mis cursos",
                        onClick = {
                            onClickCourse(it)

                        }
                    )
                    dropDownMenuClassTransparent(
                        suggestions = CurrentUser.myClasses,
                        nameOfMenu = "Mis Clases",
                        onClick = {

                            onClickClass(it)
                        }
                    )

                    dropDownMenuButton(
                        textOfButton = "Nuevo",
                        onChangeGetDates = {
                            onChangeGetDates(it)
                        },
                        onCreateClass = {
                            onClickClass(it)
                            //showToast.value = true
                            toastTitle = "La clase ha sido creada correctamente"
                            toastText = "La clase ${it.name} se ha creado correctamente"
                        },
                        onCreateCourse = {
                           // showToast.value = true
                            toastTitle = "El curso ha sido creada correctamente"
                            toastText = "El curso ${it.name} se ha creado correctamente"
                            onClickCourse(it)

                        }
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(PaddingValues(end = 20.dp)),
                        content = {
                            dropDownMenuUserImg(
                                onCloseSession =  { onCloseSession() }
                            )
                        }
                    )

                }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(1.dp, Color.LightGray.copy(0.7f))),
                content = {
                    Spacer(modifier = Modifier.padding(1.dp))
                }
            )
        }
    )

}

