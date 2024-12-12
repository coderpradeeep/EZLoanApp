package com.example.ezloan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.ezloan.Data.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NavButtons(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            Spacer(
                Modifier.weight(1f)
            )

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 32.dp)
            ) {

                if(viewModel.isResultScreen || viewModel.isPredictScreen) {
                    Button(
                        onClick = {
                            viewModel.viewModelScope.launch {
                                if (viewModel.isPredictScreen) {
                                    viewModel.isPredictScreen = false
                                }
                                if (viewModel.isResultScreen) {
                                    viewModel.isResultScreen = false
                                }
                                if(viewModel.isChatScreen) {
                                    viewModel.isChatScreen = false
                                }
                            }
                            navController.popBackStack()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BUTTON_COLOR
                        ),
                        elevation = ButtonDefaults.buttonElevation(4.dp),
                        modifier = Modifier
                            .width(IntrinsicSize.Min)
                            .weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier
                                .scale(1.5f)
                                .padding(6.dp)
                        )
                    }
                    Spacer(Modifier.padding(horizontal = 12.dp))

                    OutlinedButton(
                        onClick = {
                            navController.navigate(route = "Login")
                            viewModel.page = 0
                            viewModel.isResultScreen = false
                            viewModel.isPredictScreen = false
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = BOTTOM_COLOR,
                        ),
                        elevation = ButtonDefaults.buttonElevation(4.dp),
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .width(IntrinsicSize.Min)
                            .weight(1f)
                    ) {
                        Text(
                            text = "Go Home",
                            fontSize = 14.sp,
                            letterSpacing = 0.sp,
                            fontWeight = FontWeight.W500,
                            color = Color.LightGray,
                            modifier = Modifier
                                .padding(vertical = 6.dp)
                        )
                    }
                }
                else {
                    if (viewModel.page != 0) {
                        Button(
                            onClick = {
                                viewModel.viewModelScope.launch {
                                    delay(200)
                                    viewModel.page--
                                }
                                navController.popBackStack()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = BUTTON_COLOR
                            ),
                            elevation = ButtonDefaults.buttonElevation(4.dp),
                            modifier = Modifier
                                .width(IntrinsicSize.Min)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBack,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier
                                    .scale(1.5f)
                                    .padding(6.dp)
                            )
                        }
                        Spacer(Modifier.padding(horizontal = 12.dp))
                    }

                    Button(
                        onClick = {
                            if (viewModel.page == 12) {
                                navController.navigate(route = "Prediction_Route")

                            } else {
                                navController.navigate(route = "Login")
                                viewModel.viewModelScope.launch {
                                    delay(200)
                                    viewModel.page++
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BUTTON_COLOR
                        ),
                        elevation = ButtonDefaults.buttonElevation(4.dp),
                        modifier = Modifier
                            .width(IntrinsicSize.Min)
                            .weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowForward,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier
                                .scale(1.5f)
                                .padding(6.dp)
                        )
                    }

                }

            }
        }

    }

}