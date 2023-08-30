package com.example.moviex.screens

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moviex.BottomNavBar
import com.example.moviex.api.Movie
import com.example.moviex.api.MovieResponse
import com.example.moviex.api.authorizationHeader
import com.example.moviex.api.movieApiService
import com.example.moviex.components.BottomScreen
import com.example.moviex.components.MiddleScreen
import com.example.moviex.components.TopScreen
import com.example.moviex.components.UpComingScreen
import com.example.moviex.ui.theme.MovieXTheme
import kotlinx.coroutines.launch

enum class BottomBar {
    Home,
    Favorite,
    Profile,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    var nowPlayingMovieList by remember {
        mutableStateOf(emptyList<Movie>())
    }
    var popularMovieList by remember {
        mutableStateOf(emptyList<Movie>())
    }
    var topRatedMovieList by remember {
        mutableStateOf(emptyList<Movie>())
    }
    var upComingMovieList by remember {
        mutableStateOf(emptyList<Movie>())
    }
    LaunchedEffect(key1 = true) {
        scope.launch {
            try {
                val responseNowPlaying: MovieResponse = movieApiService.getNowPlayingMovies(
                    authorizationHeader
                )
                nowPlayingMovieList = responseNowPlaying.results ?: emptyList()

                val responsePopularMovie: MovieResponse = movieApiService.getPopularMovies(
                    authorizationHeader
                )
                popularMovieList = responsePopularMovie.results ?: emptyList()

                val responseTopRated: MovieResponse = movieApiService.getTopRatedMovies(
                    authorizationHeader
                )
                topRatedMovieList = responseTopRated.results ?: emptyList()
                val responseUpComing: MovieResponse = movieApiService.getUpComingMovies(
                    authorizationHeader
                )
                upComingMovieList = responseUpComing.results ?: emptyList()
            } catch (e: Exception) {
                Log.e("MovieDbScreen", "Error fetching movies: ${e.message}", e)
            }
        }
    }

    var selectedTab by remember { mutableStateOf(BottomBar.Home) }
    if (nowPlayingMovieList.isEmpty() && popularMovieList.isEmpty() && topRatedMovieList.isEmpty() && upComingMovieList.isEmpty()) {
        Text(
            text = "No Internet Connection",
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
    } else {
        Scaffold(
            bottomBar = {
                BottomNavBar(
                    selectedTab = selectedTab,
                    onTabSelected = { tab ->
                        selectedTab = tab
                    }
                )
            }
        ) { innerPadding ->
            when (selectedTab) {
                BottomBar.Home -> {
                    LazyColumn {
                        item {
                            TopScreen(popularMovieList, navController)
                        }
                        item {
                            MiddleScreen(topRatedMovieList, navController)
                        }
                        item {
                            BottomScreen(nowPlayingMovieList, navController)
                        }
                        item {
                            UpComingScreen(upComingMovieList, navController)
                        }
                    }
                }

                BottomBar.Favorite -> {
                    FavoriteScreen(navController = navController)
                }

                BottomBar.Profile -> {
                    ProfileScreen(navController = navController)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    MovieXTheme {
        HomeScreen(navController = rememberNavController())
    }
}
