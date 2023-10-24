package com.demo.englishdectionary.presentation.mainscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.demo.englishdectionary.R
import com.demo.englishdectionary.common.Navigation
import com.demo.englishdectionary.presentation.UiEvent
import com.demo.englishdectionary.presentation.mainscreen.MainScreenViewModel
import com.demo.englishdectionary.ui.theme.Gold


@Composable
fun BookMarkItem(
    word : String ,
    meaning : String ,
    modifier : Modifier = Modifier,
    viewModel : MainScreenViewModel ,
    navController : NavController
){

    Card(
        shape = RoundedCornerShape(24.dp),
        elevation = 8.dp,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .clickable {
                    navController.navigate(Navigation.OnlineSearch.route+ "/${word}")
                }
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Text(
                word,
                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Divider(
                color = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
            Text(
                meaning,
                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                color = Color.Gray
            )
            IconButton(
                onClick = {
                          viewModel.onEvent(MainScreenEvent.DeleteBookMark(word))
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_star),
                    contentDescription = null,
                    tint = Gold
                )
            }
        }
    }
}