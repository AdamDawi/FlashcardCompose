package com.adamdawi.flashcardcompose

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adamdawi.flashcardcompose.ui.theme.Blue
import com.adamdawi.flashcardcompose.ui.theme.DarkBlue
import com.adamdawi.flashcardcompose.ui.theme.FlashCardComposeTheme
import com.adamdawi.flashcardcompose.ui.theme.Green
import com.adamdawi.flashcardcompose.ui.theme.Red

private val listOfFlashCards = mapOf(
    "hello" to "hola",
    "not much" to "no mucho",
    "fine" to "bien",
    "one" to "uno",
    "two" to "dos",
    "three" to "tres",
    "bathroom" to "El Baño",
    "kitchen" to "La cocina"
)

@Composable
fun MainScreen() {
    val redCounter = remember {
        mutableIntStateOf(0)
    }

    val greenCounter = remember {
        mutableIntStateOf(0)
    }

    val greenCounterBg = remember {
        mutableStateOf(Color.Transparent)
    }

    val animatedGreenCounterBg = animateColorAsState(
        targetValue = greenCounterBg.value,
        label = ""
    )

    val redCounterBg = remember {
        mutableStateOf(Color.Transparent)
    }

    val animatedRedCounterBg = animateColorAsState(
        targetValue = redCounterBg.value,
        label = ""
    )

    val currentFlashCardNumber = remember {
        mutableIntStateOf(0)
    }
    val animatedAlphaRedCounter = animateFloatAsState(
        targetValue = if (redCounterBg.value == Red) 1f else 0f,
        label = ""
    )

    val animatedAlphaGreenCounter = animateFloatAsState(
        targetValue = if (greenCounterBg.value == Green) 1f else 0f,
        label = ""
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = DarkBlue
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TopButtons(
                currentQuestionNumber = currentFlashCardNumber.intValue
            )
            HorizontalDivider(thickness = 3.dp, color = Blue)
            CountersRow(
                redCounter = redCounter.intValue,
                greenCounter = greenCounter.intValue,
                greenCounterBg = animatedGreenCounterBg.value,
                redCounterBg = animatedRedCounterBg.value,
                redCounterAlpha = animatedAlphaRedCounter.value,
                greenCounterAlpha = animatedAlphaGreenCounter.value
            )
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                FlashCard(
                    frontText = listOfFlashCards.keys.elementAt(if(currentFlashCardNumber.intValue<listOfFlashCards.keys.size) currentFlashCardNumber.intValue else 0),
                    backText = listOfFlashCards.values.elementAt(if(currentFlashCardNumber.intValue<listOfFlashCards.keys.size) currentFlashCardNumber.intValue else 0),
                    onLeftSwipe = {
                        redCounter.intValue++
                        currentFlashCardNumber.intValue++
                        greenCounterBg.value = Color.Transparent
                        redCounterBg.value = Color.Transparent
                    },
                    onRightSwipe = {
                        greenCounter.intValue++
                        currentFlashCardNumber.intValue++
                        greenCounterBg.value = Color.Transparent
                        redCounterBg.value = Color.Transparent
                    },
                    onCloseToLeft = {
                        redCounterBg.value = Red
                    },
                    onCloseToRight = {
                        greenCounterBg.value = Green
                    },
                    onNeutral = {
                        greenCounterBg.value = Color.Transparent
                        redCounterBg.value = Color.Transparent
                    }
                )
                BottomButtons()
            }
        }
    }
}

@Composable
private fun TopButtons(
    modifier: Modifier = Modifier,
    currentQuestionNumber: Int
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {}
        ) {
            Icon(
                modifier = Modifier
                    .size(26.dp),
                imageVector = Icons.Default.Clear,
                contentDescription = null,
                tint = Color.White
            )
        }
        Text(
            text = "${currentQuestionNumber+1}/88",
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        IconButton(
            onClick = {}
        ) {
            Icon(
                modifier = Modifier
                    .size(26.dp),
                imageVector = Icons.Default.Settings,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Composable
private fun CountersRow(
    modifier: Modifier = Modifier,
    redCounter: Int,
    greenCounter: Int,
    greenCounterBg: Color,
    redCounterBg: Color,
    redCounterAlpha: Float,
    greenCounterAlpha: Float
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .width(60.dp)
                .height(38.dp)
                .border(
                    width = 1.dp,
                    color = Red,
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 100.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 100.dp
                    )
                )
                .clip(RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 100.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 100.dp
                ))
                .background(redCounterBg),
            contentAlignment = Alignment.Center
        ) {
            Box {
                Text(
                    modifier = Modifier
                        .alpha(alpha = redCounterAlpha),
                    text = "+1",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier
                        .alpha(alpha = 1f - redCounterAlpha),
                    text = redCounter.toString(),
                    color = Red,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Box(
            modifier = Modifier
                .width(60.dp)
                .height(38.dp)
                .border(
                    width = 1.dp,
                    color = Green,
                    RoundedCornerShape(
                        topStart = 100.dp,
                        topEnd = 0.dp,
                        bottomStart = 100.dp,
                        bottomEnd = 0.dp
                    )
                )
                .clip(
                    RoundedCornerShape(
                        topStart = 100.dp,
                        topEnd = 0.dp,
                        bottomStart = 100.dp,
                        bottomEnd = 0.dp
                    )
                )
                .background(greenCounterBg),
            contentAlignment = Alignment.Center
        ) {
            Box{
                Text(
                    modifier = Modifier
                        .alpha(alpha = greenCounterAlpha),
                    text = "+1",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier
                        .alpha(alpha = 1f - greenCounterAlpha),
                    text = greenCounter.toString(),
                    color = Green,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}

@Composable
private fun BottomButtons(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {}
        ) {
            Icon(
                modifier = Modifier
                    .size(26.dp),
                painter = painterResource(R.drawable.return_ic),
                contentDescription = null,
                tint = Color.White
            )
        }
        IconButton(
            onClick = {}
        ) {
            Icon(
                modifier = Modifier
                    .size(26.dp),
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}


@Preview
@Composable
private fun MainScreenPreview() {
    FlashCardComposeTheme {
        MainScreen()
    }
}