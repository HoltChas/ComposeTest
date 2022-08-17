package com.constellation.goddess.composetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.constellation.goddess.composetest.ui.theme.ComposeTestTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestTheme {
                // A surface container using the 'background' color from the theme
                Column {
//                    LayoutsCodelab()
                    ImageList()
                }

            }
        }
    }
}

@Composable
fun PhotographerCard(modifier: Modifier = Modifier) {
    Row(modifier = modifier
        .padding(8.dp)
        .clip(RoundedCornerShape(4.dp))
        .background(Color.Cyan)
        .clickable { }
        .padding(16.dp)) {
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(0.2f)
        ) {

        }

        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(text = "Aifred Sisley", fontWeight = FontWeight.Bold)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text("3 min ago", style = MaterialTheme.typography.body2)
            }
        }
    }

}

//@Preview(showBackground = true)
//@Composable
//fun PhotographerCardPreview() {
//    ComposeTestTheme {
//        PhotographerCard()
//    }
//}

@Composable
fun LayoutsCodelab() {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "LayoutsCodelab") },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.Favorite, contentDescription = null)
                }
            })
    }) {
        BodyContent(
            Modifier
                .padding(it)
                .padding(8.dp)
        )
    }

}


@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = "hi here")
        Text(text = "Thanks for going through the Layouts codelab")
    }
}

@Preview(showBackground = true)
@Composable
fun LayoutCodeloadPreview() {
    ComposeTestTheme {
//        LayoutsCodelab()
//        ImageList()
    }
}

@Composable
fun ImageListItem(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = rememberImagePainter(data = "https://developer.android.com/images/brand/Android_Robot.png"),
            contentDescription = "android logo",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Item #$index",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
fun ImageList() {
    val listSize = 100

    val scrollState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    Column {
        Row {
            Button(
                onClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollToItem(0)
                    }
                }, modifier = Modifier
                    .weight(1f, true)
                    .padding(2.dp)
            ) {
                Text(text = "Scroll to the top")
            }

            Button(
                onClick = { coroutineScope.launch { scrollState.animateScrollToItem(listSize - 1) } },
                modifier = Modifier
                    .weight(1f, true)
                    .padding(2.dp)
            ) {
                Text(text = "Scroll to the end")
            }
        }

        LazyColumn(state = scrollState, modifier = Modifier.padding(top = 10.dp)) {
            items(100) {
                ImageListItem(index = it)
            }
        }
    }

}

fun Modifier.firstBaselineToTop(firstBaselineToTop: Dp) = this.then(
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]


        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        val height = placeable.height + placeableY
        layout(placeable.width, height) {

        }
    }

)
