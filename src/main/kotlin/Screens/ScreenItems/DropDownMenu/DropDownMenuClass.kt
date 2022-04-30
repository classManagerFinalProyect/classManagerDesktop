package Screens.MainAppScreen.Items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import data.remote.Course
import data.remote.Class

@Composable
fun dropDownMenuClassTransparent(
    suggestions: MutableList<Class>,
    nameOfMenu: String,
    onClick: (Class) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(nameOfMenu) }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }
    var editItem = remember{ mutableStateOf(false) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(
        content = {
            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    backgroundColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                value = selectedText,
                textStyle = TextStyle(fontSize = 14.sp),
                onValueChange = {  },
                enabled = true,
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        textfieldSize = coordinates.size.toSize()
                    }
                    .width(200.dp),
                trailingIcon = {
                    Icon(
                        imageVector =  icon,
                        tint = Color.White,
                        contentDescription = "arrowExpanded",
                        modifier = Modifier
                            .clickable { expanded = !expanded }
                    )
                },
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textfieldSize.width.toDp() }),
                content = {
                    suggestions.forEach { label ->
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                onClick(label)
                            },
                            content = {
                                Text(text = label.name)
                            }
                        )
                    }
                }
            )
        }
    )
}