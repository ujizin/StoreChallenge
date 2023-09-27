package com.ujizin.storechallenge.presentation.features.product.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ujizin.storechallenge.R
import com.ujizin.storechallenge.domain.Product
import com.ujizin.storechallenge.presentation.extensions.formatToEuroCurrency
import com.ujizin.storechallenge.presentation.features.product.detail.preview.ProductDetailStateProvider
import com.ujizin.storechallenge.themes.StoreChallengeTheme

@Composable
fun ProductDetailInfo(
    modifier: Modifier = Modifier,
    product: Product,
) {
    Column(modifier) {
        ProductDetailTitle(
            modifier = Modifier.padding(top = 16.dp),
            title = product.title
        )
        ProductDetailPriceContent(
            modifier = Modifier.padding(top = 8.dp),
            price = product.price,
            discountPercentage = product.discountPercentage,
            originalPrice = product.originalPrice
        )
        ProductDetailMoreInfo(
            modifier = Modifier.padding(top = 4.dp),
            rating = product.rating,
            stock = product.stock
        )
        ProductDetailDescription(
            modifier = Modifier.padding(top = 16.dp),
            description = product.description,
        )
    }
}

@Composable
private fun ProductDetailTitle(
    modifier: Modifier = Modifier,
    title: String,
) {
    Text(
        modifier = modifier,
        text = title,
        style = MaterialTheme.typography.displayLarge,
    )
}

@Composable
private fun ProductDetailDescription(
    modifier: Modifier = Modifier,
    description: String
) {
    Text(
        modifier = modifier,
        text = description,
    )
}

@Composable
private fun ProductDetailPriceContent(
    modifier: Modifier = Modifier,
    price: Double,
    originalPrice: Double,
    discountPercentage: Double,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = price.formatToEuroCurrency(),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = originalPrice.formatToEuroCurrency(),
            textDecoration = TextDecoration.LineThrough,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray
        )
        Text(
            text = "($discountPercentage%)",
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelMedium,
            letterSpacing = 0.25.sp,
        )
    }
}

@Composable
private fun ProductDetailMoreInfo(
    modifier: Modifier = Modifier,
    rating: Double,
    stock: Int,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProductDetailRating(
            rating = rating
        )
        VerticalDivider(Modifier.padding(horizontal = 4.dp))
        Text(
            text = stringResource(R.string.stock_label, stock),
            style = MaterialTheme.typography.labelLarge,
        )
    }

}

@Composable
private fun VerticalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    height: Dp = 16.dp,
    color: Color = Color.LightGray
) {
    Box(
        modifier
            .width(thickness)
            .height(height)
            .background(color)
    )
}

@Composable
private fun ProductDetailRating(
    modifier: Modifier = Modifier,
    rating: Double,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(rating.toInt()) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Rounded.Star,
                contentDescription = null
            )
        }
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = "$rating",
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun ProductDetailInfoPreview() {
    StoreChallengeTheme {
        ProductDetailInfo(product = ProductDetailStateProvider.product)
    }
}