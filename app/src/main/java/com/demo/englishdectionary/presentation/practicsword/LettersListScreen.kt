package com.demo.englishdectionary.presentation.practicsword

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.demo.englishdectionary.R
import com.demo.englishdectionary.common.Letter
import com.demo.englishdectionary.common.Navigation
import kotlin.random.Random

data class ListItem(
    val height : Dp,
    val color : Color
)

@Composable
fun LetterListScreen(
    navController: NavController
){
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize(),

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.vocabulary_start_with),
                        style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
                    )
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp,
            )
        }
    ){paddingValue->
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(120.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalItemSpacing = 8.dp,
        ){
            items(Letter.values()){letter->
                RandomColorBlock(
                    item = ListItem(
                        height = Random.nextInt(150,200).dp,
                        color = Color(Random.nextLong(0xFFFFFFFF)).copy(alpha = 1f)
                    ),
                    letter = letter.name
                ){
                    navController.navigate(Navigation.SelectedLetterScreen.route + "/${letter.name}")
                }
            }
        }
    }
}

@Composable
fun RandomColorBlock(
    item : ListItem,
    letter : String,
    onItemClick : ()->Unit
){

     Card(
        shape = RoundedCornerShape(24.dp),
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(item.height)
            .padding(8.dp)
    ) {
         Box(
             modifier = Modifier
                 .fillMaxWidth()
                 .height(item.height)
                 .background(item.color)
                 .clickable {
                            onItemClick()
                 },
             contentAlignment = Alignment.Center
         ){
             Text(
                 text = letter,
                 fontWeight = FontWeight.ExtraBold,
                 fontStyle = FontStyle.Italic,
                 fontSize = 100.sp
             )
         }
     }

}
