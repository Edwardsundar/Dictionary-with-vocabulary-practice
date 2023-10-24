package com.demo.englishdectionary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.demo.englishdectionary.common.Navigation
import com.demo.englishdectionary.presentation.mainscreen.MainScreen
import com.demo.englishdectionary.presentation.OnlineSearchScreen
import com.demo.englishdectionary.presentation.practicsword.LetterListScreen
import com.demo.englishdectionary.presentation.practicsword.SelectedLetterScreen
import com.demo.englishdectionary.ui.theme.EnglishDectionaryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //installSplashScreen()
        setContent {
            EnglishDectionaryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Navigation.MainScreen.route
                    ){
                        composable(route = Navigation.MainScreen.route){
                            MainScreen(navController)
                        }
                        composable(
                            route = Navigation.OnlineSearch.route + "/{word}"
                        ){
                            OnlineSearchScreen()
                        }
                        composable(route = Navigation.PracticeScreen.route){
                            LetterListScreen(navController = navController)
                        }
                        composable(
                            route = Navigation.SelectedLetterScreen.route + "/{letter}"
                        ){
                            SelectedLetterScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}