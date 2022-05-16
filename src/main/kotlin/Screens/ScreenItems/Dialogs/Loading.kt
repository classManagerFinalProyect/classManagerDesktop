package Screens.ScreenItems.Dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun loadingDialog(
    loading : MutableState<Boolean>,
    informativeText: String
) {

    if (loading.value) {
        AlertDialog(
            backgroundColor = Color.Transparent,
            modifier = Modifier
                .fillMaxSize(0.4f),
            onDismissRequest = { },
            title = { },
            text = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = {
                        CircularProgressIndicator(
                            color = Color.Black,
                            modifier = Modifier
                                .height(40.dp)
                                .width(40.dp)
                        )
                        Text(
                            text = informativeText,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    }
                )
            },
            buttons = {}
        )
    }
}