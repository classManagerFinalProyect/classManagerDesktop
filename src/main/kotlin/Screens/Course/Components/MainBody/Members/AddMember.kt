package Screens.Course.Components.MainBody.Members

import Screens.Course.ViewModelCourse
import Screens.ScreenItems.DropDownMenu.bigSelectedDropDownMenu
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp

@Composable
fun addMember(
    onValueChangeExpanded: (Boolean) -> Unit,
    showToast: (String, String) -> Unit
) {


    val composableScope = rememberCoroutineScope()
    var idOfUser by remember { mutableStateOf("") }
    val suggestion: MutableList<String> = mutableListOf("admin","profesor","padre","alumno")
    var textSelectedRol by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }



    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
            .width(320.dp),
        content = {
            Spacer(modifier = Modifier.padding(10.dp))
            TextField(
                value = idOfUser,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .padding(
                        PaddingValues(
                            start = 20.dp,
                            end = 20.dp
                        )
                    ),
                label = {
                    Text(text = "Id del usuario")
                },
                placeholder = {
                    Text(text = "Añadir la id de un usuario")
                },
                onValueChange = {
                    idOfUser = it
                },
                singleLine = true,
            )
            Spacer(modifier = Modifier.padding(6.dp))

            bigSelectedDropDownMenu (
                suggestions = suggestion,
                onValueChangeTextSelectedItem = { textSelectedRol = it }
            )

            Spacer(modifier = Modifier.padding(7.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        PaddingValues(
                            end = 20.dp
                        )
                    ),
                content = {
                    TextButton(
                        onClick = {
                            onValueChangeExpanded(false)
                        },
                        content = {
                            Text(text = "Cancelar")
                        }
                    )
                    TextButton(
                        onClick = {
                            if(textSelectedRol != "") {
                                ViewModelCourse.addNewMember(
                                    rol = textSelectedRol,
                                    composableScope = composableScope,
                                    idOfUser = idOfUser,
                                    onFinished = {
                                        showToast(
                                            "Usuario agregado.",
                                            "El usuario se ha agregado correctamente"
                                        )
                                    }
                                )
                            }
                            else {
                                showToast("Debes de asignarle un rol al usuario","Debes de usar el elemento desplegable para seleccionar un rol")
                            }
                        },
                        content = {
                            Text(text = "Save")
                        }
                    )

                }
            )

        }
    )
}