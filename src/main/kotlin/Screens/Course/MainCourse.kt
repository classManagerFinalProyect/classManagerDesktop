package Screens.Course

import Screens.Class.Components.editClass
import Screens.Course.Components.MainBody.ContentState
import Screens.Course.Components.MainBody.classes
import Screens.Course.Components.MainBody.events
import Screens.Course.Components.MainBody.members
import Screens.Course.Components.editCourse
import Screens.MainAppScreen.Components.topBar
import Screens.ScreenComponents.Header.header
import Screens.ScreenComponents.NavigationBar.navigationBar
import Screens.theme.blueDesaturated
import Utils.LazyGridFor
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.remote.Course
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import data.remote.Class


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainCourse(
    selectedCourse: Course,
    onClickCourse: (Course) -> Unit,
    onClickClass: (Class) -> Unit,
    onClickBeginning: () -> Unit,
    getDates: Boolean,
    onChangeGetDates: (Boolean) -> Unit,
    onCloseSession: () -> Unit
){
    val composableScope = rememberCoroutineScope()
    var showMainContent by remember { mutableStateOf(false) }
    var editCourse by remember { mutableStateOf(false) }

    if(editCourse) {
        editCourse(
            editCourse = { editCourse = it },
            onClickBeginning = { onClickBeginning() }
        )
    }

    LaunchedEffect(getDates) {
        ViewModelCourse.updateContentState(newValue = ContentState.CLASSES)

        if (getDates) {
            showMainContent = false
            ViewModelCourse.selectedCourse = selectedCourse
            ViewModelCourse.getCurrentClasses(
                selectedCourse = selectedCourse,
                composableScope = composableScope,
                onFinished = {
                    showMainContent = true
                }
            )

            ViewModelCourse.getCurrentEvents(
                composableScope = composableScope,
                selectedCourse = selectedCourse,
                onFinished = {

                }
            )

            ViewModelCourse.getCurrentMembers(
                composableScope = composableScope,
                selectedCourse = selectedCourse,
                onFinished = {}
            )
            onChangeGetDates(false)
        }
    }


    val contentState by ViewModelCourse.contentState


    Scaffold(
        topBar = {
             topBar(
                onClickCourse = { onClickCourse(it) },
                onClickClass= { onClickClass(it) },
                onClickBeginning = { onClickBeginning() },
                onCloseSession = { onCloseSession() },
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
                                text = selectedCourse.name,
                                fontSize = 20.sp,
                                color = Color.White
                            )

                            IconButton(
                                onClick = {
                                    editCourse = true
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
                                    ViewModelCourse.updateContentState(newValue = ContentState.CLASSES)
                                },
                                content = {
                                    Text(text = "Clases")
                                }
                            )
                            TextButton(
                                onClick = {
                                    ViewModelCourse.updateContentState(newValue = ContentState.EVENTS)
                                },
                                content = {
                                    Text(text = "Eventos")
                                }
                            )
                            TextButton(
                                onClick = {
                                    ViewModelCourse.updateContentState(newValue = ContentState.MEMBERS)
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
                            if (showMainContent) {
                                MainContent(
                                    onClickClass = onClickClass,
                                    contentState = contentState,
                                    selectedCourse = selectedCourse
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
    onClickClass: (Class) -> Unit,
    selectedCourse: Course
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight(),
        content = {
            when (contentState) {
                ContentState.CLASSES ->  {
                    classes(
                        onClickClass = onClickClass
                    )
                }
                ContentState.MEMBERS -> {
                    members(
                        selectedCourse = selectedCourse
                    )
                }
                ContentState.EVENTS -> {
                    events()
                }
            }
        }
    )
}
