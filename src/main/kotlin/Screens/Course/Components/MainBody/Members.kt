package Screens.Course.Components.MainBody

import Screens.Course.ViewModelCourse
import Utils.LazyGridFor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun members() {
    LazyGridFor(
        items = ViewModelCourse.currentMembers,
        rowSize = 5,
        itemContent = {
            Text(text = it.name)
        }
    )

}