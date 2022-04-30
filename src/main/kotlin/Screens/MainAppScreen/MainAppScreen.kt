package Screens.MainAppScreen

import Screens.Course.Components.MainBody.ContentState
import Screens.Course.ViewModelCourse
import Screens.MainAppScreen.Components.topBar
import Screens.MainAppScreen.Items.rectangleCard
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
    onBack: () -> Unit,
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
            ViewModelMainAppScreen.getCompleteCourses(
                composableScope = composableScope,
                allCourses = CurrentUser.myCourses,
                onFinished = {
                    onChangeGetDates(false)
                }
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
                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .width(400.dp)
                                        .padding(PaddingValues(30.dp))
                                        .border(BorderStroke(1.dp,Color.LightGray)),
                                    content = {
                                        /*
                                        Text(text = "${CurrentUser.currentUser.name.uppercase()}")
                                        Text(text = CurrentUser.currentUser.email)
                                        */
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(30.dp)
                                                .clickable {
                                                    clipboardManager.setText(
                                                        AnnotatedString(text = "${CurrentUser.currentUser.id}")
                                                    )
                                                    //Toast
                                                },
                                            content = {
                                                Text(text = "#${CurrentUser.currentUser.id}")
                                                Spacer(modifier = Modifier.padding(4.dp))

                                                Icon(
                                                    painter = painterResource(resourcePath = "content_copy_black.png"),
                                                    contentDescription = "Copiar"
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
                                                Text(text = "Cursos:")
                                                IconButton(
                                                    onClick = {
                                                        expanded = !expanded
                                                    },
                                                    content = {
                                                        Icon(
                                                            imageVector =  icon,
                                                            contentDescription = "arrowExpanded",
                                                        )
                                                    }
                                                )
                                            }
                                        )

                                        if(expanded) {
                                            CurrentUser.myCourses.forEach {
                                                TextButton (
                                                    onClick = {
                                                        //Navegar al seleccionado
                                                    },
                                                    content = {
                                                        Text(text = "Course --- ${it.name}")
                                                    }
                                                )
                                            }
                                        }

                                    }
                                )








                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .fillMaxWidth()
                                        .padding(PaddingValues(30.dp)),
                                    content = {
                                        Text(
                                            text = "Cursos en los que participas",
                                            fontSize = 25.sp,
                                            color = Color.LightGray,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(PaddingValues(top = 10.dp, bottom = 20.dp))
                                        )

                                        if(!getDates) {
                                            LazyColumn(
                                                content = {
                                                    itemsIndexed(ViewModelMainAppScreen.completeCourses) { index, item ->
                                                        Card(
                                                            elevation = 3.dp,
                                                            shape = RoundedCornerShape(4.dp),
                                                            modifier = Modifier
                                                                .clickable {
                                                                    var listOfClassesIds: MutableList<String> = arrayListOf()
                                                                    item.classes.forEach { listOfClassesIds.add(it.id)}
                                                                    var selectedCourse = Course(
                                                                        users = item.users,
                                                                        classes = listOfClassesIds,
                                                                        events = item.events,
                                                                        name = item.name,
                                                                        description = item.description,
                                                                         id = item.id
                                                                    )
                                                                    onClickCourse(selectedCourse)
                                                               },
                                                            content = {

                                                                Column(
                                                                    modifier = Modifier
                                                                        .fillMaxWidth()
                                                                        .padding(PaddingValues(all = 20.dp)),
                                                                    horizontalAlignment = Alignment.Start,
                                                                    content = {
                                                                        Row(
                                                                            modifier = Modifier
                                                                                .fillMaxWidth()
                                                                                .padding(bottom = 10.dp),
                                                                            content = {
                                                                                Column(
                                                                                    horizontalAlignment = Alignment.Start,
                                                                                    content = {
                                                                                        Text(text = "Curso: ${item.name}")

                                                                                    }
                                                                                )
                                                                                Column(
                                                                                    horizontalAlignment = Alignment.End,
                                                                                    content = {
                                                                                        Text(text = "Número de clases: ${item.classes.size}")
                                                                                    }
                                                                                )
                                                                            }
                                                                        )

                                                                        Box(
                                                                            modifier = Modifier
                                                                                .fillMaxWidth(),
                                                                            content = {
                                                                                LazyRow(
                                                                                    content = {

                                                                                        this.itemsIndexed(item.classes){ index, item ->
                                                                                            rectangleCard(
                                                                                                title = item.name,
                                                                                                subtitle = "Subtitle",
                                                                                                onClick = { onClickClass(item) }
                                                                                            )
                                                                                        }

                                                                                    }
                                                                                )
                                                                            }
                                                                        )
                                                                    }
                                                                )

                                                            }
                                                        )
                                                        Spacer(modifier = Modifier.padding(20.dp))
                                                    }
                                                }
                                            )
                                        }

                                    }
                                )


                            }
                        )
                    }
                )
            }
        )

}
