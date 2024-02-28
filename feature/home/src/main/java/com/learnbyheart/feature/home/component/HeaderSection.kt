package com.learnbyheart.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnbyheart.core.common.ui.Black1A1A1A
import com.learnbyheart.core.common.ui.Black292929

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
    title: String,
    actionButtonText: String,
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
                fontSize = 21.sp,
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
            .padding(
                vertical = 4.dp,
                horizontal = 8.dp
            )
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
fun HomeHeaderPreview() {
    HomeHeader(
        title = "Quick picks",
        actionButtonText = "More"
    )
}
