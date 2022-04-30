package Screens.Course.Components.MainBody

import Screens.Course.Components.MainBody.Classes.addNewClass
import Screens.Course.Components.MainBody.Members.addMember
import Screens.Course.ViewModelCourse
import Screens.ScreenComponents.TopAppBar.CreateClass.mainCreateClass
import Screens.ScreenItems.bigRectangleCard
import Utils.LazyGridFor
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.remote.Class
import kotlinx.coroutines.delay
import java.awt.SystemColor.text

@Composable
fun classes(
    onClickClass: (Class) -> Unit
) {
    var reload by remember { mutableStateOf(false) }

    var sizeDropMenu by remember { mutableStateOf(IntSize.Zero) }
    var expanded by remember { mutableStateOf(false) }
    var getMoreInformation by remember { mutableStateOf(false) }

    LaunchedEffect(reload) {
        if (reload) {
            reload = false
        }
    }

    Row (
        content = {
            Column(
                modifier = Modifier.fillMaxHeight().fillMaxWidth(0.25f),
                content = {
                    Text(
                        text = "Opciones del curso",
                    )
                    TextButton(
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                sizeDropMenu = coordinates.size
                            },
                        onClick = {
                            getMoreInformation = !getMoreInformation
                        },
                        content = {
                            Text( text = "Ver información del curso")

                            DropdownMenu(
                                expanded = getMoreInformation,
                                onDismissRequest = { getMoreInformation = false },
                                content = {
                                    Column(
                                        modifier = Modifier.width(200.dp).padding(10.dp),
                                        content = {
                                            Text(
                                                text = ViewModelCourse.selectedCourse.name,
                                                fontSize = 20.sp,
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier.fillMaxWidth()
                                            )
                                            Spacer(modifier = Modifier.padding(10.dp))
                                            Text(
                                                text = "Usuarios: ${ViewModelCourse.selectedCourse.users.size}",
                                                fontSize = 15.sp,
                                                modifier = Modifier.fillMaxWidth()
                                            )

                                            Text(
                                                text = "Clases: ${ViewModelCourse.selectedCourse.classes.size}",
                                                fontSize = 15.sp,
                                                modifier = Modifier.fillMaxWidth()
                                            )

                                            Text(
                                                text = ViewModelCourse.selectedCourse.description,
                                                fontSize = 15.sp,
                                                modifier = Modifier.fillMaxWidth()
                                            )
                                        }
                                    )
                                }
                            )
                        }
                    )

                    TextButton(
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                sizeDropMenu = coordinates.size
                            },
                        onClick = {
                            expanded = !expanded
                        },
                        content = {
                            Text( text = "Añadir una nueva clase")

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                content = {
                                    addNewClass(
                                        reload = {
                                            reload = true
                                            expanded = false
                                        },
                                        onClickCancel = {
                                            expanded = false
                                        }
                                    )
                                }
                            )
                        }
                    )
                }
            )

            Column(
                content = {
                    if (!reload) {
                        LazyGridFor(
                            items = ViewModelCourse.currentClasses,
                            rowSize = 5,
                            itemContent = {
                                bigRectangleCard(
                                    title = it.name,
                                    subtitle = "${it.idPractices.size}",
                                    onClick = { onClickClass(it) }
                                )
                            }
                        )
                    }
                }
            )
        }
    )
}