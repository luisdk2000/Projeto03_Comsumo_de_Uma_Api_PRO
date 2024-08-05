package com.example.projeto03_comsumo_de_uma_api_pro.ui.views

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.projeto03_comsumo_de_uma_api_pro.R
import com.example.projeto03_comsumo_de_uma_api_pro.ui.viewmodels.CharactersViewModel
import com.example.projeto03_comsumo_de_uma_api_pro.data.Character

@Composable
fun CharactersListScreen(
    charactersViewModel: CharactersViewModel = viewModel(),
    navController: NavController
) {
    val uiState by charactersViewModel.uiState.collectAsState()

    when (uiState) {
        is CharactersViewModel.CharactersUiState.Loading -> LoadingScreen()
        is CharactersViewModel.CharactersUiState.Success -> {
            val characters = (uiState as CharactersViewModel.CharactersUiState.Success).characters
            LazyVerticalGrid(
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxSize(), columns = GridCells.Fixed(2)
            ) {
                items(characters) { character ->
                    CharacterEntry(
                        character = character,
                        navController = navController
                    )
                }
            }
        }

        is CharactersViewModel.CharactersUiState.Error -> ErrorScreen()
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.loading),
            contentDescription = "",
            modifier = Modifier.size(150.dp)
        )
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.falha),
                contentDescription = ""
            )
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(id = R.string.error)
            )
        }
    }
}

@Composable
fun CharacterEntry(character: Character, navController: NavController) {
    val density = LocalDensity.current.density
    val width = remember { mutableFloatStateOf(0F) }
    val height = remember { mutableFloatStateOf(0F) }

    Card(modifier = Modifier
        .padding(6.dp)
        .clickable {
            navController.navigate("character_details/${character.id}")
        }, elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box {
            Log.d("ImageLoad", "Loading image from: ${BASE_URL + character.img}")
            AsyncImage(model = ImageRequest.Builder(LocalContext.current).data(character.img)
                .build(),
                placeholder = painterResource(R.drawable.ponto_interroga__o),
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RectangleShape)
                    .onGloballyPositioned {
                        width.floatValue = it.size.width / density
                        height.floatValue = it.size.height / density
                    })
            Box(
                modifier = Modifier
                    .size(width = width.floatValue.dp, height = height.floatValue.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color.Black),
                            100F,
                            500F,
                        )
                    )
            )
            Text(
                text = character.name,
                modifier = Modifier.align(Alignment.BottomCenter),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}