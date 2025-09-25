package com.calyrsoft.ucbp1.features.movies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calyrsoft.ucbp1.features.movies.domain.model.Movie
import com.calyrsoft.ucbp1.features.movies.domain.usecase.GetPopularMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MoviesUiState>(MoviesUiState.Loading)
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            _uiState.value = MoviesUiState.Loading
            val result = getPopularMoviesUseCase()
            result.fold(
                onSuccess = { movies ->
                    _uiState.value = MoviesUiState.Success(movies)
                },
                onFailure = { exception ->
                    _uiState.value = MoviesUiState.Error(exception.message ?: "Error desconocido")
                }
            )
        }
    }

    sealed interface MoviesUiState {
        data object Loading : MoviesUiState
        data class Success(val movies: List<Movie>) : MoviesUiState
        data class Error(val message: String) : MoviesUiState
    }
}