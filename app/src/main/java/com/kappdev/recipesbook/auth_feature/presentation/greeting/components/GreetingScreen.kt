package com.kappdev.recipesbook.auth_feature.presentation.greeting.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kappdev.recipesbook.R
import com.kappdev.recipesbook.core.presentation.navigation.Screen
import com.kappdev.recipesbook.ui.theme.CookieFontFamily

@Composable
fun GreetingScreen(
    navController: NavHostController
) {
    Box {
        Image(
            painter = painterResource(R.drawable.greeting_background),
            contentDescription = stringResource(R.string.greeting_background_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        GreetingText(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SignUpButton {
                navController.navigate(Screen.SignUp.route)
            }
            LoginButton {
                navController.navigate(Screen.Login.route)
            }
        }
    }
}

@Composable
private fun GreetingText(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.greeting),
        fontSize = 64.sp,
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface,
        fontFamily = CookieFontFamily
    )
}

@Composable
private fun SignUpButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = ButtonModifier
    ) {
        Text(
            text = stringResource(R.string.sign_up),
            fontSize = 18.sp
        )
    }
}

@Composable
private fun LoginButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary
        ),
        modifier = ButtonModifier
    ) {
        Text(
            text = stringResource(R.string.log_in),
            fontSize = 18.sp
        )
    }
}

private val ButtonModifier = Modifier.height(50.dp).width(280.dp)