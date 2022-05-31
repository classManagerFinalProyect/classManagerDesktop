package Screens.Course.Components.MainBody.Members

import Screens.Course.ViewModelCourse
import Screens.ScreenItems.DropDownMenu.bigSelectedDropDownMenu
import Screens.ScreenItems.Dialogs.infoDialog
import Screens.theme.blueDesaturated
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import data.local.UserWithRol
import data.remote.AppUser

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun members() {
    var expanded by remember { mutableStateOf(false) }
    var reload by remember { mutableStateOf(false) }
    val composableScope = rememberCoroutineScope()
    var deleteUser by remember { mutableStateOf(false) }
    var rolFilter by remember { mutableStateOf("ALL") }
    var selectedUserWithRol by remember { mutableStateOf(UserWithRol(AppUser("","","", arrayListOf(), arrayListOf(),"","",""),"")) }
    val suggestions: MutableList<String> = mutableListOf("admin","profesor","padre","alumno")

    var sizeDropMenu by remember { mutableStateOf(IntSize.Zero) }


    val showToast = remember { mutableStateOf(false) }
    var toastTitle by remember{ mutableStateOf("Title") }
    var toastText by remember{ mutableStateOf("Text") }

    if(showToast.value) {
        infoDialog(
            showToast = showToast,
            title = toastTitle,
            text = toastText
        )
    }

    LaunchedEffect(reload) {
        if(reload) {
            reload = false
        }
    }


    if (deleteUser) {
        AlertDialog(
            modifier = Modifier.width(300.dp),
            onDismissRequest = {
                deleteUser = false
            },
            title = {
                Text(text = "Desea eliminar a este usuario")
            },
            text = {
               Text(text = "Podrás volver a agregarlo en cualquier momento")
            },
            buttons = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    content = {
                        TextButton(
                            onClick = {
                                deleteUser = false
                            },
                            content = {
                                Text(text = "Cancelar")
                            }
                        )
                        TextButton(
                            onClick = {
                                ViewModelCourse.deleteUserToCurrentCourse(
                                    composableScope = composableScope,
                                    user = selectedUserWithRol,
                                    onFinished = {
                                        deleteUser = false
                                        reload = true
                                    }
                                )
                            },
                            content = {
                                Text(text = "Eliminar")
                            }
                        )
                    }
                )
            }
        )
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        content = {
            Column(
                modifier = Modifier.fillMaxHeight().fillMaxWidth(0.3f),
                content = {
                    Text(
                        text = "Miembros del curso",
                    )
                    TextButton(
                        onClick = {
                            ViewModelCourse.updateRolState(newValue = RolState.ALL)
                            rolFilter = "ALL"
                        },
                        content = {
                            Text(text = "Ver todos")
                        }
                    )
                    TextButton(
                        onClick = {
                            ViewModelCourse.updateRolState(newValue = RolState.ADMIN)
                            rolFilter = "admin"
                        },
                        content = {
                            Text(text = "Administradores")
                        }
                    )
                    TextButton(
                        onClick = {
                            ViewModelCourse.updateRolState(newValue = RolState.TEACHER)
                            rolFilter = "profesor"
                        },
                        content = {
                            Text(text = "Profesores")
                        }
                    )
                    TextButton(
                        onClick = {
                            ViewModelCourse.updateRolState(newValue = RolState.PARENTS)
                            rolFilter = "padre"
                        },
                        content = {
                            Text(text = "Padres")
                        }
                    )
                    TextButton(
                        onClick = {
                            ViewModelCourse.updateRolState(newValue = RolState.STUDENT)
                            rolFilter = "alumno"
                        },
                        content = {
                            Text(text = "Alumnos")
                        }
                    )


                }
            )
            Column(
                modifier = Modifier.fillMaxHeight(0.9f),
                content = {
                    Text(
                        text = "Miembros del curso: (${ViewModelCourse.currentMembers.size})",
                        fontSize = 20.sp,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Spacer(modifier = Modifier.padding(10.dp))

                    if(ViewModelCourse.currentUser.rol == "admin") {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            content = {

                                Button(
                                    modifier = Modifier
                                        .onGloballyPositioned { coordinates ->
                                            sizeDropMenu = coordinates.size
                                        },
                                    onClick = {
                                        expanded = !expanded
                                    },
                                    content = {
                                        Text( text = "Añadir un nuevo miembro")

                                        DropdownMenu(
                                            expanded = expanded,
                                            onDismissRequest = { expanded = false },
                                            content = {
                                                addMember(
                                                    onValueChangeExpanded = {
                                                        expanded = false
                                                    },
                                                    showToast = { title, text ->
                                                        reload = true
                                                        expanded = false
                                                        showToast.value = true
                                                        toastTitle = title
                                                        toastText = text
                                                    }
                                                )
                                            }
                                        )
                                    }
                                )

                                Spacer(modifier = Modifier.padding(5.dp))

                            }
                        )
                    }


                    Spacer(modifier = Modifier.padding(10.dp))
                    if (!reload) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 5.dp),
                            content = {
                                val grouped = ViewModelCourse.currentMembers.groupBy { it.user.name.uppercase().substring(0, 1) }.toSortedMap()

                                grouped.forEach { (header, items) ->
                                    var writeHeader: Boolean = checkIfListIsEmpty(items, rolFilter)

                                    items.forEach {

                                        if(writeHeader) {
                                            stickyHeader(
                                                content = {
                                                    Text(
                                                        text = header,
                                                        color = Color.White,
                                                        style = MaterialTheme.typography.caption,
                                                        modifier = Modifier
                                                            .background(blueDesaturated)
                                                            .padding(16.dp)
                                                            .fillMaxWidth()
                                                    )
                                                }
                                            )
                                            itemsIndexed(items) { _: Int, item ->
                                                if(item.rol == rolFilter || rolFilter == "ALL") {
                                                    Row(
                                                        horizontalArrangement = Arrangement.Start,
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        modifier = Modifier
                                                            .fillMaxWidth(),
                                                        content = {
                                                            TooltipArea(
                                                                tooltip = {
                                                                    Column(
                                                                        modifier = Modifier.background(Color.Transparent),
                                                                        content = {
                                                                            Text(
                                                                                text = item.user.email,
                                                                                fontWeight = FontWeight.Bold
                                                                            )

                                                                        }
                                                                    )
                                                                },
                                                                delayMillis = 400,
                                                                tooltipPlacement = TooltipPlacement.CursorPoint(
                                                                    offset = DpOffset(0.dp, 16.dp)
                                                                ),
                                                                content = {
                                                                    Text(
                                                                        text = item.user.name,
                                                                        modifier = Modifier
                                                                            .weight(1.5f)
                                                                            .padding(16.dp)
                                                                            .wrapContentWidth(Alignment.Start),
                                                                        style = MaterialTheme.typography.subtitle1
                                                                    )
                                                                }
                                                            )
                                                            var userRolExpanded by remember {  mutableStateOf(false) }
                                                            if (ViewModelCourse.currentUser.rol == "admin") {
                                                                var textSelectedRol by remember { mutableStateOf(item.rol) }


                                                                Row (
                                                                    verticalAlignment = Alignment.CenterVertically,
                                                                    horizontalArrangement = Arrangement.End,
                                                                    modifier = Modifier.fillMaxWidth(),
                                                                    content = {
                                                                        TextButton(
                                                                            onClick = {
                                                                                userRolExpanded = true
                                                                            },
                                                                            content = {
                                                                                Text(
                                                                                    text = textSelectedRol,
                                                                                    fontSize = 12.sp,
                                                                                    modifier = Modifier
                                                                                        .padding(end = 0.dp, start = 16.dp, top = 16.dp, bottom = 16.dp)
                                                                                        .wrapContentWidth(Alignment.End),
                                                                                    style = MaterialTheme.typography.subtitle1
                                                                                )
                                                                                DropdownMenu(
                                                                                    expanded = userRolExpanded,
                                                                                    onDismissRequest = {
                                                                                        userRolExpanded = false
                                                                                        textSelectedRol = item.rol
                                                                                    },
                                                                                    content = {
                                                                                        Column(
                                                                                            content = {
                                                                                                bigSelectedDropDownMenu(
                                                                                                    suggestions = suggestions,
                                                                                                    onValueChangeTextSelectedItem = { textSelectedRol = it }
                                                                                                )

                                                                                                TextButton(
                                                                                                    onClick = {
                                                                                                        userRolExpanded = false

                                                                                                        ViewModelCourse.changeRol(
                                                                                                            composableScope = composableScope,
                                                                                                            appUser = item.user,
                                                                                                            newRol = textSelectedRol,
                                                                                                            onFinished = {
                                                                                                                item.rol = textSelectedRol
                                                                                                            }
                                                                                                        )
                                                                                                    },
                                                                                                    content = {
                                                                                                        Text(text = "Guardar", textAlign = TextAlign.End)
                                                                                                    }
                                                                                                )
                                                                                            }
                                                                                        )
                                                                                    }
                                                                                )
                                                                            }
                                                                        )

                                                                        IconButton(
                                                                            onClick = {
                                                                                deleteUser = true
                                                                                selectedUserWithRol = item
                                                                            },
                                                                            content = {
                                                                                Icon(
                                                                                    imageVector = Icons.Default.Delete,
                                                                                    contentDescription = "Eliminar empleado",
                                                                                    modifier = Modifier.size(ButtonDefaults.IconSize)
                                                                                )
                                                                            }
                                                                        )
                                                                    }
                                                                )

                                                            }
                                                            else {
                                                                Text(
                                                                    text = item.rol,
                                                                    fontSize = 12.sp,
                                                                    modifier = Modifier
                                                                        .weight(0.5f)
                                                                        .padding(16.dp)
                                                                        .wrapContentWidth(Alignment.End),
                                                                    style = MaterialTheme.typography.subtitle1
                                                                )
                                                            }
                                                        }
                                                    )
                                                }
                                            }
                                        }
                                        writeHeader = false
                                    }
                                }

                            }
                        )
                    }
                }
            )
        }
    )
}

private fun checkIfListIsEmpty(
    items: List<UserWithRol>,
    rolFilter: String,
): Boolean {
    if (rolFilter == "ALL") return true
    items.forEach {
        if (it.rol == rolFilter) {
            return true
        }
    }
    return false
}