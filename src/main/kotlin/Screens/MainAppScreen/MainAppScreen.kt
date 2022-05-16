package Screens.MainAppScreen

import Screens.Course.ViewModelCourse
import Screens.MainAppScreen.Components.ContentState
import Screens.MainAppScreen.Components.MainBody.myClasses
import Screens.MainAppScreen.Components.MainBody.myCourses
import Screens.MainAppScreen.Components.topBar
import Screens.MainAppScreen.Items.rectangleCard
import Screens.theme.blue
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.local.CurrentUser
import data.local.Item
import data.remote.Course
import data.remote.Class


@ExperimentalFoundationApi
@Composable
fun MainAppScreen(
    onClickCourse: (Course) -> Unit ,
    onClickClass: (Class) -> Unit ,
    onClickBeginning: () -> Unit,
    onCloseSession: () -> Unit,
    getDates: Boolean,
    onChangeGetDates: (Boolean) -> Unit
){

    //Help variables
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    var expanded by remember { mutableStateOf(false) }
    val state = rememberLazyListState()
    val density = LocalDensity.current
    val stateHorizontal = rememberScrollState(0)
    val stateVertical = rememberScrollState(0)
    val composableScope = rememberCoroutineScope()
    var numOfCourses by remember { mutableStateOf(0) }

    LaunchedEffect(getDates) {
        if (getDates) {
            ViewModelMainAppScreen.updateContentState(ContentState.COURSE)

            ViewModelMainAppScreen.getCompleteCourses(
                composableScope = composableScope,
                allCourses = CurrentUser.myCourses,
                onFinished = {
                    onChangeGetDates(false)
                }
            )
            ViewModelMainAppScreen.getCompleteClasses(
                composableScope = composableScope,
                allClasses = CurrentUser.myClasses,
                onFinished = {}
            )
        }
    }

        Scaffold(
            topBar = {
               topBar(
                   onClickCourse = {
                       onClickCourse(it)
                   },
                   onClickClass = {
                        onClickClass(it)
                   },
                   onClickBeginning = {
                       onClickBeginning()
                   },
                   onCloseSession = { onCloseSession() },
                   onChangeGetDates = { onChangeGetDates(it) }
               )
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(0.9f),
                    content = {

                        Row(
                            modifier = Modifier.padding(PaddingValues(start = 50.dp, end = 50.dp)),
                            content = {
                                Column (
                                    content = {
                                        Column(
                                            modifier = Modifier.fillMaxHeight(0.1f),
                                            content = {

                                            }
                                        )
                                        Column(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .fillMaxWidth(0.2f)
                                                .padding(PaddingValues(start = 30.dp)),
                                                //.border(BorderStroke(1.dp,Color.LightGray)),
                                            content = {


                                                TextButton(
                                                    onClick = {
                                                        ViewModelMainAppScreen.updateContentState(ContentState.COURSE)
                                                    },
                                                    content = {
                                                        Text(
                                                            modifier = Modifier.fillMaxWidth(),
                                                            text =  "Ver mis cursos",
                                                            textAlign = TextAlign.Start
                                                        )
                                                    }
                                                )

                                                TextButton(
                                                    onClick = {
                                                          ViewModelMainAppScreen.updateContentState(ContentState.CLASS)
                                                    },
                                                    content = {
                                                        Text(
                                                            modifier = Modifier.fillMaxWidth(),
                                                            text =  "Ver mis clases",
                                                            textAlign = TextAlign.Start
                                                        )
                                                    }
                                                )

                                                val icon = if (expanded)
                                                    Icons.Filled.KeyboardArrowUp
                                                else
                                                    Icons.Filled.KeyboardArrowDown

                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Start,
                                                    modifier = Modifier.fillMaxWidth(),
                                                    content = {
                                                        TextButton(
                                                            onClick = {
                                                                expanded = !expanded
                                                            },
                                                            content = {
                                                                Row(
                                                                    modifier = Modifier.fillMaxWidth(),
                                                                    verticalAlignment = Alignment.CenterVertically,
                                                                    horizontalArrangement = Arrangement.Start,
                                                                    content = {
                                                                        Text(
                                                                            text = "Mostrar mi ID",
                                                                            textAlign = TextAlign.Start
                                                                        )
                                                                        Spacer(modifier = Modifier.padding(5.dp))
                                                                        Icon(
                                                                            imageVector =  icon,
                                                                            contentDescription = "arrowExpanded",
                                                                            tint = blue
                                                                        )
                                                                    }
                                                                )
                                                            }
                                                        )
                                                    }
                                                )

                                                if(expanded) {
                                                    Row(
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .height(35.dp)
                                                            .clickable {
                                                                clipboardManager.setText(
                                                                    AnnotatedString(text = "${CurrentUser.currentUser.id}")
                                                                )
                                                            },
                                                        content = {

                                                            Text(
                                                                text = "#${CurrentUser.currentUser.id}",
                                                            )
                                                            Spacer(modifier = Modifier.padding(4.dp))

                                                            Icon(
                                                                painter = painterResource(resourcePath = "content_copy_black.png"),
                                                                contentDescription = "Copiar"
                                                            )
                                                        }
                                                    )
                                                }
                                            }
                                        )

                                    }
                                )



                                val contentState by ViewModelMainAppScreen.contentState

                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .fillMaxWidth()
                                        .padding(PaddingValues(30.dp)),
                                    content = {
                                        mainContent(
                                            contentState = contentState,
                                            getDates = getDates,
                                            onClickCourse = { onClickCourse(it) },
                                            onClickClass = { onClickClass(it) }
                                        )
                                    }
                                )

                            }
                        )
                    }
                )
            }
        )
}

@Composable
private fun mainContent(
    contentState: ContentState,
    getDates: Boolean,
    onClickCourse: (Course) -> Unit,
    onClickClass: (Class) -> Unit
){
    when(contentState){
        ContentState.CLASS -> myClasses(
            getDates = getDates,
            onClickClass = { onClickClass(it) }
        )

        ContentState.COURSE -> myCourses(
            getDates = getDates,
            onClickCourse = { onClickCourse(it) },
            onClickClass = { onClickClass(it) }
        )
    }

}
