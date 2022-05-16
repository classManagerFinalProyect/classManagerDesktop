package Screens.MainAppScreen.Items

import Screens.ScreenComponents.TopAppBar.CreateClass.ViewModelCreateClass
import Screens.ScreenComponents.TopAppBar.CreateClass.mainCreateClass
import Screens.ScreenComponents.TopAppBar.CreateCourse.mainCreateCourse
import Screens.ScreenComponents.TopAppBar.Profile.mainProfile
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.createTextLayoutResult
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Popup
import com.example.classmanagerandroid.Views.CreateClass.selectedDropDownMenuCurseItem
import data.local.CurrentUser
import data.remote.Course
import data.remote.Class
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

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
                        text = "${textOfButton}",
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
                            onCreateClass(it)
                        }
                    )
                }
            )
        }
    )
}