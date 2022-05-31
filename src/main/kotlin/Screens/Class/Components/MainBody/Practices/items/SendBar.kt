package Screens.Class.Components.MainBody.Practices.items

import Screens.Class.ViewModelClass
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import data.local.CompletePractice
import data.local.CurrentUser
import data.local.Message
import kotlinx.coroutines.CoroutineScope
import java.time.LocalDate

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun sendBar(
    textOfChat: MutableState<String>,
    focusRequester: FocusRequester,
    selectedPractices: CompletePractice,
    composableScope: CoroutineScope,
    reloadChat: MutableState<Boolean>
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Transparent),
        content = {

            OutlinedTextField(
                value = textOfChat.value,
                shape = RoundedCornerShape(30.dp),
                onValueChange = {
                    textOfChat.value = it
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
                singleLine = true,
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
                    if (selectedPractices.practice.id != ("")) {
                        selectedPractices.chat.conversation.add(
                            Message(
                                textOfChat.value,
                                CurrentUser.currentUser,
                                LocalDate.now().toString()
                            )
                        )
                        ViewModelClass.updateChat(
                            composableScope = composableScope,
                            chat = selectedPractices.chat,
                            onFinished = {
                                textOfChat.value = ""
                                reloadChat.value = true
                            }
                        )
                    }
                }
            )
        }
    )
}