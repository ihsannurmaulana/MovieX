package com.example.moviex

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState


/*
//@Composable
//fun NavigationBottomView(
//    items: List<NavigationItems>,
//    modifier: Modifier = Modifier,
//    navController: NavController,
//    onItemClick: (NavigationItems) -> Unit
//) {
//    val backStackEntry = navController.currentBackStackEntryAsState()
//    BottomNavigation(
//        modifier = modifier,
//        backgroundColor = Color.Gray, // Pembenaran pengejaan
//        elevation = 5.dp.toPx() // Konversi ke nilai Dp
//    ) {
//        // Tambahkan item-item ke dalam BottomNavigation sesuai dengan kebutuhan
//    }
//}

@Composable
fun Home() {
    Box(modifier = Modifier
        .fillMaxSize(), contentAlignment = Alignment.Center){
        Text(text = "Home Screen")
    }
}

@Composable
fun Favorite() {
    Box(modifier = Modifier
        .fillMaxSize(), contentAlignment = Alignment.Center){
        Text(text = "Favorite Screen")
    }
}

@Composable
fun Profile() {
    Box(modifier = Modifier
        .fillMaxSize(), contentAlignment = Alignment.Center){
        Text(text = "Profile Screen")
    }
}*/


