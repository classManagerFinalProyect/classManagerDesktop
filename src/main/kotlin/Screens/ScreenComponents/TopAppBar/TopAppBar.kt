package Screens.MainAppScreen.Components

import Screens.MainAppScreen.Items.dropDownMenuButton
import Screens.MainAppScreen.Items.dropDownMenuClass
import Screens.MainAppScreen.Items.dropDownMenuUserImg
import Screens.ScreenComponents.TopAppBar.items.dialogProfile
import Screens.ScreenItems.dropDownMenuCourses
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import data.local.CurrentUser
import data.remote.Course
import data.remote.Class

@Composable
fun topBar(
    onClickCourse: (Course) -> Unit,
    onClickClass: (Class) -> Unit,
    onClickBeginning: () -> Unit,
    onCloseSession: () -> Unit
) {
    var profileIsOpen by remember { mutableStateOf(false) }

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
            dropDownMenuClass(
                suggestions = CurrentUser.myClasses,
                nameOfMenu = "Mis Clases",
                onClick = {
                    onClickClass(it)
                }
            )
            dropDownMenuButton(
                textOfButton = "Nuevo"
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
}