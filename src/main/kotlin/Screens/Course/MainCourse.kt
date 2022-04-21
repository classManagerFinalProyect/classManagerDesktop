package Screens.Course

import Screens.Course.Components.MainBody.ContentState
import Screens.Course.Components.MainBody.classes
import Screens.Course.Components.MainBody.events
import Screens.Course.Components.MainBody.members
import Screens.MainAppScreen.Components.topBar
import Utils.LazyGridFor
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.remote.Course
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.TextButton
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
    onChangeGetDates: () -> Unit,
    onCloseSession: () -> Unit
){
    val composableScope = rememberCoroutineScope()
    var showMainContent by remember { mutableStateOf(false) }


    LaunchedEffect(getDates) {
        if (getDates) {
            showMainContent = false
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
                onFinished = {}
            )

            ViewModelCourse.getCurrentMembers(
                composableScope = composableScope,
                selectedCourse = selectedCourse,
                onFinished = {}
            )
            onChangeGetDates()
        }
    }


    val contentState by ViewModelCourse.contentState


    Scaffold(
        topBar = {
             topBar(
                onClickCourse = { onClickCourse(it) },
                onClickClass= { onClickClass(it) },
                onClickBeginning = { onClickBeginning() },
                onCloseSession = { onCloseSession() }
             )
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        content = {
                            Column(
                                content =  {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        content = {
                                            Image(
                                                painter = painterResource(resourcePath = "books.jpg"),
                                                contentDescription = "logo",
                                                modifier = Modifier.size(100.dp)
                                            )
                                            Text(
                                                text = selectedCourse.name,
                                                fontSize = 20.sp
                                            )
                                        }
                                    )
                                }
                            )
                        }
                    )


                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                        content = {

                            TextButton(
                                onClick = {
                                    ViewModelCourse.updateContentState(newValue = ContentState.CLASSES)
                                },
                                content = {
                                    Text(text = "Classes")
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
    onClickClass: (Class) -> Unit
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
                    members()
                }
                ContentState.EVENTS -> {
                    events()
                }
            }
        }
    )
}
