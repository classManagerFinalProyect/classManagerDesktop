package Screens.ScreenItems

import Screens.ScreenComponents.TopAppBar.Profile.ViewModelProfile
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.local.CurrentUser

@Composable
fun longButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(start = 40.dp, end = 40.dp)),
        onClick = {
            onClick()
        },
        content = {
            Text(text = "${text}")
        }
    )
}