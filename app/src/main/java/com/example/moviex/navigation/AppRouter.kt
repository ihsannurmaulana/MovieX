package com.example.moviex.navigation

import DetailScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moviex.BottomBarScreen
import com.example.moviex.screens.FavoriteScreen
import com.example.moviex.screens.HomeScreen
import com.example.moviex.screens.PreLoginScreen
import com.example.moviex.screens.ProfileScreen
import com.example.moviex.screens.SignInScreen
import com.example.moviex.screens.SignUpScreen

@Composable
fun AppRouter(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "preLogin"){

        composable("preLogin"){
            PreLoginScreen(navController)
        }
        composable("signIn"){
            SignInScreen(navController)
        }
        composable("signUp"){
            SignUpScreen(navController)
        }
        composable("home"){
            HomeScreen(navController)
        }
        composable("favorite"){
            FavoriteScreen(navController)
        }
        composable("profile"){
            ProfileScreen(navController)
        }
        composable("detail/{movieId}"){ backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")
            movieId?.let{
                DetailScreen(navController = navController, itemId = movieId)
            }
        }
    }
}
