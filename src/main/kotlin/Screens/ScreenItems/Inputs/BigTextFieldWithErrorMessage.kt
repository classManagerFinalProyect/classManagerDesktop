package ScreenItems

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.awt.Cursor

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun bigTextFieldWithErrorMessage(
    text: String,
    value: String,
    onValueChange: (String) -> Unit,
    validateError: (String) -> Boolean,
    errorMessage: String,
    changeError: (Boolean) -> Unit,
    error: Boolean,
    mandatory: Boolean,
    KeyboardType : KeyboardType
) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(start = 40.dp, end = 40.dp)),
        content = {
            OutlinedTextField(
                value = value,
                onValueChange = {
                    onValueChange(it)
                    changeError(!validateError(it))
                },
                placeholder = { Text(text) },
                label = { Text(text = text) },
                singleLine = true,
                isError = error,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType),

                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray
                ),
                modifier = Modifier.fillMaxWidth()
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
                            (keyEvent.key  == Key.Delete && keyEvent.type == KeyEventType.KeyDown) -> {
                                if(value.isNotEmpty()) onValueChange(value.substring(0, value.length-1))
                                true
                            }
                            (keyEvent.key == Key.Backspace && keyEvent.type == KeyEventType.KeyDown) -> {
                                if(value.isNotEmpty()) onValueChange(value.substring(0, value.length-1))
                                true
                            }
                            else -> false
                        }
                    },
            )
            val assistiveElementText = if (error) errorMessage else if (mandatory) "*Obligatorio" else ""
            val assistiveElementColor = if (error) {
                MaterialTheme.colors.error
            } else {
                MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
            }
            Spacer(modifier = Modifier.padding(2.dp))
            Text(
                text = assistiveElementText,
                color = assistiveElementColor,
                style = MaterialTheme.typography.caption,
            )
        }
    )
}