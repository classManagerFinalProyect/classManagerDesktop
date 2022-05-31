package Screens.MainAppScreen.Components

import Screens.MainAppScreen.Items.dropDownMenuButton
import Screens.Course.Components.MainBody.Events.Items.dropDownMenuClass
import Screens.MainAppScreen.Items.dropDownMenuClassTransparent
import Screens.MainAppScreen.Items.dropDownMenuUserImg
import Screens.ScreenItems.dropDownMenuCourses
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
                        },
                        onCreateCourse = {
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

