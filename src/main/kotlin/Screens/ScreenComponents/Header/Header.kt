package Screens.ScreenComponents.Header

import Screens.Course.ViewModelCourse.Companion.selectedCourse
import Screens.theme.blueDesaturated
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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