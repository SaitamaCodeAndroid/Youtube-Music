package com.learnbyheart.ytmusic.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learnbyheart.spotify.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicTopAppBar(
    onSearchClick: () -> Unit,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        title = { /*TODO*/ },
        navigationIcon = {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(30.dp),
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.app_icon_music),
                    contentDescription = "app logo",
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = stringResource(id = R.string.app_bar_title),
                    color = Color.White,
                    fontSize = 30.sp,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        },
        actions = {
            IconButton(
                onClick = onSearchClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cast),
                    contentDescription = "search",
                    tint = Color.Unspecified
                )
            }

            IconButton(
                onClick = onSearchClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "search",
                    tint = Color.Unspecified
                )
            }
        },
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    )
}

@Preview
@Composable
fun MusicTopAppBarPreview() {
    Surface(color = Color.Black) {
        MusicTopAppBar {}
    }
}
