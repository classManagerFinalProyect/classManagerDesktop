package ScreenItems

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun bigPasswordInputWithErrorMessage(
    value: String,
    onValueChangeValue: (String) -> Unit,
    valueError: Boolean,
    onValueChangeError: (Boolean) -> Unit,
    errorMesaje: String,
    validateError: (String) -> Boolean,
    mandatory: Boolean,
    keyboardType: KeyboardType
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
                      /*
                      val vector =
                          rememberImagePainter(
                              data =
                              if (hidden) "https://firebasestorage.googleapis.com/v0/b/class-manager-58dbf.appspot.com/o/appImages%2Fic_visibility_off.png?alt=media&token=0806f411-e1a8-4fd9-b803-ec53c4a7b4ac"
                              else "https://firebasestorage.googleapis.com/v0/b/class-manager-58dbf.appspot.com/o/appImages%2Fic_visibility.png?alt=media&token=93177a8c-40c9-4368-b6bc-6223a3fdb98b"
                          )
                      val description = if (hidden) "Ocultar contraseña" else "Revelar contraseña"
                      Icon(painter = vector, contentDescription = description)*/
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
          )
          val assistiveElementText = if (valueError) errorMesaje else if (mandatory) "*Obligatorio" else ""
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