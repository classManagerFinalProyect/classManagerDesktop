package Screens.ScreenComponents.Header

import Screens.theme.blueDesaturated
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun header(
    content: @Composable () -> Unit
){
    Column(
        modifier = Modifier
            .background(blueDesaturated),
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                content = {
                    Column(
                        content =  {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                content = {
                                   content()
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}