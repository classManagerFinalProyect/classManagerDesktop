package Screens.ScreenComponents.NavigationBar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun navigationBar(
    content: @Composable () -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
        content = {
            content()
        }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, Color.LightGray)),
        content = {
            Spacer(modifier = Modifier.padding(1.dp))
        }
    )
    Spacer(modifier = Modifier.padding(5.dp))
}