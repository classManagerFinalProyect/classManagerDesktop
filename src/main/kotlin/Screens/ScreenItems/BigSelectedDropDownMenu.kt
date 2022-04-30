package com.example.classmanagerandroid.Views.Course

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@Composable
fun bigSelectedDropDownMenu(
    suggestions: List<String>,
    onValueChangeTextSelectedItem: (String) -> Unit,

) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("Sin asignar") }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(
        content = {
            OutlinedTextField(
                value = selectedText,
                onValueChange = { selectedText = it },
                enabled = false,
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .onGloballyPositioned { coordinates ->
                        textFieldSize = coordinates.size.toSize()
                    },
                trailingIcon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = "arrowExpanded",
                        modifier = Modifier.clickable { expanded = !expanded }
                    )
                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textFieldSize.width.toDp() }),
                content = {
                    suggestions.forEach { label ->
                        DropdownMenuItem(
                            onClick = {
                                selectedText = label
                                onValueChangeTextSelectedItem(label)
                                expanded = false
                            }
                        ) {
                            Text(text = label)
                        }
                    }
                }
            )
        }
    )
}
