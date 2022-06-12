package Screens.ScreenComponents.TopAppBar.items

import Screens.ScreenComponents.TopAppBar.CreateClass.mainCreateClass
import Screens.ScreenComponents.TopAppBar.CreateCourse.mainCreateCourse
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import data.remote.Course
import data.remote.Class

@Composable
fun dropDownMenuButton(
    textOfButton: String,
    onChangeGetDates: (Boolean) -> Unit,
    onCreateClass: (Class) -> Unit,
    onCreateCourse: (Course) -> Unit
) {

    var size by remember { mutableStateOf(Size.Zero) }
    var expanded by remember { mutableStateOf(false) }
    var createNewCourse by remember { mutableStateOf(false) }
    var createNewClass by remember { mutableStateOf(false) }




    Column(
        content = {
            Button(
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        size = coordinates.size.toSize()
                    },
                onClick = {
                    expanded = !expanded
                },
                content = {
                    Text(
                        text = textOfButton,
                        fontSize = 14.sp
                    )
                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },

                content = {
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            createNewCourse = true
                        },
                        content = {
                            Text(text = "Nuevo curso")
                        }
                    )
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            createNewClass = true
                        },
                        content = {
                            Text(text = "Nueva clase")
                        }
                    )
                }
            )

            DropdownMenu(
                expanded = createNewCourse,
                onDismissRequest = { createNewCourse = false},
                content = {
                    mainCreateCourse(
                        onChangeGetDates = { onChangeGetDates(it) },
                        onClickCancel = {
                            createNewCourse = false
                            expanded = true
                        },
                        onCreateCourse = {
                            onCreateCourse(it)

                        }
                    )
                }
            )

            DropdownMenu(
                expanded = createNewClass,
                onDismissRequest = { createNewClass = false},
                content = {
                    mainCreateClass(
                        onClickCancel = {

                            createNewClass = false
                            expanded = true
                        },
                        onCreateClass = {
                            createNewClass = false


                            onCreateClass(it)
                        }
                    )
                }
            )
        }
    )
}