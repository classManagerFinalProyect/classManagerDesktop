package Screens.MainAppScreen.Components.MainBody

import Screens.MainAppScreen.Items.rectangleCard
import Screens.MainAppScreen.ViewModelMainAppScreen
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        LazyColumn(
            content = {
                itemsIndexed(
                    ViewModelMainAppScreen.completeCourses
                ) { index, item ->
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