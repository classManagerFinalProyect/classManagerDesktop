package Screens.Course.Components.MainBody

import Screens.Course.ViewModelCourse
import Utils.LazyGridFor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun events() {
    LazyGridFor(
        items = ViewModelCourse.currentEvents,
        rowSize = 5,
        itemContent = {
            Text(text = it.name)
        }
    )

}