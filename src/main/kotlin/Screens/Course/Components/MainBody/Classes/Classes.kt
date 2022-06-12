package Screens.Course.Components.MainBody.Classes

import Screens.Course.ViewModelCourse
import Screens.ScreenItems.Dialogs.infoDialog
import Screens.ScreenItems.Cards.bigRectangleCard
import Screens.ScreenItems.Dialogs.confirmAlertDialog
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.remote.Class

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun classes(
    onClickClass: (Class) -> Unit,
    onClickBeginning: () -> Unit
) {
    var reload by remember { mutableStateOf(false) }

    var sizeDropMenu by remember { mutableStateOf(IntSize.Zero) }
    var expanded by remember { mutableStateOf(false) }
    var getMoreInformation by remember { mutableStateOf(false) }
    val showToast = remember { mutableStateOf(false) }
    var toastTitle by remember{ mutableStateOf("Title") }
    var toastText by remember{ mutableStateOf("Text") }
    var leaveCourse by remember { mutableStateOf(false) }
    val composableScope = rememberCoroutineScope()

    if(leaveCourse) {
        confirmAlertDialog(
            title = "¿Estas seguro que desea abandonar el curso?",
            subtitle = "Perderas el acceso a sus clases",
            onValueChangeGoBack = { leaveCourse = false},
            onClickAccept = {
                leaveCourse = false
                ViewModelCourse.leaveCourse(
                    composableScope = composableScope,
                    onFinished = {
                        onClickBeginning()
                    }

                )
            }
        )
    }

    LaunchedEffect(reload) {
        if (reload) {
            reload = false
        }
    }


    if(showToast.value) {
        infoDialog(
            showToast = showToast,
            title = toastTitle,
            text = toastText
        )
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

                    if (ViewModelCourse.currentUser.rol == "admin") {
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
                                            onClickCancel = {
                                                expanded = false
                                            },
                                            createClass = {
                                                reload = true
                                                expanded = false
                                                showToast.value = true
                                                toastTitle = "Clase creada"
                                                toastText = "Se ha creado la clase correctamente"
                                            }
                                        )
                                    }
                                )
                            }
                        )
                    }
                    TextButton(
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                sizeDropMenu = coordinates.size
                            },
                        onClick = {
                            leaveCourse = true
                        },
                        content = {
                            Text( text = "Abandonar curso")
                        }
                    )
                }
            )

            Column(
                content = {
                    if (!reload) {

                        LazyVerticalGrid(
                            cells = GridCells.Adaptive(200.dp),
                            content = {
                                this.itemsIndexed(ViewModelCourse.currentClasses) { _: Int, item: Class ->
                                    bigRectangleCard(
                                        title = item.name,
                                        subtitle = "${item.idPractices.size}",
                                        onClick = { onClickClass(item) }
                                    )
                                }
                            }
                        )
                    }
                }
            )
        }
    )
}