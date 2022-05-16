package Screens.ScreenItems.Others

import Utils.CommonErrors
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import data.local.Message

@Composable
fun floatToast(
    message: String,
    showToast: MutableState<Boolean>
) {
    ExtendedFloatingActionButton(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxWidth(0.9f),
        text = { Text("ERROR: $message") },
        onClick = {
            showToast.value = false
        }
    )
}