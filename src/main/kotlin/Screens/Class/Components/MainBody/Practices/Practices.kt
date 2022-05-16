package Screens.Class.Components.MainBody.Practices

import ScreenItems.bigTextFieldWithErrorMessage
import Screens.Class.Components.MainBody.Practices.items.chatItem
import Screens.Class.ViewModelClass
import Screens.Course.Components.MainBody.Classes.addNewClass
import Screens.ScreenItems.confirmAlertDialog
import Screens.theme.blue
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import data.local.CompletePractice
import data.local.CurrentUser
import data.local.Message
import data.remote.Chat
import data.remote.Practice
import java.awt.SystemColor.text
import java.time.LocalDate
import java.time.LocalDate.now

@OptIn(ExperimentalComposeUiApi::class)
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

    val messageDateClassError by remember { mutableStateOf("La fecha debe seguir el siguiente formato: dd/mm/yyyy") }

    var commentsIsSelected by remember { mutableStateOf(false) }
    var textOfChat by remember { mutableStateOf("") }

    //Help variables
    val composableScope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }
    var reload by remember { mutableStateOf(false) }
    var reloadChat by remember { mutableStateOf(false) }
    var deletePractice by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(reloadChat){
        if (reloadChat) {
            reloadChat = false
        }
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
                                                            reload = {
                                                                reload = true
                                                                expanded = false
                                                            },
                                                        )
                                                    }
                                                )
                                            }
                                        )
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
                                                        errorMessage = messageDateClassError,
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
                                                        errorMessage = "",
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
                                                        errorMessage = "",
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

                                            if(!reloadChat){
                                                itemsIndexed(selectedPractices.chat.conversation) { index: Int, item: Message ->
                                                    chatItem(item)
                                                    Spacer(modifier = Modifier.padding(2.dp))
                                                }
                                            }
                                        }
                                    )

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight()
                                            .background(Color.Transparent),
                                        content = {

                                            OutlinedTextField(
                                                value = textOfChat,
                                                shape = RoundedCornerShape(30.dp),
                                                onValueChange = {
                                                    textOfChat = it
                                                },
                                                placeholder = {
                                                    Text(
                                                        modifier = Modifier
                                                            .alpha(ContentAlpha.medium),
                                                        text = "Mensaje",
                                                        color = Color.Black
                                                    )
                                                },
                                                textStyle = TextStyle(
                                                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                                                    color = Color.Black
                                                ),
                                                singleLine = false,
                                                keyboardOptions = KeyboardOptions(
                                                    imeAction = ImeAction.Send
                                                ),
                                                keyboardActions = KeyboardActions(
                                                    //Darle al botón de enviar del teclado
                                                ),
                                                colors = TextFieldDefaults.textFieldColors(
                                                    backgroundColor = Color.White,
                                                    cursorColor = Color.Black
                                                ),
                                                modifier = Modifier
                                                    .focusRequester(focusRequester)
                                                    .fillMaxWidth(0.85f)
                                                    .onPreviewKeyEvent { keyEvent ->
                                                        when {
                                                            (keyEvent.key == Key.DirectionRight) -> {
                                                                //  CursorSelectionBehaviour
                                                                true
                                                            }
                                                            (keyEvent.key == Key.DirectionLeft) -> {
                                                                TextRange(1, 0)
                                                                true
                                                            }
                                                            (keyEvent.key == Key.Delete && keyEvent.type == KeyEventType.KeyDown) -> {
                                                                if (textOfChat.isNotEmpty()) textOfChat = textOfChat.substring(0, textOfChat.length - 1)
                                                                true
                                                            }
                                                            (keyEvent.key == Key.Backspace && keyEvent.type == KeyEventType.KeyDown) -> {
                                                                if (textOfChat.isNotEmpty()) textOfChat = textOfChat.substring(0, textOfChat.length - 1)
                                                                true
                                                            }
                                                            else -> false
                                                        }
                                                    }
                                            )

                                            FloatingActionButton(
                                                backgroundColor = MaterialTheme.colors.primary,
                                                modifier = Modifier
                                                    .size(50.dp),
                                                content = {
                                                    Icon(
                                                        imageVector = Icons.Default.Send,
                                                        contentDescription = "Enviar mensaje",
                                                        tint = Color.White
                                                    )
                                                },
                                                onClick = {
                                                    if (selectedPractices.practice.id != ("")){
                                                        selectedPractices.chat.conversation.add(Message(textOfChat,CurrentUser.currentUser,now().toString()))
                                                        ViewModelClass.updateChat(
                                                            composableScope = composableScope,
                                                            chat = selectedPractices.chat,
                                                            onFinished = {
                                                                textOfChat = ""
                                                                reloadChat = true

                                                            }
                                                        )
                                                    }
                                                }
                                            )
                                        }
                                    )
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}