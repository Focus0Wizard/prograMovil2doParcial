package com.calyrsoft.ucbp1.features.movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.calyrsoft.ucbp1.features.movies.domain.model.MovieModel
import com.calyrsoft.ucbp1.features.movies.domain.usecase.FetchMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(
    val usecase: FetchMoviesUseCase

): ViewModel(){
    sealed class MoviesStateUI {
        object Init: MoviesStateUI()
        object Loading: MoviesStateUI()
        class Error(val message: String): MoviesStateUI()
        class Success(val movies: Array<MovieModel>): MoviesStateUI()

    }
    private val _state = MutableStateFlow<MoviesStateUI>(MoviesStateUI.Init)
    val state : StateFlow<MoviesStateUI> = _state.asStateFlow()
    fun fetchMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = MoviesStateUI.Loading
            val result = usecase.invoke()
            result.fold(
                onSuccess = {
                        movies -> _state.value = MoviesStateUI.Success( movies )
                },
                onFailure = {
                        error -> _state.value = MoviesStateUI.Error(message = error.message ?: "Error desconocido")
                }
            )
        }
    }
}