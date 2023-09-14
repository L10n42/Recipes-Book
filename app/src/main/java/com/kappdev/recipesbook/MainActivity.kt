package com.kappdev.recipesbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.kappdev.recipesbook.core.presentation.navigation.Screen
import com.kappdev.recipesbook.core.presentation.navigation.SetupNavGraph
import com.kappdev.recipesbook.ui.theme.RecipesBookTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipesBookTheme(darkTheme = false) {
                val navController = rememberNavController()

                SetupNavGraph(
                    navController = navController,
                    startScreen = Screen.Greeting
                )
            }
        }
    }
}