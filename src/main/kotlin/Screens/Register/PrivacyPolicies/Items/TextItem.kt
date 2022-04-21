package Screens.Register.PrivacyPolicies.Items

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun textItem(
    text: String
) {
    Text(
        text = text,
        textAlign = TextAlign.Justify,
        modifier = Modifier.padding(PaddingValues(start = 10.dp, end = 10.dp))
    )
}