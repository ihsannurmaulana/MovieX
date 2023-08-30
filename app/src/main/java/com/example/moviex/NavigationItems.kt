package com.example.moviex

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.moviex.screens.BottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavBar(
    selectedTab: BottomBar,
    onTabSelected: (BottomBar) -> Unit
) {
    BottomAppBar(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .height(80.dp),
        containerColor = Color.Black.copy(alpha= 0.7f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BottomBarItem(
                icon = Icons.Default.Home,
                text = "Home",
                isSelected = selectedTab == BottomBar.Home
            ) {
                onTabSelected(BottomBar.Home)
            }
            BottomBarItem(
                icon = Icons.Default.Favorite,
                text = "Favorite",
                isSelected = selectedTab == BottomBar.Favorite
            ) {
                onTabSelected(BottomBar.Favorite)
            }
            BottomBarItem(
                icon = Icons.Default.Person,
                text = "Profile",
                isSelected = selectedTab == BottomBar.Profile
            ) {
                onTabSelected(BottomBar.Profile)
            }
        }
    }
}

@Composable
fun BottomBarItem(
    icon: ImageVector,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        androidx.compose.material3.Icon(
            imageVector = icon,
            contentDescription = text,
            tint = if (isSelected) Color.Red else Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            color = if (isSelected) Color.Red else Color.Gray,
            fontWeight = FontWeight.Bold
        )
    }
}
