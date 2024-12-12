package com.example.ezloan

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ezloan.Data.MainViewModel

data class AboutUs(
    val name : String,
    val description : String,
    val image : Int
)

val aboutUsList = listOf(
    AboutUs("Pradeep Chaurasiya", "GCET CSDS (B)", R.drawable.ic_launcher_foreground),
    AboutUs("Pranjal Tiwari", "GCET CSDS (B)", R.drawable.ic_launcher_foreground),
    AboutUs("Vivek Chaurasiya", "GCET CSDS (B)", R.drawable.ic_launcher_foreground),
    AboutUs("Vivek Dhakrey", "GCET CSDS (B)", R.drawable.ic_launcher_foreground)
)

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun AboutUsScreen(
    navController: NavHostController,
) {

    Surface(
        color = BOTTOM_COLOR,
        modifier = Modifier
            .fillMaxSize()
    ) {
        AnimatedVisibility(
            visible = navController.context.isUiContext,
            enter = expandVertically(
                animationSpec = tween(1000),
                expandFrom = Alignment.Top
            ),
            exit = shrinkVertically(
                animationSpec = tween(1000),
                shrinkTowards = Alignment.Top
            )
        ) {

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(start = 16.dp, end = 16.dp, bottom = 32.dp, top = 110.dp)
            ) {
                AboutUsCard(0)
                AboutUsCard(1)
                AboutUsCard(2)
                AboutUsCard(3)
            }
        }
    }
}

@Composable
private fun AboutUsCard(index: Int) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CARD_COLOR),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .padding(PaddingValues(vertical = 6.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Left,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(PaddingValues(8.dp))
                .background(Color.Transparent)
        ) {
            Card(
                shape = CircleShape,
                modifier = Modifier
                    //.padding(PaddingValues(8.dp))
                    .size(50.dp)
                    .background(Color.Transparent)
            ) {
                Image(
                    painter = painterResource(aboutUsList[index].image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(PaddingValues(start = 12.dp))
            ) {
                Text(
                    text = aboutUsList[index].name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                    letterSpacing = 0.sp,
                    maxLines = 1,
                    color = Color.White,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = aboutUsList[index].description,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    letterSpacing = 0.sp,
                    maxLines = 1,
                    color = Color.LightGray,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}