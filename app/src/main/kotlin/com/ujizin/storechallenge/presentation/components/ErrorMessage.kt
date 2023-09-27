package com.ujizin.storechallenge.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ujizin.storechallenge.R
import com.ujizin.storechallenge.presentation.extensions.toCapitalize

@Composable
fun ErrorMessage(
    modifier: Modifier = Modifier,
    description: String,
    retry: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = description.toCapitalize())
        Button(
            modifier = Modifier.padding(top = 8.dp),
            onClick = retry,
        ) {
            Text(text = stringResource(id = R.string.try_again).toCapitalize())
        }
    }
}