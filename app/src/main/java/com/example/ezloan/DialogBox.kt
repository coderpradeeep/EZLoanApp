package com.example.ezloan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.ezloan.ChatBot.generativeModel
import com.example.ezloan.Data.MainViewModel
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DialogBox(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    var response = viewModel.prediction.observeAsState()
    LaunchedEffect(Unit) {
        viewModel.isUser = false
        viewModel.firstChat = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BOX_COLOR),
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(24.dp)
                .shadow(elevation = 24.dp, spotColor = Color.Red)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(PaddingValues(12.dp))
            ) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(PaddingValues(bottom = 16.dp))
                ) {
                    Text(
                        text = "Chat with AI",
                        fontSize = 24.sp,
                        letterSpacing = 0.sp,
                        maxLines = 1,
                        lineHeight = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.LightGray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                    )
                    Text(
                        text = if(response.value!! >= 0.8f) {
                            "Great ${viewModel.name}!\nFeel free to chat with our AI for further improving your profile."
                        }
                        else "Don't worry ${viewModel.name}\nImprove your profile using our AI Chatbot. Click the Chat button to get personalized suggestions.",
                        fontSize = 15.sp,
                        letterSpacing = 0.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.LightGray,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BOTTOM_COLOR
                        ),
                        onClick = {
                            navController.popBackStack()
                            viewModel.viewModelScope.launch {
                                viewModel.predicting.value = 0
                                delay(500)
                                viewModel.dialogBoxState.value = false
                            }
                        },
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Skip",
                            fontSize = 15.sp,
                            letterSpacing = 0.sp,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            color = TOP_COLOR,
                            modifier = Modifier
                                .padding(PaddingValues(2.dp))
                        )
                    }

                    Button(
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BOTTOM_COLOR
                        ),
                        onClick = {
                            navController.navigate(route = "Chat_Screen")
                            viewModel.viewModelScope.launch {
                                viewModel.predicting.value = 0
                                delay(500)
                                viewModel.dialogBoxState.value = false
                            }
                        },
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Chat",
                            fontSize = 15.sp,
                            letterSpacing = 0.sp,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            color = TOP_COLOR,
                            modifier = Modifier
                                .padding(PaddingValues(2.dp))
                        )
                    }
                }
            }
        }
    }
}