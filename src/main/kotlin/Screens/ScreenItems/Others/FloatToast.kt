package Screens.ScreenItems.Others

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun floatToast(
    message: String,
    showToast: MutableState<Boolean>
) {
    ExtendedFloatingActionButton(
        backgroundColor = MaterialTheme.colors.primary.copy(0.7f),
        modifier = Modifier
            .fillMaxWidth(0.8f),
        text = { Text(text = message, textAlign = TextAlign.Center) },
        onClick = {
            showToast.value = false
        }
    )
}