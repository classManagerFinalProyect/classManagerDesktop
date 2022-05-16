package Screens.Class.Components.MainBody.Practices

import ScreenItems.bigTextFieldWithErrorMessage
import Screens.Class.ViewModelClass
import Screens.Course.ViewModelCourse
import Utils.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import data.remote.Event
import data.remote.Practice

@Composable
fun addNewPractice(
    reload: () -> Unit,
) {

    //Texts
    var textName by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false) }
    val messageNameClassError by remember { mutableStateOf("El nombre debe de contener únicamente caracteres alfanuméricos") }

    var textDeliveryDate by remember{ mutableStateOf("") }
    var deliveryDateError by remember { mutableStateOf(false) }
    val messageDeliveryDateError by remember { mutableStateOf("La fecha debe seguir el siguiente formato: dd/mm/yyyy") }

    var textDescription by remember{ mutableStateOf("") }
    var descriptionError by remember { mutableStateOf(false) }
    val messageDescriptionDateError by remember { mutableStateOf("Debes usar caracteres alfanuméricos y nunca más de 30 carácteres.") }

    var textAnnotation by remember{ mutableStateOf("") }
    var annotationError by remember { mutableStateOf(false) }
    val annotationDateError by remember { mutableStateOf("La fecha debe seguir el siguiente formato: dd/mm/yyyy") }

    //Help variables
    val composableScope = rememberCoroutineScope()


    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.width(400.dp),
        content = {
            Spacer(modifier = Modifier.padding(10.dp))

            bigTextFieldWithErrorMessage(
                text = "Nombre de la práctica",
                value = textName,
                onValueChange = {  textName = it },
                validateError = ::isValidName,
                errorMessage = CommonErrors.notValidName,
                changeError = { nameError = it },
                error = nameError,
                mandatory = true,
                KeyboardType = KeyboardType.Text,
                enabled = true
            )

            bigTextFieldWithErrorMessage(
                text = "Fecha de entrega",
                value = textDeliveryDate,
                onValueChange = { textDeliveryDate = it },
                validateError = ::isDate,
                errorMessage = CommonErrors.notValidDate,
                changeError = { deliveryDateError = it },
                error = deliveryDateError,
                mandatory = false,
                KeyboardType = KeyboardType.Text,
                enabled = true
            )

            bigTextFieldWithErrorMessage(
                text = "Descripción de la práctica",
                value = textDescription,
                onValueChange = {  textDescription = it },
                validateError = ::isValidDescription,
                errorMessage = CommonErrors.notValidDescription,
                changeError = { descriptionError = it },
                error = descriptionError,
                mandatory = false,
                KeyboardType = KeyboardType.Text,
                enabled = true
            )

            bigTextFieldWithErrorMessage(
                text = "Anotación del profesor",
                value = textAnnotation,
                onValueChange = {  textAnnotation = it },
                validateError = ::isAlphanumeric,
                errorMessage = CommonErrors.notAlphanumericText,
                changeError = { annotationError = it },
                error = annotationError,
                mandatory = false,
                KeyboardType = KeyboardType.Text,
                enabled = true
            )

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

                    if(isValidDescription(textDescription) && isValidName(textName) && isDate(textDeliveryDate) && isAlphanumeric(textAnnotation)) {
                        val newPractice = Practice(
                            id = "",
                            deliveryDate = textDeliveryDate,
                            description = textDescription,
                            idOfChat = "",
                            teacherAnnotation = textAnnotation,
                            name = textName,
                            idOfClass = ViewModelClass.selectedClass.id
                        )
                        ViewModelClass.addPractice(
                            practice = newPractice,
                            composableScope = composableScope,
                            onFinished = {
                                reload()
                            }
                        )
                    }
                },
                content = {
                    Text(text = "Crear práctica")
                }
            )
            Spacer(modifier = Modifier.padding(10.dp))

        }
    )
}