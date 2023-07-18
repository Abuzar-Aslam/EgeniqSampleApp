package com.example.egeniqsampleapp.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.egeniqsampleapp.domain.model.BreedResult
import com.example.egeniqsampleapp.presentation.model.BreedItem
import com.example.egeniqsampleapp.ui.theme.lightGrey

/**
 * Composable function for displaying a list of breed items.
 *
 * @param list The lazy paging items representing the breeds to display.
 * @param modifier The modifier to be applied to the root composable.
 */
@Composable
fun displayBreedList(list: LazyPagingItems<BreedItem>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .background(color = lightGrey)
            .padding(top = 8.dp)
    ) {
        items(list.itemCount) { index ->
            list[index]?.let {
                BetItem(
                    item = it,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )
            }
        }
    }
}