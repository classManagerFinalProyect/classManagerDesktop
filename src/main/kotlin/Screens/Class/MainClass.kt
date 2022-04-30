package Screens.Class

import ScreenItems.bigTextFieldWithErrorMessage
import Screens.Class.Components.MainBody.ContentState
import Screens.Class.Components.MainBody.Members.members
import Screens.Class.Components.MainBody.Practices.practices
import Screens.Course.Components.MainBody.classes
import Screens.Course.ViewModelCourse
import Screens.Course.ViewModelCourse.Companion.selectedCourse
import Screens.MainAppScreen.Components.topBar
import Screens.theme.blueDesaturated
import Utils.isDate
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.remote.Class
import data.remote.Course
import data.remote.Practice

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

    val contentState by ViewModelClass.contentState

    LaunchedEffect(getDates) {
        if (getDates) {
            ViewModelClass.selectedClass = selectedClass
            ViewModelClass.getCurrentPractices(
                composableScope = composableScope,
                onFinished = {
                    showMainContent = true
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
                    Column(
                        modifier = Modifier
                            .background(blueDesaturated),
                        content = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                content = {
                                    Column(
                                        content = {
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
                                                        fontSize = 20.sp,
                                                        color = Color.White
                                                    )
                                                }
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

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(BorderStroke(1.dp, Color.LightGray)),
                        content = {
                            Spacer(modifier = Modifier.padding(1.dp))
                        }
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
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