package com.calyrsoft.ucbp1.features.movies.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items // Make sure this import is present
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
// Removed ui.semantics.error as it wasn't used and can sometimes be a typo for Material's error color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage // Updated import for Coil 3
import coil3.request.ImageRequest // Updated import for Coil 3
import com.calyrsoft.ucbp1.features.movies.domain.model.MovieModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MoviesScreen(
    vm: MoviesViewModel = koinViewModel(),
    navController: NavController,
    modifier: Modifier
) {
    val state by vm.state.collectAsState()

    // Fetch movies when the composable enters the composition
    LaunchedEffect(Unit) {
        vm.fetchMovies()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (val currentState = state) {
            is MoviesViewModel.MoviesStateUI.Init -> {
                Text("Initializing...")
            }
            is MoviesViewModel.MoviesStateUI.Loading -> {
                CircularProgressIndicator()
            }
            is MoviesViewModel.MoviesStateUI.Error -> {
                Text(
                    text = "Error: ${currentState.message}",
                    color = MaterialTheme.colorScheme.error, // Ensure this is from MaterialTheme
                    textAlign = TextAlign.Center
                )
            }
            is MoviesViewModel.MoviesStateUI.Success -> {
                if (currentState.movies.isEmpty()) {
                    Text(
                        text = "No movies found.",
                        textAlign = TextAlign.Center
                    )
                } else {
                    MovieList(movies = currentState.movies.toList()) // Convert Array to List
                }
            }
        }
    }
}

@Composable
fun MovieList(movies: List<MovieModel>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(
            items = movies,
            // SIMPLIFICATION: Use a String key derived from movie properties.
            // This avoids needing MovieModel to be Parcelable for the key.
            key = { movie -> movie.title + "::" + movie.pathUrl } // Concatenate properties for a unique String key
        ) { movie ->
            MovieItem(movie = movie)
        }
    }
}

@Composable
fun MovieItem(movie: MovieModel, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            // Ensure you have a base URL if movie.pathUrl is only a partial path
            val imageBaseUrl = "https://image.tmdb.org/t/p/w342" // Example base URL
            val imageUrl = if (movie.pathUrl.startsWith("http")) {
                movie.pathUrl // It's already a full URL
            } else {
                imageBaseUrl + movie.pathUrl // Prepend base URL
            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .build(),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 150.dp, height = 225.dp) // Fixed size for consistency
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                maxLines = 2,
                minLines = 2 // Helps maintain consistent item height
            )
        }
    }
}

