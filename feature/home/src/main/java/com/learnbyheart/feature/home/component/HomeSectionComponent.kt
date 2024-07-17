package com.learnbyheart.feature.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import com.learnbyheart.core.model.MusicDisplayData
import com.learnbyheart.core.ui.Grey898989

@Composable
internal fun HorizontalTypeItem(
    item: MusicDisplayData,
    onItemClick: (String) -> Unit = {}
) {

    val constrainSet = ConstraintSet {
        val image = createRefFor("image")
        val song = createRefFor("song")
        val artist = createRefFor("artist")
        val action = createRefFor("action")

        constrain(image) { start.linkTo(parent.start) }

        constrain(song) {
            bottom.linkTo(artist.top)
            start.linkTo(image.end, 16.dp)
            end.linkTo(action.start)
            width = Dimension.fillToConstraints
        }

        constrain(artist) {
            top.linkTo(song.bottom, 8.dp)
            start.linkTo(image.end, 16.dp)
            end.linkTo(action.start)
            width = Dimension.fillToConstraints
        }

        constrain(action) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
        }

        createVerticalChain(
            song,
            artist,
            chainStyle = ChainStyle.Packed
        )
    }

    ConstraintLayout(
        modifier = Modifier
            .wrapContentHeight()
            .width(380.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onItemClick(item.id) }
            .padding(8.dp),
        constraintSet = constrainSet
    ) {

        AsyncImage(
            modifier = Modifier
                .size(60.dp)
                .clip(shape = RoundedCornerShape(4.dp))
                .layoutId("image"),
            model = item.image,
            contentScale = ContentScale.Crop,
            contentDescription = item.name,
            placeholder = painterResource(id = com.learnbyheart.core.ui.R.drawable.img_music_thumbnail)
        )

        Text(
            modifier = Modifier
                .layoutId("song"),
            textAlign = TextAlign.Start,
            text = item.name,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            modifier = Modifier
                .layoutId("artist"),
            text = item.owner,
            color = Grey898989,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        IconButton(
            modifier = Modifier
                .layoutId("action"),
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

@Composable
internal fun VerticalTypeItem(
    item: MusicDisplayData,
) {

    Column(
        modifier = Modifier
            .padding(start = 16.dp)
            .height(250.dp)
            .width(160.dp)
            .clip(RoundedCornerShape(4.dp))
            .clickable { }
    ) {

        AsyncImage(
            modifier = Modifier
                .size(160.dp)
                .clip(RoundedCornerShape(4.dp)),
            model = item.image,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = com.learnbyheart.core.ui.R.drawable.img_music_thumbnail)
        )

        Text(
            modifier = Modifier.padding(top = 8.dp),
            textAlign = TextAlign.Start,
            text = item.name,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        if (item.owner.isNotEmpty()) {
            Text(
                text = item.owner,
                color = Grey898989,
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
private fun HorizontalTypeItemPreview() {
    HorizontalTypeItem(
        item = MusicDisplayData(
            id = "",
            name = "Hot summer",
            image = "https://i.scdn.co/image/ab67706f000000022e63cecea090de728740ae76",
            owner = "Calvin Harris"
        )
    )
}

@Preview
@Composable
private fun VerticalTypeItemPreview() {
    VerticalTypeItem(
        item = MusicDisplayData(
            id = "",
            name = "Hot\nsummer",
            image = "https://i.scdn.co/image/ab67706f000000022e63cecea090de728740ae76",
            owner = "Calvin\nHarris"
        )
    )
}
