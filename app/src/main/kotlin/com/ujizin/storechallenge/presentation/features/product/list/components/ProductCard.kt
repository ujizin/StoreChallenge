package com.ujizin.storechallenge.presentation.features.product.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ujizin.storechallenge.themes.StoreChallengeTheme


@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    title: String,
    imageUrl: String,
    rating: Double,
) {
    Row(modifier = modifier) {
        AsyncImage(
            modifier = Modifier
                .size(128.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp)),
            model = imageUrl,
            contentScale = ContentScale.Crop,
            contentDescription = title,
        )
        Column(Modifier.padding(8.dp)) {
            Text(text = title, maxLines = 3, overflow = TextOverflow.Ellipsis)
            ProductRating(
                modifier = Modifier.padding(top = 8.dp),
                rating = rating
            )
        }
    }
}

@Composable
fun ProductRating(modifier: Modifier = Modifier, rating: Double) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.Rounded.Star,
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = rating.toString()
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun ProductCardPreview() {
    StoreChallengeTheme {
        ProductItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            title = "foo",
            imageUrl = "",
            rating = 4.92
        )
    }
}