package Screens.Course.Components.MainBody.Events

import ScreenItems.bigTextFieldWithErrorMessage
import Screens.Course.ViewModelCourse
import Screens.ScreenComponents.TopAppBar.CreateClass.ViewModelCreateClass
import Screens.theme.blue
import Utils.isAlphabetic
import Utils.isDate
import Utils.isTime
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.local.CurrentUser
import data.local.RolUser
import data.remote.Class
import data.remote.Event


@Composable
fun modifierEvent(
    event: Event,
    onCloseRequest: () -> Unit
) {

    //Texts
    var (textName,onValueChangeNameText) = remember{ mutableStateOf(event.name) }
    var (nameError,nameErrorChange) = remember { mutableStateOf(false) }
    val messageNameClassError by remember { mutableStateOf("El nombre debe de contener únicamente caracteres alfabeticos") }

    var (textDate,onValueChangeDateText) = remember{ mutableStateOf(event.date) }
    var (dateError,dateErrorChange) = remember { mutableStateOf(false) }
    val messageDateClassError by remember { mutableStateOf("La fecha debe seguir el siguiente formato: dd/mm/yyyy") }

    var (textStartTime,onValueChangeStartTimeText) = remember{ mutableStateOf(event.initialTime) }
    var (startTimeError,startTimeErrorChange) = remember { mutableStateOf(false) }
    val messageStartTimeClassError by remember { mutableStateOf("Las horas deben seguir el siguente formato: hh:mm") }

    var (textFinalTime,onValueChangeFinalTimeText) = remember{ mutableStateOf(event.finalTime) }
    var (finalTimeError,finalTimeErrorChange) = remember { mutableStateOf(false) }
    val messageFinalTimeClassError by remember { mutableStateOf("Las horas deben seguir el siguente formato: hh:mm") }

    //Help variables
    val composableScope = rememberCoroutineScope()


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
                content = {
                    Spacer(modifier = Modifier.padding(15.dp))

                    Text(
                        text = "${event.nameOfClass}",
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
                        validateError = ::isAlphabetic,
                        errorMessage = messageNameClassError,
                        changeError = nameErrorChange,
                        error = nameError,
                        mandatory = true,
                        KeyboardType = KeyboardType.Text
                    )

                    bigTextFieldWithErrorMessage(
                        text = "Fecha el evento",
                        value = textDate,
                        onValueChange = onValueChangeDateText,
                        validateError = ::isDate,
                        errorMessage = messageDateClassError,
                        changeError = dateErrorChange,
                        error = dateError,
                        mandatory = true,
                        KeyboardType = KeyboardType.Text
                    )

                    bigTextFieldWithErrorMessage(
                        text = "Hora inicial",
                        value = textStartTime,
                        onValueChange = onValueChangeStartTimeText,
                        validateError = ::isTime,
                        errorMessage = messageStartTimeClassError,
                        changeError = startTimeErrorChange,
                        error = startTimeError,
                        mandatory = true,
                        KeyboardType = KeyboardType.Text
                    )

                    bigTextFieldWithErrorMessage(
                        text = "Hora final",
                        value = textFinalTime,
                        onValueChange = onValueChangeFinalTimeText,
                        validateError = ::isTime,
                        errorMessage = messageFinalTimeClassError,
                        changeError = finalTimeErrorChange,
                        error = finalTimeError,
                        mandatory = true,
                        KeyboardType = KeyboardType.Text
                    )

                    Spacer(modifier = Modifier.padding(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 40.dp, end = 40.dp),
                        content = {

                            Button(
                                modifier = Modifier.width(100.dp),
                                contentPadding = PaddingValues(
                                    start = 10.dp,
                                    top = 6.dp,
                                    end = 10.dp,
                                    bottom = 6.dp
                                ),
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = Color.White,
                                    backgroundColor = Color.Red
                                ),
                                onClick = {
                                      ViewModelCourse.deleteEvent(
                                          composableScope = composableScope,
                                          deleteEvent = event,
                                          onFinished = {
                                              onCloseRequest()
                                          }
                                      )

                                },
                                content = {
                                    Text(text = "Eliminar")
                                }
                            )

                            Button(
                                modifier = Modifier.width(100.dp),
                                contentPadding = PaddingValues(
                                    start = 10.dp,
                                    top = 6.dp,
                                    end = 10.dp,
                                    bottom = 6.dp
                                ),
                                onClick = {
                                    val updateEvent = Event(
                                        id = event.id,
                                        idOfCourse = event.idOfCourse,
                                        name = textName,
                                        nameOfClass = event.nameOfClass,
                                        finalTime = textFinalTime,
                                        initialTime = textStartTime,
                                        date = textDate
                                    )

                                    ViewModelCourse.updateEvent(
                                        composableScope = composableScope,
                                        event = updateEvent,
                                        onFinished = {
                                            ViewModelCourse.currentEvents.remove(event)
                                            onCloseRequest()
                                        }
                                    )
                                },
                                content = {
                                    Text(text = "Guardar")
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}