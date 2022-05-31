package Screens.MainAppScreen.Components.MainBody

import Screens.ScreenItems.Cards.rectangleCard
import Screens.MainAppScreen.ViewModelMainAppScreen
import Screens.ScreenComponents.TopAppBar.CreateCourse.mainCreateCourse
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.remote.Course
import data.remote.Class

@Composable
fun myCourses(
    getDates: Boolean,
    onClickCourse: (Course) -> Unit,
    onClickClass: (Class) -> Unit
){
    var createNewCourse by remember { mutableStateOf(false) }

    Text(
        text = "Cursos en los que participas",
        fontSize = 25.sp,
        color = Color.Black,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(top = 10.dp, bottom = 20.dp))
    )

    if(!getDates) {
        if(ViewModelMainAppScreen.completeCourses.size == 0) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize(),
                content = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier
                            .width(400.dp)
                            .height(350.dp),
                        content = {
                            LazyColumn(
                                content = {

                                    item {
                                        Image(
                                            painter = painterResource("logoDani.png"),
                                            contentDescription = "Sample",
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                    item {
                                        Text(text = "No dispone de ningún curso actualmente", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                                    }

                                    item {
                                        TextButton(
                                            onClick = {
                                                createNewCourse = true
                                            },
                                            content = {
                                                DropdownMenu(
                                                    expanded = createNewCourse,
                                                    onDismissRequest = { createNewCourse = false},
                                                    content = {
                                                        mainCreateCourse(
                                                            onClickCancel = {
                                                                createNewCourse = false
                                                            },
                                                            onCreateCourse = {
                                                                onClickCourse(it)
                                                            },
                                                            onChangeGetDates = {}
                                                        )
                                                    }
                                                )
                                                Text(text = "Crear curso", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
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
        LazyColumn(
            content = {
                itemsIndexed(
                    ViewModelMainAppScreen.completeCourses
                ) { _, item ->
                    Card(
                        elevation = 3.dp,
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .clickable {
                                val listOfClassesIds: MutableList<String> = arrayListOf()
                                item.classes.forEach { listOfClassesIds.add(it.id)}
                                val selectedCourse = Course(
                                    users = item.users,
                                    classes = listOfClassesIds,
                                    events = item.events,
                                    name = item.name,
                                    description = item.description,
                                    id = item.id,
                                    img = item.img
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
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        content = {
                                            Column(
                                                horizontalAlignment = Alignment.Start,
                                                content = {
                                                    Row(
                                                        content = {
                                                            Text(text = "Curso: ", fontWeight = FontWeight.Bold)
                                                            Text(text = item.name,color = MaterialTheme.colors.primary)
                                                        }
                                                    )
                                                }
                                            )
                                            Column(
                                                horizontalAlignment = Alignment.End,
                                                content = {
                                                    Row(
                                                        content = {
                                                            Text(text = "Número de clases: ", fontWeight = FontWeight.Bold)
                                                            Text(text = "${item.classes.size}",color = MaterialTheme.colors.primary)
                                                        }
                                                    )
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

                                                    this.itemsIndexed(item.classes){ _, item ->
                                                        rectangleCard(
                                                            title = item.name,
                                                            subtitle = "${item.idPractices.size}",
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