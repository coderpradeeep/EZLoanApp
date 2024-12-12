package com.example.ezloan.ChatBot

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.ezloan.BOTTOM_COLOR
import com.example.ezloan.BUTTON_COLOR
import com.example.ezloan.BuildConfig
import com.example.ezloan.Data.MainViewModel
import com.example.ezloan.TOP_COLOR
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

lateinit var generativeModel : GenerativeModel

@Composable
fun ChatScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val chatList by viewModel.chatList.observeAsState()
    LaunchedEffect(Unit) {
        viewModel.isChatScreen = true

        generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = BuildConfig.APIKEY,
        )
        if(viewModel.firstChat) {
            viewModel.chatAI(generativeModel)
        }
    }

    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        TOP_COLOR,
                        BOTTOM_COLOR
                    ),
                    startY = 0f,
                    endY = 1200f
                )
            )
    ) {

        // Chats
        chatList?.let {
            LazyColumn(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Bottom,
                state = LazyListState(
                    firstVisibleItemIndex = it.size,
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .padding(start = 16.dp, end = 16.dp, top = 50.dp, bottom = 100.dp)
                    .navigationBarsPadding()
                    .statusBarsPadding()
            ) {
                // Demo chat
                items(it, key = {it.id}) {item ->
                    if (item.isUser) {
                        ChatUserShape(item.text)
                    } else {
                        ChatAIShape(item.text)
                    }
                }
            }
        }

        // TextField and Search Button on top of chats
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .navigationBarsPadding()
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            ) {
                Spacer(
                    Modifier
                        .weight(1f)
                        .background(Color.Transparent)
                )

                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                    modifier = Modifier
                        .background(Color.Transparent)
                        .padding(PaddingValues(vertical = 12.dp, horizontal = 16.dp))
                ) {
                    OutlinedTextField(
                        enabled = viewModel.isUser,
                        shape = CircleShape,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = null,
                                tint = Color.LightGray
                            )
                        },
                        label = {
                            Text(
                                text = "Ask a Question"
                            )
                        },
                        value = viewModel.enteredText,
                        onValueChange = {
                            viewModel.enteredText = it
                        },
                        maxLines = 2,
                        textStyle = TextStyle(Color.White),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Text
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = null
                        ),
                        modifier = Modifier
                            .background(Color.Transparent)
                            .weight(1f)
                    )

                    Button(
                        shape = CircleShape,
                        enabled = if(viewModel.enteredText != "") {
                                        true
                                    } else false,
                        onClick = {
                            viewModel.viewModelScope.launch {
                                viewModel.addChat(viewModel.enteredText)
                                viewModel.prompt = viewModel.enteredText
                                viewModel.chatAI(generativeModel)
                                viewModel.enteredText = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BUTTON_COLOR,
                            disabledContainerColor = BUTTON_COLOR
                        ),
                        elevation = ButtonDefaults.buttonElevation(4.dp),
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .width(IntrinsicSize.Min)
                            .padding(PaddingValues(start = 16.dp))
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Send,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .scale(1.2f)
                                .padding(PaddingValues(10.dp))
                        )
                    }

                }
            }
        }

    }
}

@Composable
private fun ChatUserShape(
    text: String = "",
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.Right,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .animateContentSize()
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(Color.Gray),
            modifier = Modifier
                .padding(start = 60.dp, top = 8.dp, bottom = 8.dp)
                .wrapContentHeight()
                .wrapContentWidth()
                .background(Color.Transparent)
        ) {
            Text(
                text = text,
                color = Color.Black,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.sp,
                lineHeight = 20.sp,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(PaddingValues(horizontal = 12.dp, vertical = 6.dp))
                    .align(Alignment.End)
            )
        }
    }
}

@Composable
private fun ChatAIShape(
    text: String = "",
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.Left,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(Color.DarkGray),
            modifier = Modifier
                .padding(end = 60.dp, top = 6.dp, bottom = 6.dp)
                .wrapContentHeight()
                .wrapContentWidth()
                .background(Color.Transparent)
        ) {
            Text(
                text = text,
                color = Color.White,
                fontWeight = FontWeight.Normal,
                letterSpacing = 0.sp,
                lineHeight = 20.sp,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(PaddingValues(horizontal = 12.dp, vertical = 6.dp))
                    .align(Alignment.Start)
            )
        }
    }
}