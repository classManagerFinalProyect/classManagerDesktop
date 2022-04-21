package Screens.Register.PrivacyPolicies.Items

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun textTitle(
    text: String
) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(top = 8.dp, bottom = 10.dp))
    )
}