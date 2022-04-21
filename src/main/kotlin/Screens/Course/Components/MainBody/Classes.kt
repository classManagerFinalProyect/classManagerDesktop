package Screens.Course.Components.MainBody

import Screens.Course.ViewModelCourse
import Screens.ScreenItems.bigRectangleCard
import Utils.LazyGridFor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import data.remote.Class

@Composable
fun classes(
    onClickClass: (Class) -> Unit
) {
    LazyGridFor(
        items = ViewModelCourse.currentClasses,
        rowSize = 5,
        itemContent = {
            bigRectangleCard(
                title = it.name,
                subtitle = "${it.idPractices.size}",
                onClick = { onClickClass(it) }
            )
        }
    )
}