package Screens.ScreenItems.Cards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.remote.Event


@Composable
fun bigVerticalCard(
    event: Event,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .width(260.dp)
            .height(310.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp),
        content = {
            Button(
                onClick = {
                    onClick()
                },
                contentPadding = PaddingValues(10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Black
                ),
                content = {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        content = {

                            Text(
                                text = event.name,
                                fontSize = 20.sp,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.padding(7.dp))
                            Text(
                                text = "Fecha del evento: ${event.date}",
                                fontSize = 15.sp,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.padding(5.dp))

                            Text(
                                text = "Hora inicial: ${event.initialTime}" ,
                                fontSize = 15.sp,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.padding(5.dp))

                            Text(
                                text = "Hora de finalización: ${event.finalTime}",
                                fontSize = 15.sp,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.padding(5.dp))

                            Text(
                                text = "Clase asignada: ${event.nameOfClass}",
                                fontSize = 15.sp,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    )
                }
            )
        }
    )
}