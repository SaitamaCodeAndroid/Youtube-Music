package com.learnbyheart.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.learnbyheart.core.common.ui.Grey898989
import com.learnbyheart.core.data.model.HomeDisplayData

@Composable
fun LoadingProgress() {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator(

        )
    }
}

@Composable
fun TrackItem(
    track: HomeDisplayData,
) {

    ConstraintLayout(
        modifier = Modifier
            .wrapContentHeight()
            .width(380.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { }
            .padding(4.dp)
    ) {
        val (image, song, artist, action) = createRefs()

        AsyncImage(
            modifier = Modifier
                .size(100.dp)
                .clip(shape = RoundedCornerShape(4.dp))
                .constrainAs(image) { start.linkTo(parent.start) },
            model = track.image,
            contentScale = ContentScale.Crop,
            contentDescription = track.name,
        )

        /*ConstraintSet {

            createVerticalChain(song, artist, chainStyle = ChainStyle.SpreadInside)

        }*/

        Text(
            modifier = Modifier
                .constrainAs(song) {
                    bottom.linkTo(artist.top)
                    start.linkTo(image.end, 16.dp)
                    end.linkTo(action.start, 16.dp)
                    width = Dimension.fillToConstraints
                }
                .fillMaxWidth(0.7f),
            textAlign = TextAlign.Start,
            text = track.name,
            color = Color.White,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            modifier = Modifier
                .constrainAs(artist) {
                    top.linkTo(song.bottom)
                    start.linkTo(image.end, 16.dp)
                }
                .fillMaxWidth(0.7f),
            text = track.artists,
            color = Grey898989,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        IconButton(
            modifier = Modifier
                .constrainAs(action) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
            content = {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "",
                    tint = Color.White
                )
            },
            onClick = { /*TODO*/ }
        )
    }
}

@Preview
@Composable
fun TrackItemPreview() {
    TrackItem(
        track = HomeDisplayData(
            id = "",
            name = "Hot summer",
            image = "",
            artists = "Calvin Harris"
        )
    )
}
