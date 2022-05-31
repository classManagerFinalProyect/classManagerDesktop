package Screens.Class.Components.MainBody.Practices

import ScreenItems.bigTextFieldWithErrorMessage
import Screens.Class.Components.MainBody.Practices.items.chatItem
import Screens.Class.Components.MainBody.Practices.items.sendBar
import Screens.Class.ViewModelClass
import Screens.Course.Components.MainBody.Classes.addNewClass
import Screens.ScreenItems.Dialogs.infoDialog
import Screens.ScreenItems.confirmAlertDialog
import Screens.theme.blue
import Utils.CommonErrors
import Utils.isAlphabetic
import Utils.isDate
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.local.CompletePractice
import data.local.Message
import data.remote.Chat
import data.remote.Practice


@Composable
fun practices() {

    var selectedPractices by remember {
        mutableStateOf(
            CompletePractice(
                Practice("","","","","","",""),
                Chat("", arrayListOf())
            )
        )
    }


    var commentsIsSelected by remember { mutableStateOf(false) }
    var textOfChat = remember { mutableStateOf("") }

    //Help variables
    val composableScope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }
    var reload by remember { mutableStateOf(false) }
    var reloadChat = remember { mutableStateOf(false) }
    var deletePractice by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(reloadChat.value){
        if (reloadChat.value) {
            reloadChat.value = false
        }
    }

    var showToast = remember { mutableStateOf(false) }
    var toastTitle by remember{ mutableStateOf("Title") }
    var toastText by remember{ mutableStateOf("Text") }

    if(showToast.value) {
        infoDialog(
            showToast = showToast,
            title = toastTitle,
            text = toastText
        )
    }

    LaunchedEffect(reload){
        if (reload) {
            reload = false
        }
    }

    if (deletePractice) {
        confirmAlertDialog(
            title = "¿Desea eliminar la práctica seleccionada?",
            subtitle = "No podrás volver a recuperarla",
            onValueChangeGoBack = { deletePractice = false },
            onClickAccept = {
                ViewModelClass.deletePractice(
                    composableScope = composableScope,
                    practice =  selectedPractices.practice,
                    onFinished = {
                        selectedPractices = CompletePractice(
                            Practice("","","","","","",""),
                            Chat("", arrayListOf())
                        )
                        reload = true
                    }
                )
            }
        )
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        content = {

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.3f),
                content = {
                    LazyColumn(
                        modifier = Modifier.fillMaxHeight(0.9f),
                        content = {

                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically,
                                    content = {
                                        Text(
                                            modifier = Modifier
                                                .padding(bottom = 5.dp),
                                            text = "Listado de prácticas",
                                            fontSize = 20.sp
                                        )
                                        if(ViewModelClass.currentUser.rol == "admin" || ViewModelClass.currentUser.rol == "profesor") {
                                            IconButton(
                                                onClick = {
                                                    expanded = !expanded
                                                },
                                                content = {

                                                    Icon(
                                                        imageVector = Icons.Default.Add,
                                                        contentDescription = "Añadir práctica",
                                                        tint = blue
                                                    )

                                                    DropdownMenu(
                                                        expanded = expanded,
                                                        onDismissRequest = { expanded = false },
                                                        content = {
                                                            addNewPractice(
                                                                showToast = { title, text ->
                                                                    reload = true
                                                                    expanded = false
                                                                    showToast.value = true
                                                                    toastTitle = title
                                                                    toastText = text
                                                                },
                                                            )
                                                        }
                                                    )
                                                }
                                            )
                                        }
                                    }
                                )
                            }

                            if(!reload){
                                itemsIndexed(ViewModelClass.currentPractices) { index: Int, item: CompletePractice ->
                                    Surface(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(60.dp),
                                        content = {
                                            Column(
                                                modifier = Modifier.fillMaxHeight(),
                                                content = {
                                                    Row(
                                                        content = {
                                                            TextButton(
                                                                onClick =  {
                                                                    selectedPractices = item

                                                                },
                                                                content = {
                                                                    Text(
                                                                        modifier = Modifier.fillMaxWidth(0.8f),
                                                                        text = item.practice.name,
                                                                        textAlign = TextAlign.Start
                                                                    )
                                                                }
                                                            )
                                                            if(ViewModelClass.currentUser.rol == "admin" || ViewModelClass.currentUser.rol == "profesor") {
                                                                IconButton(
                                                                    onClick = {
                                                                        selectedPractices = item
                                                                        deletePractice = true
                                                                    },
                                                                    content = {
                                                                        Icon(
                                                                            imageVector = Icons.Default.Delete,
                                                                            contentDescription = "Eliminar Práctica",
                                                                            modifier = Modifier.size(ButtonDefaults.IconSize)
                                                                        )
                                                                    }
                                                                )
                                                            }
                                                        }

                                                    )

                                                    Column(
                                                        modifier = Modifier
                                                            .fillMaxWidth(0.9f)
                                                            .border(BorderStroke(1.dp, Color.LightGray)),
                                                        content = {
                                                            Spacer(modifier = Modifier.padding(1.dp))
                                                        }
                                                    )
                                                }
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    )
                }
            )



            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        elevation = 4.dp,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .fillMaxHeight(0.9f),
                        content = {
                            Column(
                                content = {
                                    LazyColumn(
                                        modifier = Modifier.padding(start = 40.dp, end = 40.dp).fillMaxWidth().fillMaxHeight(0.85f),
                                        content = {
                                            if (!commentsIsSelected) {
                                                item {
                                                    Spacer(modifier =  Modifier.padding(10.dp))

                                                    Text(
                                                        modifier = Modifier.fillMaxWidth(),
                                                        text = selectedPractices.practice.name,
                                                        textAlign = TextAlign.Center,
                                                        fontSize = 25.sp
                                                    )
                                                    Spacer(modifier =  Modifier.padding(10.dp))
                                                }

                                                item {
                                                    bigTextFieldWithErrorMessage(
                                                        text = "Fecha de entrega",
                                                        value = selectedPractices.practice.deliveryDate,
                                                        onValueChange = { },
                                                        validateError = ::isDate,
                                                        errorMessage = CommonErrors.notValidDate,
                                                        changeError = {  },
                                                        error = false,
                                                        mandatory = false,
                                                        enabled = false,
                                                        KeyboardType = KeyboardType.Text
                                                    )
                                                }

                                                item {
                                                    bigTextFieldWithErrorMessage(
                                                        text = "Descriptcion",
                                                        value = selectedPractices.practice.description,
                                                        onValueChange = {  },
                                                        validateError = ::isAlphabetic,
                                                        errorMessage = CommonErrors.notAlphanumericText,
                                                        changeError = {  },
                                                        error = false,
                                                        mandatory = false,
                                                        enabled = false,
                                                        KeyboardType = KeyboardType.Text
                                                    )
                                                }

                                                item {
                                                    bigTextFieldWithErrorMessage(
                                                        text = "Annotation",
                                                        value = selectedPractices.practice.teacherAnnotation,
                                                        onValueChange = {  },
                                                        validateError = ::isAlphabetic,
                                                        errorMessage = CommonErrors.notAlphanumericText,
                                                        changeError = {  },
                                                        error = false,
                                                        mandatory = false,
                                                        enabled = false,
                                                        KeyboardType = KeyboardType.Text
                                                    )
                                                }
                                            }

                                            item {
                                                Spacer(modifier = Modifier.padding(10.dp))
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .border(BorderStroke(1.dp, Color.LightGray)),
                                                    content = {
                                                        Spacer(modifier = Modifier.padding(1.dp))
                                                    }
                                                )
                                                Row (
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Start,
                                                    modifier = Modifier
                                                        .clickable {
                                                            commentsIsSelected = !commentsIsSelected
                                                        },
                                                    content = {
                                                        Text(text = "Comentarios")
                                                    }
                                                )
                                                Spacer(modifier = Modifier.padding(8.dp))
                                            }

                                            if(!reloadChat.value){
                                                itemsIndexed(selectedPractices.chat.conversation) { index: Int, item: Message ->
                                                    chatItem(item)
                                                    Spacer(modifier = Modifier.padding(2.dp))
                                                }
                                            }
                                        }
                                    )

                                    if(selectedPractices.practice.id != "") {
                                        if(ViewModelClass.currentUser.rol != "padre")
                                            sendBar(textOfChat, focusRequester, selectedPractices, composableScope, reloadChat)
                                    }
                                    else
                                        Text(text = "Debes seleccionar una práctica", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(), fontSize = 20.sp, color = MaterialTheme.colors.primary)
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}

