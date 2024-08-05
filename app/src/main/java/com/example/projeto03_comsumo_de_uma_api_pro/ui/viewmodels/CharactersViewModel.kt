package com.example.projeto03_comsumo_de_uma_api_pro.ui.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.projeto03_comsumo_de_uma_api_pro.ui.views.AppUiState
import com.example.projeto03_comsumo_de_uma_api_pro.ui.views.CharacterDetailsUiState
import com.example.projeto03_comsumo_de_uma_api_pro.ui.views.RickAndMortyApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import com.example.projeto03_comsumo_de_uma_api_pro.data.Character
class CharactersViewModel : ViewModel() {

    private var _uiState: MutableStateFlow<CharactersUiState> =
        MutableStateFlow(CharactersUiState.Loading)
    val uiState: StateFlow<CharactersUiState> = _uiState.asStateFlow()

    private var charactersList: List<Character> = emptyList() // Lista para armazenar os personagens

    private val _appUiState: MutableStateFlow<AppUiState> = MutableStateFlow(AppUiState())
    val appUiState: StateFlow<AppUiState> = _appUiState.asStateFlow()
    private val _characterDetailsUiState: MutableStateFlow<CharacterDetailsUiState> =
        MutableStateFlow(CharacterDetailsUiState())

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            try {
                val response = RickAndMortyApi.retrofitService.getCharacters()
                charactersList = response.results // Armazenando a lista de personagens
                _uiState.value = CharactersUiState.Success(charactersList)
            } catch (e: IOException) {
                _uiState.value = CharactersUiState.Error
            } catch (e: HttpException) {
                _uiState.value = CharactersUiState.Error
            }
        }
    }

    fun getCharacterById(id: Int): Character? {
        return charactersList.find { it.id == id }
    }

    sealed interface CharactersUiState {
        data object Loading : CharactersUiState
        data class Success(val characters: List<Character>) : CharactersUiState
        data object Error : CharactersUiState
    }
}