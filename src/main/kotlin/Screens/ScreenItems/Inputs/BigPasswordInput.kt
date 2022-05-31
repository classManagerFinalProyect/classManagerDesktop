package Screens.ScreenItems.Inputs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun bigPasswordInput(
    value: String,
    onValueChangeValue: (String) -> Unit,
) {
    var hidden by remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }

    OutlinedTextField(
        value = value,
        onValueChange = { text ->
            onValueChangeValue(text)
        },
        placeholder = { Text(text = "Escribe su contraseña") },
        label = { Text(text = "Contraseña") },
        isError = false,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        visualTransformation = if (hidden) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            IconButton(onClick = { hidden = !hidden }) {
                painterResource("logoDani.png")
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
        singleLine = false,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.LightGray
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(start = 40.dp, end = 40.dp))
            .focusRequester(focusRequester)
    )

}