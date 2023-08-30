package com.example.moviex.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moviex.R
import com.example.moviex.database.MyApplication
import com.example.moviex.ui.theme.Primary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_logo),
                        contentDescription = "Logo",
                        modifier = Modifier.width(150.dp),
                    )
                }
            }, colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Black)
        )
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 10.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_person),
                contentDescription = null,
                tint = Primary,
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.CenterStart)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 100.dp)
            ) {
                userLogin?.let {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black
                    )
                }
                userLogin?.let {
                    Text(
                        text = it.email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        // About Us
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "About Us",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
//            MenuItem(icon = Icons.Default.MailOutline, text = "Contact Us")
//            MenuItem(icon = Icons.Default.Description, text = "Terms")
            val context = LocalContext.current
            val userDao = (context.applicationContext as MyApplication).database.UserDao()
            Row(

                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                    CoroutineScope(Dispatchers.IO).launch {
                        val user = userDao.getUserByLogin(true)
                        user?.let { nonNullUser ->
                            withContext(Dispatchers.Main) {
                                userDao.updateUser(nonNullUser.id, false)
                                navController.navigate("signIn") {
                                    popUpTo("signIn") { inclusive = true }
                                }
                                userLogin = null
                                Toast.makeText(context, "You are logout now", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Logout",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(28.dp)
                        .padding(end = 8.dp)
                )
                Text(
                    text = "Logout", style = MaterialTheme.typography.bodyLarge, color = Color.Black
                )
            }
        }
    }
}

@Composable
fun MenuItem(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(bottom = 8.dp)
            .clickable {
            }) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = Primary,
            modifier = Modifier
                .size(28.dp)
                .padding(end = 8.dp)
        )
        Text(
            text = text, style = MaterialTheme.typography.bodyLarge, color = Color.Black
        )
    }
}


@Preview
@Composable
fun PreviewProfileScreen() {
    val navController = rememberNavController()
    ProfileScreen(navController = navController)
}

