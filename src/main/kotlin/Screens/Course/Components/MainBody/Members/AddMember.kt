package Screens.Course.Components.MainBody.Members

import Screens.Course.Components.MainBody.ContentState
import Screens.Course.ViewModelCourse
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.classmanagerandroid.Views.Course.bigSelectedDropDownMenu

@Composable
fun addMember(
    onValueChangeExpanded: (Boolean) -> Unit
) {


    val composableScope = rememberCoroutineScope()
    var idOfUser by remember { mutableStateOf("") }
    val suggestion: MutableList<String> = mutableListOf("admin","profesor","padre","alumno")
    var textSelectedRol by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight(0.8f),
        content = {
            Spacer(modifier = Modifier.padding(10.dp))
            TextField(
                value = idOfUser,
                modifier = Modifier
                    .fillMaxWidth()
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
                            ViewModelCourse.addNewMember(
                                rol = textSelectedRol,
                                composableScope = composableScope,
                                idOfUser = idOfUser,
                                onFinished = {
                                    ViewModelCourse.updateContentState(newValue = ContentState.CLASSES)
                                }
                            )
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