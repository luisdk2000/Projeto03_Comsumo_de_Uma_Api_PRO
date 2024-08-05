package com.example.projeto03_comsumo_de_uma_api_pro.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projeto03_comsumo_de_uma_api_pro.R
import com.example.projeto03_comsumo_de_uma_api_pro.ui.viewmodels.CharactersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterApp(modifier: Modifier = Modifier) {
    val viewModel: CharactersViewModel = viewModel()
    val navController = rememberNavController()
    val uiState by viewModel.appUiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = stringResource(id = uiState.title))
                        Spacer(modifier = Modifier.width(20.dp))
                        Image(
                            painter = painterResource(id = R.drawable.rick_logo),
                            contentDescription = "Logo da Série",
                            modifier = Modifier.size(100.dp)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = AppScreens.CharacterListScreen.name,
            modifier = modifier.padding(paddingValues)
        ) {
            composable(route = AppScreens.CharacterListScreen.name) {
                CharactersListScreen(charactersViewModel = viewModel, navController = navController)
            }
            composable(route = "character_details/{characterId}") { backStackEntry ->
                val characterId = backStackEntry.arguments?.getString("characterId")?.toInt() ?: 0
                CharacterDetails(
                    viewModel = viewModel,
                    navController = navController,
                    characterId = characterId
                )
            }
        }
    }
}

enum class AppScreens { CharacterListScreen }