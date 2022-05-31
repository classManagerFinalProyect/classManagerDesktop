package Screens.Class.Components.MainBody.Practices

import Screens.ScreenItems.Inputs.bigTextFieldWithErrorMessage
import Screens.Class.ViewModelClass
import Screens.ScreenItems.Others.floatToast
import Utils.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import data.remote.Practice
import kotlinx.coroutines.delay

@Composable
fun addNewPractice(
    showToast: (String, String) -> Unit
) {

    //Texts
    var textName by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false) }

    var textDeliveryDate by remember{ mutableStateOf("") }
    var deliveryDateError by remember { mutableStateOf(false) }

    var textDescription by remember{ mutableStateOf("") }
    var descriptionError by remember { mutableStateOf(false) }

    var textAnnotation by remember{ mutableStateOf("") }
    var annotationError by remember { mutableStateOf(false) }


    //Help variables
    val composableScope = rememberCoroutineScope()
    val showFloatToast = remember { mutableStateOf(false) }

    LaunchedEffect(showFloatToast.value) {
        if(showFloatToast.value) {
            delay(1500L)
            showFloatToast.value = false
        }
    }


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
                                showToast(
                                    "Práctica creada",
                                    "Se ha creado la práctica con exito"
                                )
                            }
                        )
                    }
                    else {
                        showFloatToast.value = true
                    }
                },
                content = {
                    Text(text = "Crear práctica")
                }
            )

            Spacer(modifier = Modifier.padding(10.dp))
            if(showFloatToast.value){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        floatToast(
                            message = "ERROR: Debes de rellenar todos los campos correctamente",
                            showToast = showFloatToast
                        )
                    }
                )

            }
        }
    )
}