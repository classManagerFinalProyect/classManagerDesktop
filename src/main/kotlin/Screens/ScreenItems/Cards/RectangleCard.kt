package Screens.ScreenItems.Cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun rectangleCard(
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp, 4.dp)
            .width(200.dp)
            .height(100.dp)
            .clickable {
                onClick()
            },
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
                                text = title,
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
                                        text = subtitle,
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