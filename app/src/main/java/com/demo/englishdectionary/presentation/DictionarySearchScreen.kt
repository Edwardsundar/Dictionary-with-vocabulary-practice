package com.demo.englishdectionary.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DictionarySearchScreen(
    viewModel: DictionaryViewModel = hiltViewModel()
){
    val state = viewModel.uiState.collectAsState()
    var searchQuery by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
            },
            modifier = Modifier.fillMaxWidth(),
        )
        Button(
            onClick = {
                viewModel.onEvent(UiEvent.SearchWord(searchQuery))
            }
        ){
            Text(text = "click")
        }
        state.value.wordMeaning?.let { wordMeaning->
            LazyColumn {
                item{
                    Text(text = wordMeaning.word ?: "", modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally))
                    Spacer(modifier = Modifier.height(20.dp))
                    wordMeaning.meanings!!.forEach {meaning->
                        Text(text = meaning.partOfSpeech, modifier = Modifier.fillMaxWidth())
                        meaning.definitions.forEach {definition->
                                Text(text = definition.definition, modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            }
        }
        if (state.value.isLoading){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        if (state.value.errorMessage.isNotBlank()){
            Box(modifier = Modifier.fillMaxSize() , contentAlignment = Alignment.Center){
                Text(text = state.value.errorMessage)
            }
        }
    }

}