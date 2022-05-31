package Screens.ScreenComponents.TopAppBar.items

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

@Composable
fun dropDownMenuCourses(
    suggestions: MutableList<Course>,
    nameOfMenu: String,
    onClick: (Course) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedText by remember { mutableStateOf(nameOfMenu) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

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
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle(fontSize = 14.sp),
                value = selectedText,
                onValueChange = {  },
                enabled = true,
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        textFieldSize = coordinates.size.toSize()
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
                    .width(with(LocalDensity.current) { textFieldSize.width.toDp() }),
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