package com.example.projeto03_comsumo_de_uma_api_pro.ui.views


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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.projeto03_comsumo_de_uma_api_pro.R
import com.example.projeto03_comsumo_de_uma_api_pro.ui.viewmodels.CharactersViewModel

@Composable
fun CharacterDetails(viewModel: CharactersViewModel, navController: NavController, characterId: Int) {
    val character = viewModel.getCharacterById(characterId)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)

    ) {
        Image(
            painter = painterResource(id = R.drawable.fundo_rick_and_mory),
            contentDescription = "Fundo Preto",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.2f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            character?.let {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).data(it.img).build(),
                        contentDescription = it.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                    Spacer(modifier = Modifier.padding(50.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Nome: ${it.name}",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 35.sp, color = Color.White),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Status: ${it.status}",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 35.sp, color = Color.White),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Espécie: ${it.species}",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 35.sp, color = Color.White),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Tipo: ${it.type}",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 35.sp, color = Color.White),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Gênero: ${it.gender}",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 35.sp, color = Color.White),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } ?: run {
                Text(
                    text = "Character not found",
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
                )
            }
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.costas),
                    contentDescription = "Voltar",
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}