package Screens.Course.Components.MainBody.Events.Components

import Screens.ScreenItems.Inputs.bigTextFieldWithErrorMessage
import Utils.CommonErrors
import Utils.isDate
import Utils.isTime
import Utils.isValidName
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.remote.Event


@Composable
fun seeEvent(
    event: Event,
    onCloseRequest: () -> Unit,
) {

    //Texts
    val (textName,onValueChangeNameText) = remember{ mutableStateOf(event.name) }
    val (nameError,nameErrorChange) = remember { mutableStateOf(false) }

    val (textDate,onValueChangeDateText) = remember{ mutableStateOf(event.date) }
    val (dateError,dateErrorChange) = remember { mutableStateOf(false) }

    val (textStartTime,onValueChangeStartTimeText) = remember{ mutableStateOf(event.initialTime) }
    val (startTimeError,startTimeErrorChange) = remember { mutableStateOf(false) }

    val (textFinalTime,onValueChangeFinalTimeText) = remember{ mutableStateOf(event.finalTime) }
    val (finalTimeError,finalTimeErrorChange) = remember { mutableStateOf(false) }


    Card(
        modifier = Modifier
            .padding(4.dp)
            .width(550.dp)
            .height(600.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp),
        content = {

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
                content = {
                    Spacer(modifier = Modifier.padding(15.dp))

                    Text(
                        text = event.nameOfClass,
                        fontSize = 20.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Monospace
                    )

                    Spacer(modifier = Modifier.padding(10.dp))

                    bigTextFieldWithErrorMessage(
                        text = "Nombre del evento",
                        value = textName,
                        onValueChange = onValueChangeNameText,
                        validateError = ::isValidName,
                        errorMessage = CommonErrors.notValidName,
                        changeError = nameErrorChange,
                        error = nameError,
                        mandatory = false,
                        KeyboardType = KeyboardType.Text,
                        enabled = false
                    )

                    bigTextFieldWithErrorMessage(
                        text = "Fecha el evento",
                        value = textDate,
                        onValueChange = onValueChangeDateText,
                        validateError = ::isDate,
                        errorMessage = CommonErrors.notValidDate,
                        changeError = dateErrorChange,
                        error = dateError,
                        mandatory = false,
                        KeyboardType = KeyboardType.Text,
                        enabled = false
                    )

                    bigTextFieldWithErrorMessage(
                        text = "Hora inicial",
                        value = textStartTime,
                        onValueChange = onValueChangeStartTimeText,
                        validateError = ::isTime,
                        errorMessage = CommonErrors.notValidTime,
                        changeError = startTimeErrorChange,
                        error = startTimeError,
                        mandatory = false,
                        KeyboardType = KeyboardType.Text,
                        enabled = false
                    )

                    bigTextFieldWithErrorMessage(
                        text = "Hora final",
                        value = textFinalTime,
                        onValueChange = onValueChangeFinalTimeText,
                        validateError = ::isTime,
                        errorMessage = CommonErrors.notValidTime,
                        changeError = finalTimeErrorChange,
                        error = finalTimeError,
                        mandatory = false,
                        KeyboardType = KeyboardType.Text,
                        enabled = false
                    )

                    Spacer(modifier = Modifier.padding(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 40.dp, end = 40.dp),
                        content = {

                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(
                                    start = 10.dp,
                                    top = 6.dp,
                                    end = 10.dp,
                                    bottom = 6.dp
                                ),
                                onClick = {
                                    onCloseRequest()
                                },
                                content = {
                                    Text(text = "Aceptar")
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}