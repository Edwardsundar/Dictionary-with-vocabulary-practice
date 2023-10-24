package com.demo.englishdectionary.presentation.mainscreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.demo.englishdectionary.R
import com.demo.englishdectionary.common.Navigation

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainScreenViewModel = hiltViewModel()
){
    val uiState = viewModel.state.collectAsState()

    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize(),

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
                    )
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp,
            )
        }
    ){paddingValues ->
        if (uiState.value.isUploading){
            val animateProgress by animateFloatAsState(
                targetValue = viewModel.uploadProgress,
                animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec, label = ""
            )
            Column(
                modifier = Modifier.fillMaxSize().background(Color.Transparent.copy(0.5f)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                LinearProgressIndicator(
                    progress = animateProgress
                )
            }
        }
        if (uiState.value.history.isEmpty()){
            NoBookmarksMessage()
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .clip(RoundedCornerShape(18.dp))
            ){
                items(uiState.value.history){ wordMeaning ->
                    BookMarkItem(
                        word = wordMeaning.word ?: "" ,
                        meaning = wordMeaning.meanings?.
                        getOrNull(0)?.definitions?.getOrNull(0)?.definition ?: "",
                        viewModel = viewModel,
                        navController = navController
                    )
                }
            }
            // First FAB on the left
            ExtendedFloatingActionButton(
                text = { /* Text for the FAB */ },
                icon = { Icon(
                    painterResource(R.drawable.ic_letters),
                    contentDescription = null)
                       },
                onClick = {
                          navController.navigate(Navigation.PracticeScreen.route)
                },
                modifier = Modifier
                    .padding(start = 32.dp , end = 32.dp , bottom = 64.dp)
                    .align(Alignment.BottomStart) ,
                backgroundColor =  MaterialTheme.colors.secondary
            )

            // Second FAB on the right
            ExtendedFloatingActionButton(
                text = { /* Text for the FAB */ },
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = null ,
                        ) },
                onClick = {
                          navController.navigate(Navigation.OnlineSearch.route+"/ ")
                },
                modifier = Modifier
                    .padding(start = 32.dp , end = 32.dp , bottom = 64.dp)
                    .align(Alignment.BottomEnd),
                backgroundColor =  MaterialTheme.colors.primary
            )
        }
    }

}
@Composable
fun NoBookmarksMessage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.no_bookmarks_message),
            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Image(
            modifier = Modifier.size(125.dp),
            painter = painterResource(id = R.drawable.bookmarks_img),
            contentDescription = stringResource(R.string.no_bookmarks_message),
            alpha = 0.7f
        )
    }
}