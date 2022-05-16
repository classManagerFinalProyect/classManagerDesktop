package Screens.Class

import ScreenItems.bigTextFieldWithErrorMessage
import Screens.Class.Components.MainBody.ContentState
import Screens.Class.Components.MainBody.Members.members
import Screens.Class.Components.MainBody.Practices.practices
import Screens.Class.Components.editClass
import Screens.MainAppScreen.Components.topBar
import Screens.ScreenComponents.Header.header
import Screens.ScreenComponents.NavigationBar.navigationBar
import Screens.ScreenItems.Dialogs.defaultDialog
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import data.remote.Class
import data.remote.Course
import java.awt.SystemColor.text

@Composable
fun MainClass(
    selectedClass: Class,
    onClickCourse: (Course) -> Unit,
    onClickClass: (Class) -> Unit,
    onClickBeginning: () -> Unit,
    onCloseSession: () -> Unit,
    onChangeGetDates: (Boolean) -> Unit,
    getDates: Boolean,

    ) {
    val composableScope = rememberCoroutineScope()
    var showMainContent by remember { mutableStateOf(false) }
    var editClass by remember { mutableStateOf(false) }
    val contentState by ViewModelClass.contentState


    if(editClass) {
        editClass(
            editClass = { editClass = it },
            onClickBeginning = { onClickBeginning() }
        )
    }

    LaunchedEffect(getDates) {
        if (getDates) {
            ViewModelClass.selectedClass = selectedClass
            ViewModelClass.getCurrentPractices(
                composableScope = composableScope,
                onFinished = {
                    showMainContent = true
                }
            )
            ViewModelClass.getCurrentCourse(
                composableScope = composableScope,
                onFinished =  {

                }
            )
            ViewModelClass.getCurrentMembers(
                composableScope = composableScope,
                onFinished =  {

                }
            )
            onChangeGetDates(false)

        }
    }

    Scaffold(
        topBar = {
            topBar(
                onClickCourse = { onClickCourse(it) },
                onClickClass= { onClickClass(it) },
                onClickBeginning = { onClickBeginning() },
                onCloseSession = { onCloseSession()  },
                onChangeGetDates = { onChangeGetDates(it) }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                content = {
                    header(
                        content = {
                            Image(
                                painter = painterResource(resourcePath = "books.jpg"),
                                contentDescription = "logo",
                                modifier = Modifier.size(100.dp)
                            )
                            Spacer(modifier = Modifier.padding(10.dp))
                            Text(
                                text = selectedClass.name,
                                fontSize = 20.sp,
                                color = Color.White
                            )
                            IconButton(
                                onClick = {
                                    editClass = true
                                },
                                content = {
                                    Icon(
                                        tint = Color.White,
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Editar Clase"
                                    )
                                }
                            )
                        }
                    )

                    navigationBar(
                        content = {
                            TextButton(
                                onClick = {
                                    ViewModelClass.updateContentState(newValue = ContentState.PRACTICES)
                                },
                                content = {
                                    Text(text = "Prácticas")
                                }
                            )
                            TextButton(
                                onClick = {
                                    ViewModelClass.updateContentState(newValue = ContentState.MEMBERS)
                                },
                                content = {
                                    Text(text = "Miembros")
                                }
                            )
                        }
                    )

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth(),
                        content = {
                            if(showMainContent) {
                                MainContent(
                                    contentState = contentState,
                                )
                            }
                        }
                    )
                }
            )
        }
    )
}

@Composable
private fun MainContent(
    contentState: ContentState,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight(),
        content = {
            when (contentState) {
                ContentState.PRACTICES ->  {
                    practices(
                    )
                }
                ContentState.MEMBERS -> {
                   members()
                }

            }
        }
    )
}