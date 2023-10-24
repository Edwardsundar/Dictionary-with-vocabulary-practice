package com.demo.englishdectionary.presentation

import android.content.Context
import android.icu.text.CaseMap.Title
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.provider.Settings.Global.getString
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material.TopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.demo.englishdectionary.R
import com.demo.englishdectionary.data.remoat.dto.Meaning
import com.demo.englishdectionary.ui.theme.LightGray
import com.demo.englishdectionary.ui.theme.Red
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun OnlineSearchScreen(
    viewModel: DictionaryViewModel = hiltViewModel()
){
    val state = viewModel.uiState.collectAsState()

    Scaffold(
        scaffoldState = rememberScaffoldState(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.search),
                        style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
                    )
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                    if (state.value.isLiked){
                        state.value.wordMeaning?.word?.let {word->
                            viewModel.onEvent(UiEvent.DeleteWord(word))
                        }
                    }
                    else{

                        state.value.wordMeaning?.let { wordMeaning ->
                            viewModel.onEvent(UiEvent.BookMark(wordMeaning = wordMeaning ))
                        }
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
            ) {
                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(R.drawable.ic_like),
                    contentDescription = null,
                    tint = if (state.value.isLiked) Red else Color.White
                )
            }
        }
    ) {paddingValues->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            item {
                SearchBar(
                    wantKeyboard = state.value.wordMeaning == null
                ) {query->
                    viewModel.onEvent(UiEvent.SearchWord(query))
                }
                if (state.value.isLoading){
                    CircularProgressIndicator()
                }
                else if(state.value.errorMessage.isNotBlank() ){
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        Text(text = state.value.errorMessage)
                    }
                }
                else if (
                    state.value.exclamation.isNotEmpty() ||
                    state.value.verb.isNotEmpty() ||
                    state.value.noun.isNotEmpty() ||
                    state.value.interjection.isNotEmpty()
                ){
                    WordAndVoiceWigets(
                        title = state.value.wordMeaning?.word ?: "",
                        body = state.value.wordMeaning?.phonetic ?: "",
                        audioUrl = state.value.wordMeaning?.phonetics?.getOrNull(0)?.audio
                    )
                    if (state.value.exclamation.isNotEmpty()){
                        WordMeaningWigets(
                            partOfSpeech = stringResource(R.string.exalamation),
                            meanings = state.value.exclamation
                        )
                    }
                    if (state.value.noun.isNotEmpty()){
                        WordMeaningWigets(
                            partOfSpeech = stringResource(R.string.nonu),
                            meanings = state.value.noun
                        )
                    }
                     if (state.value.verb.isNotEmpty()){
                        WordMeaningWigets(
                            partOfSpeech = stringResource(R.string.verb),
                            meanings = state.value.verb
                        )
                    }
                    if (state.value.interjection.isNotEmpty()){
                        WordMeaningWigets(
                            partOfSpeech = stringResource(R.string.interjection),
                            meanings = state.value.interjection
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WordMeaningWigets(
    partOfSpeech : String,
    meanings : List<Meaning>
){
    Spacer(modifier = Modifier.height(10.dp))
    Text(text = partOfSpeech , fontWeight = FontWeight.ExtraBold
        , fontSize = 25.sp )
    Spacer(modifier = Modifier.height(10.dp))

    Card(
        shape = RoundedCornerShape(24.dp),
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        backgroundColor = LightGray
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ){
            meanings.forEach { meaning ->
                meaning.definitions.forEach { definition ->
                    if (definition.definition?.isNotBlank() == true) {
                        MeaningsWigets(
                            title = stringResource(R.string.definition),
                            body = definition.definition
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                    if (definition.example?.isNotBlank() == true) {
                        MeaningsWigets(
                            title = stringResource(R.string.example),
                            body = definition.example
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                    if (definition.synonyms?.isEmpty() == false) {
                        MeaningsWigets(
                            title = stringResource(R.string.synonyms),
                            body = definition.synonyms.joinToString(",")
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                    if (definition.antonyms?.isEmpty() == false) {
                        MeaningsWigets(
                            title = stringResource(R.string.antonyms),
                            body = definition.antonyms.joinToString(",")
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 100.dp , end = 100.dp , top = 8.dp , bottom = 8.dp),
                        color = Color.Black,
                        thickness = 1.dp,
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    wantKeyboard : Boolean ,
    onSearch :(String) -> Unit
){
    var query by remember {
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(wantKeyboard){focusRequester.requestFocus()}
    OutlinedTextField(
        value = query,
        onValueChange = {query = it},
        label = { Text(stringResource(R.string.search_the_word)) },
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .focusRequester(focusRequester),
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .padding(end = 5.dp)
                    .clickable {
                        keyboardController?.hide()
                        onSearch(query)
                    }
                    .focusRequester(focusRequester)
            )
        },
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                onSearch(query)
            }
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun MeaningsWigets(
    title: String,
    body : String=""
){
    Card(
        shape = RoundedCornerShape(24.dp),
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)

        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.body2,
                fontSize = 17.sp
                )
            Box(
                modifier = Modifier
                    .padding(start = 8.dp , end = 8.dp , top = 8.dp , bottom = 1.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(LightGray)
            ){
                Text(
                    text = body,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@Composable
fun WordAndVoiceWigets(
    title: String,
    body : String,
    audioUrl : String?
){
    val context = LocalContext.current
    Card(
        shape = RoundedCornerShape(24.dp),
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Row(
                Modifier.fillMaxWidth().padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.body1,
                    )
                Icon(
                    painterResource(R.drawable.ic_audio),
                    null,
                    modifier = Modifier
                        .clickable {
                            CoroutineScope(Dispatchers.IO).launch {
                                playAudio( context ,audioUrl)
                            }
                        }
                )
            }
            Box(
                modifier = Modifier
                    .padding(start = 8.dp , end = 8.dp , top = 8.dp , bottom = 1.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = LightGray)
            ){
                Text(
                    text = body,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

suspend fun playAudio( context: Context, url: String? = null) {
    val mediaPlayer = MediaPlayer()
    mediaPlayer.setAudioAttributes(AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).build())

    try {
        mediaPlayer.setDataSource(
            if (url == null || url.isBlank()) context.getString(R.string.no_audio)
            else url
        )
        mediaPlayer.prepare()
        mediaPlayer.start()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}