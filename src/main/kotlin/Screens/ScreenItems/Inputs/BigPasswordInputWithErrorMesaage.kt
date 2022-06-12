package Screens.ScreenItems.Inputs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun bigPasswordInputWithErrorMessage(
    value: String,
    onValueChangeValue: (String) -> Unit,
    valueError: Boolean,
    onValueChangeError: (Boolean) -> Unit,
    errorMessage: String,
    validateError: (String) -> Boolean,
    mandatory: Boolean,
    keyboardType: KeyboardType,
    keyActionEnter: () -> Unit
) {

    var hidden by remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(start = 40.dp, end = 40.dp)),
      content = {
          OutlinedTextField(
              value = value,
              onValueChange = { it2 ->
                  onValueChangeValue(it2)
                  onValueChangeError(!validateError(it2))
              },

              keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
              placeholder = { Text(text = "Escribe su contraseña") },
              label = { Text(text = "Contraseña") },
              isError = false,
              visualTransformation = if (hidden) PasswordVisualTransformation() else VisualTransformation.None,
              trailingIcon = {
                  IconButton(onClick = { hidden = !hidden }) {

                      val vector =
                          painterResource(
                              resourcePath =
                              if (hidden) "ic_visibility_off.png"
                              else "ic_visibility.png"
                          )
                      val description = if (hidden) "Ocultar contraseña" else "Revelar contraseña"
                      Icon(
                          painter = vector,
                          contentDescription = description,
                          modifier = Modifier.size(28.dp)
                      )
                  }
              },
              singleLine = true,
              colors = TextFieldDefaults.outlinedTextFieldColors(
                  focusedBorderColor = Color.Gray,
                  unfocusedBorderColor = Color.LightGray
              ),
              modifier = Modifier
                  .fillMaxWidth()
                  .focusRequester(focusRequester)
                  .onPreviewKeyEvent {
                      when{
                          (it.key == Key.Enter && it.type == KeyEventType.KeyDown) -> {
                              keyActionEnter()
                              true
                          }
                          else -> false
                      }
                  }
          )
          val assistiveElementText = if (valueError) errorMessage else if (mandatory) "*Obligatorio" else ""
          val assistiveElementColor = if (valueError) {
              MaterialTheme.colors.error
          } else {
              MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
          }
          Text(
              text = assistiveElementText,
              color = assistiveElementColor,
              style = MaterialTheme.typography.caption,
          )
      }
    )



}