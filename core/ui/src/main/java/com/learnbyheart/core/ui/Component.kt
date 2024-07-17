package com.learnbyheart.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoadingProgress() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color.White)
    }
}

@Composable
fun CategoryItem(
    category: String,
    isSelected: Boolean,
    onClicked: (String) -> Unit = {},
) {

    Box(
        modifier = Modifier
            .padding(start = 8.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .border(
                shape = RoundedCornerShape(10.dp),
                width = 1.dp,
                color = Black292929
            )
            .clickable { onClicked(category) }
    ) {

        if (isSelected) {
            Text(
                modifier = Modifier
                    .background(Color.White)
                    .align(Alignment.Center)
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    ),
                text = category,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        } else {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    ),
                text = category,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun HeaderSection(
    modifier: Modifier = Modifier,
    title: String,
    actionButtonText: String,
    onActionClicked: () -> Unit = {}
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            color = Color.White,
            style = TextStyle(
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Box(modifier = Modifier
            .clickable { }
            .border(
                shape = RoundedCornerShape(16.dp),
                width = 1.dp,
                color = Black292929
            )
            .background(
                shape = RoundedCornerShape(16.dp),
                color = Black1A1A1A
            )
            .padding(horizontal = 6.dp)
        ) {

            Text(
                text = actionButtonText,
                color = Color.White,
                fontSize = 12.sp
            )
        }
    }
}

@Preview
@Composable
private fun HomeHeaderPreview() {
    HeaderSection(
        title = "Quick picks",
        actionButtonText = "More"
    )
}

@Preview
@Composable
private fun CategorySectionPreview() {
    CategoryItem(
        category = "Pop",
        isSelected = false
    )
}
