package com.example.egeniqsampleapp.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.egeniqsampleapp.R
import com.example.egeniqsampleapp.domain.model.BreedResult
import com.example.egeniqsampleapp.presentation.model.BreedItem

/**
 * Composable function for displaying a single bet item.
 *
 * @param item The bet item to display.
 * @param modifier The modifier to be applied to the `Card` composable.
 */
@Composable
fun BetItem(item: BreedItem, modifier: Modifier) {
    Card(modifier = modifier.padding(start = 8.dp, end = 8.dp, top = 2.dp, bottom = 2.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            val imageUrl = item.image?.url
            AsyncImage(
                model = if (imageUrl.isNullOrEmpty()) {
                    ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.cat_placeholder) // Use placeholder image resource
                        .build()
                } else {
                    ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .build()
                },
                placeholder = painterResource(R.drawable.cat_placeholder),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )

            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = item.name, style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = "Odds: ${item.temperament}", style = MaterialTheme.typography.body1)
            }
        }
    }
}
