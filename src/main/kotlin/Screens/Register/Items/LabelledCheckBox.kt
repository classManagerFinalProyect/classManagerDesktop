package com.example.classmanagerandroid.Views.Register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun labelledCheckbox(
    labelText: String,
    isCheckedValue: Boolean,
    onValueChangeCheked: (Boolean) -> Unit,
    onClickText: () -> Unit
) {
    Row(
        modifier = Modifier.padding(start = 30.dp,8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isCheckedValue,
            onCheckedChange = { onValueChangeCheked(it) },
            enabled = true,
            colors = CheckboxDefaults.colors(Color.Blue)
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = "${labelText}",
            modifier = Modifier
                .clickable{
                    onClickText()
                },
            fontSize = 17.sp,
            color = Color.Blue
        )
        Spacer(modifier = Modifier.padding(25.dp))
    }
}