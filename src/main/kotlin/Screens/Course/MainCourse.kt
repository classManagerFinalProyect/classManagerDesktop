package Screens.Course

import Screens.Course.Components.MainBody.ContentState
import Screens.Course.Components.MainBody.Classes.classes
import Screens.Course.Components.MainBody.Events.events
import Screens.Course.Components.MainBody.Members.members
import Screens.Course.Components.editCourse
import Screens.ScreenComponents.TopAppBar.topBar
import Screens.ScreenComponents.Header.header
import Screens.ScreenComponents.NavigationBar.navigationBar
import Screens.ScreenItems.Dialogs.loadingDialog
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
    val loading = remember { mutableStateOf(false) }


    if(loading.value) {
        loadingDialog(
            loading = loading,
            informativeText = "Cargando datos"
        )
    }


    if(editCourse) {
        editCourse(
            editCourse = { editCourse = it },
            onClickBeginning = { onClickBeginning() }
        )
    }

    LaunchedEffect(getDates) {
        ViewModelCourse.updateContentState(newValue = ContentState.CLASSES)

        if (getDates) {
            loading.value = true
            showMainContent = false
            ViewModelCourse.selectedCourse = selectedCourse
            ViewModelCourse.getCurrentClasses(
                selectedCourse = selectedCourse,
                composableScope = composableScope,
                onFinished = {
                    showMainContent = true
                    loading.value = false
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
        floatingActionButton = {

        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                content = {
                    header(
                        content = {
                            Image(
                                painter = painterResource(resourcePath = "libros_sin_fondo.png"),
                                contentDescription = "logo",
                                modifier = Modifier.size(100.dp)
                            )
                            Spacer(modifier = Modifier.padding(10.dp))

                            Text(
                                text = selectedCourse.name,
                                fontSize = 20.sp,
                                color = Color.White
                            )

                            if(ViewModelCourse.currentUser.rol == "admin" || ViewModelCourse.currentUser.rol == "profesor") {
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
                                    onClickBeginning = onClickBeginning
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
    onClickBeginning: () -> Unit
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
                        onClickClass = onClickClass,
                        onClickBeginning = onClickBeginning
                    )
                }
                ContentState.MEMBERS -> {
                    members()
                }
                ContentState.EVENTS -> {
                    events()
                }
            }
        }
    )
}
