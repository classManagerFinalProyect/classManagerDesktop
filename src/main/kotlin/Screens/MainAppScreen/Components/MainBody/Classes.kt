package Screens.MainAppScreen.Components.MainBody

import Screens.Course.ViewModelCourse.Companion.selectedCourse
import Screens.MainAppScreen.Items.rectangleCard
import Screens.MainAppScreen.ViewModelMainAppScreen
import akka.http.scaladsl.model.headers.LinkParams
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
fun myClasses(
    getDates: Boolean,
    onClickClass: (Class) -> Unit
){

    Text(
        text = "Clases en las que participas",
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
                    ViewModelMainAppScreen.completeClasses
                ) { index, item ->
                    Card(
                        elevation = 3.dp,
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .clickable {
                                var listOfPracticeIds: MutableList<String> = arrayListOf()
                                item.practices.forEach { listOfPracticeIds.add(it.id)}
                                var selectedClass = Class(
                                    users = item.users,
                                    idOfCourse = item.idOfCourse,
                                    idPractices = listOfPracticeIds,
                                    name = item.name,
                                    description = item.description,
                                    id = item.id,
                                    img = item.img
                                )
                                onClickClass(selectedClass)
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
                                                    Text(text = "CLass: ${item.name}")

                                                }
                                            )
                                            Column(
                                                horizontalAlignment = Alignment.End,
                                                content = {
                                                    Text(text = "Número de Prácticas: ${item.practices.size}")
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

                                                    this.itemsIndexed(item.practices){ index, item ->
                                                        Card(
                                                            modifier = Modifier
                                                                .padding(8.dp, 4.dp)
                                                                .width(200.dp)
                                                                .height(100.dp),
                                                            shape = RoundedCornerShape(8.dp),
                                                            elevation = 4.dp,
                                                            content = {
                                                                Row(
                                                                    modifier = Modifier
                                                                        .padding(4.dp)
                                                                        .fillMaxSize(),
                                                                    content = {
                                                                        Image(
                                                                            painter = painterResource(resourcePath = "books.jpg"),
                                                                            contentDescription = "Imágen del curso",
                                                                            modifier = Modifier
                                                                                .fillMaxHeight()
                                                                                .weight(0.2f),
                                                                        )
                                                                        Spacer(modifier = Modifier.padding(5.dp))
                                                                        Column(
                                                                            modifier = Modifier
                                                                                .padding(4.dp)
                                                                                .fillMaxHeight()
                                                                                .weight(0.8f),
                                                                            verticalArrangement = Arrangement.Center,
                                                                            content = {
                                                                                Text(
                                                                                    text = item.name,
                                                                                    style = MaterialTheme.typography.subtitle1,
                                                                                    fontWeight = FontWeight.Bold,
                                                                                )
                                                                                Spacer(modifier = Modifier.padding(4.dp))

                                                                                Row (
                                                                                    content = {
                                                                                        Icon(
                                                                                            painter = painterResource(resourcePath = "task_black.png"),
                                                                                            contentDescription = "Comments",
                                                                                            modifier = Modifier.size(21.dp)
                                                                                        )
                                                                                        Text(
                                                                                            text = "${item.deliveryDate}",
                                                                                            style = MaterialTheme.typography.caption,
                                                                                            modifier = Modifier
                                                                                                .padding(4.dp),
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