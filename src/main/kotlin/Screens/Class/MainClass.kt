package Screens.Class

import Screens.MainAppScreen.Components.topBar
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import data.remote.Class
import data.remote.Course

@Composable
fun MainClass(
    selectedClass: Class,
    onClickCourse: (Course) -> Unit,
    onClickClass: (Class) -> Unit,
    onClickBeginning: () -> Unit,
    onCloseSession: () -> Unit
) {
    Scaffold(
        topBar = {
            topBar(
                onClickCourse = { onClickCourse(it) },
                onClickClass= { onClickClass(it) },
                onClickBeginning = { onClickBeginning() },
                onCloseSession = { onCloseSession()  }
            )
        },
        content = {
            Text(text = selectedClass.name)

        }
    )
}