package Utils

import Screens.Course.Components.MainBody.Events.modifierEvent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun <T> LazyGridFor(
    items: MutableList<T>,
    rowSize: Int = 1,
    itemContent: @Composable BoxScope.(T) -> Unit,
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {

            items(
                items = items.chunked(rowSize),
            ) { row ->
                Row(
                    modifier = Modifier
                        .fillParentMaxWidth(),
                    content = {
                        for ((index, item) in row.withIndex()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(1f / (rowSize - index)),
                                content = {
                                    itemContent(item)
                                }
                            )
                        }
                    }
                )
            }

        }
    )
}