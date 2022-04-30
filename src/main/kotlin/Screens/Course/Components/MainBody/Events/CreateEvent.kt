package Screens.Course.Components.MainBody.Events

import ScreenItems.bigTextFieldWithErrorMessage
import Screens.Course.ViewModelCourse
import Screens.Course.Components.MainBody.Events.Items.dropDownMenuClass
import Utils.isAlphabetic
import Utils.isDate
import Utils.isTime
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import data.remote.Class
import data.remote.Event

@Composable
fun createEvent(
    onCloseRequest: () -> Unit
) {

    //Texts
    var (textName,onValueChangeNameText) = remember{ mutableStateOf("") }
    var (nameError,nameErrorChange) = remember { mutableStateOf(false) }
    val messageNameClassError by remember { mutableStateOf("El nombre debe de contener únicamente caracteres alfabeticos") }

    var (textDate,onValueChangeDateText) = remember{ mutableStateOf("") }
    var (dateError,dateErrorChange) = remember { mutableStateOf(false) }
    val messageDateClassError by remember { mutableStateOf("La fecha debe seguir el siguiente formato: dd/mm/yyyy") }

    var (textStartTime,onValueChangeStartTimeText) = remember{ mutableStateOf("") }
    var (startTimeError,startTimeErrorChange) = remember { mutableStateOf(false) }
    val messageStartTimeClassError by remember { mutableStateOf("Las horas deben seguir el siguente formato: hh:mm") }

    var (textFinalTime,onValueChangeFinalTimeText) = remember{ mutableStateOf("") }
    var (finalTimeError,finalTimeErrorChange) = remember { mutableStateOf(false) }
    val messageFinalTimeClassError by remember { mutableStateOf("Las horas deben seguir el siguente formato: hh:mm") }

    var textClasses by remember{ mutableStateOf(Class("","","", arrayListOf(), arrayListOf(),"")) }


    //Help variables
    val composableScope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }



    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.width(400.dp),
        content = {
            Spacer(modifier = Modifier.padding(10.dp))

            dropDownMenuClass(
                suggestions = ViewModelCourse.currentClasses,
                nameOfMenu = "Sin Asignar",
                onClick = {
                    textClasses = it
                }
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

            Button(
                contentPadding = PaddingValues(
                    start = 10.dp,
                    top = 6.dp,
                    end = 10.dp,
                    bottom = 6.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(start = 40.dp, end = 40.dp)),
                onClick = {
                    val newEvent = Event(
                        id = "",
                        idOfCourse = ViewModelCourse.selectedCourse.id,
                        name = textName,
                        nameOfClass = textClasses.name,
                        finalTime = textFinalTime,
                        initialTime = textStartTime,
                        date = textDate
                    )

                    ViewModelCourse.addNewEvent(
                        composableScope = composableScope,
                        newEvent = newEvent,
                        onFinished = {
                            onCloseRequest()
                        }
                    )


                },
                content = {
                    Text(text = "Crear evento")
                }
            )
        }
    )
}