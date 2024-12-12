package com.example.ezloan

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ezloan.Data.MainViewModel
import com.example.ezloan.NavGraph.NavGraph
import com.example.ezloan.ui.theme.EZLoanTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    lateinit var viewModel : MainViewModel
    lateinit var navController : NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]


        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            EZLoanTheme {
                navController = rememberNavController()

                EZLoan(
                    navController,
                    viewModel
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EZLoan(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Absolute.Right,
                        modifier = Modifier
                            .padding(end = 16.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.TopEnd,
                            modifier = Modifier
                                .wrapContentSize()
                        ) {
                            IconButton(
                                onClick = {
                                    isExpanded = true
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .scale(1.2f)
                                )
                            }
                            DropdownMenu(
                                expanded = isExpanded,
                                onDismissRequest = { isExpanded = false },
                                modifier = Modifier
                                    .wrapContentSize()
                                    .background(CARD_COLOR)
                            ) {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = "Clear Chat",
                                            color = Color.LightGray,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W500,
                                            maxLines = 1,
                                            textAlign = TextAlign.End,
                                            modifier = Modifier
                                                .padding(PaddingValues(16.dp))
                                        )
                                    },
                                    onClick = {
                                        isExpanded = false
                                        viewModel.viewModelScope.launch {
                                            viewModel.deleteChat()
                                        }
                                    }
                                )
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = "About Us",
                                            color = Color.LightGray,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.W500,
                                            maxLines = 1,
                                            textAlign = TextAlign.End,
                                            modifier = Modifier
                                                .padding(PaddingValues(16.dp))
                                        )
                                    },
                                    onClick = {
                                        isExpanded = false
                                        navController.navigate(route = "AboutUs")
                                    }
                                )
                            }
                        }
                    }
                },
                scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(),
                title = {
                    Text(
                        text = "EZ Loan",
                        color = Color.Black,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.sp,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
                modifier = Modifier
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
            )
        },

        // Content
        content = {
            NavGraph(navController, viewModel)
            if (!viewModel.isChatScreen) {
                NavButtons(navController, viewModel)
            }

            var snackBarState = viewModel.snackBarState.observeAsState()

            AnimatedVisibility(
                visible = if(snackBarState.value != 0) true else false,
                enter = fadeIn(
                    animationSpec = tween(200, easing = FastOutSlowInEasing)
                ),
                exit = fadeOut(
                    animationSpec = tween(200, easing = LinearEasing)
                )
            ) {
                 if (snackBarState.value == 1) {
                    SnackBar(navController, "Server not found")
                    LaunchedEffect(Unit) {
                        delay(1000)
                        navController.popBackStack()
                        delay(1000)
                        viewModel.snackBarState.value = 0
                    }
                }
            }

        }

    )
}