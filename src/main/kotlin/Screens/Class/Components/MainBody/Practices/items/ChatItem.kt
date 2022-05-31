package Screens.Class.Components.MainBody.Practices.items

import Utils.AsyncImage
import Utils.loadImageBitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import data.local.CurrentUser
import data.local.Message
import data.remote.Chat
import java.time.LocalDate

@Composable
fun chatItem(
    message: Message
){
    Row(
        horizontalArrangement = if (CurrentUser.currentUser.id.equals(message.sentBy.id)) Arrangement.End else Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth(),
        content = {

            Surface(
                modifier = Modifier
                    .padding(8.dp, 4.dp)
                    .fillMaxWidth(0.8f),
                shape = RoundedCornerShape(8.dp),
                elevation = 2.dp,
                content = {
                    Row(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxSize(),
                        content = {
                            Image(
                                painter = painterResource("defaultUserImg.png"),
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, Color.Gray, CircleShape)
                                    .align(Alignment.CenterVertically),
                                contentScale = ContentScale.Crop,
                                contentDescription = "avatar",
                            )
                            Spacer(modifier = Modifier.padding(5.dp))
                            Column(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.8f),
                                verticalArrangement = Arrangement.Center,
                                content = {
                                    Text(
                                        text = message.message,
                                        style = MaterialTheme.typography.subtitle1,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                            )
                            Text(
                                text = "${LocalDate.now()}",
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