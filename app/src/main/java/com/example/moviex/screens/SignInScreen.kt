package com.example.moviex.screens

import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moviex.R
import com.example.moviex.components.ButtonComponent
import com.example.moviex.components.ClickableLoginTextComponent
import com.example.moviex.components.DividerTextComponent
import com.example.moviex.components.HeadTextComponent
import com.example.moviex.components.componentShapes
import com.example.moviex.database.MyApplication
import com.example.moviex.database.User
import com.example.moviex.ui.theme.BgColor
import com.example.moviex.ui.theme.Primary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class SignInInput(
    val email: String, val password: String
)

var userLogin: User? = null

@Composable
fun PreLoginScreen(navController: NavController) {
    val context = LocalContext.current
    val userDao = (context.applicationContext as MyApplication).database.UserDao()

    LaunchedEffect(key1 = true) {
        val user = withContext(Dispatchers.IO) {
            userDao.getUserByLogin(true)
        }

        if (user != null) {
            userLogin = user
            navController.navigate("home")
        } else {
            navController.navigate("signIn")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(navController: NavController) {
    val context = LocalContext.current
    val signInInput = remember {
        mutableStateOf(SignInInput("", ""))
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            HeadTextComponent(value = stringResource(id = R.string.welcome))
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.img_logo),
                contentDescription = "",
                modifier = Modifier
                    .height(50.dp)
                    .width(200.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            // Email
            OutlinedTextField(
                value = signInInput.value.email,
                onValueChange = { newValue ->
                    signInInput.value = signInInput.value.copy(email = newValue)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(componentShapes.small),
                label = { Text("Email") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFFE80751),
                    focusedLabelColor = Color(0xFFE80751),
                    cursorColor = Primary,
                    containerColor = BgColor
                ),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_email), contentDescription = ""
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next, keyboardType = KeyboardType.Email
                ),
                singleLine = true,
                maxLines = 1,
            )
            Spacer(modifier = Modifier.height(10.dp))

            // PasswordFieldComponent for Password
            val passwordVisible = remember {
                mutableStateOf(false)
            }
            OutlinedTextField(value = signInInput.value.password,
                onValueChange = { newValue ->
                    signInInput.value = signInInput.value.copy(password = newValue)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(componentShapes.small),
                label = { Text("Password") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFFE80751),
                    focusedLabelColor = Color(0xFFE80751),
                    cursorColor = Primary,
                    containerColor = BgColor
                ),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lock), contentDescription = ""
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done, keyboardType = KeyboardType.Password
                ),
                singleLine = true,
                maxLines = 1,
                trailingIcon = {
                    val iconImage = if (passwordVisible.value) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }

                    val description = if (passwordVisible.value) {
                        stringResource(id = R.string.hide_password)
                    } else {
                        stringResource(id = R.string.show_password)
                    }

                    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                        Icon(imageVector = iconImage, contentDescription = description)
                    }
                },
                visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.heightIn(min = 20.dp))
            ButtonComponent(
                value = "Login", onButtonClicked = {
                    val email = signInInput.value.email
                    val password = signInInput.value.password

                    val userDao = (context.applicationContext as MyApplication).database.UserDao()

                    CoroutineScope(Dispatchers.IO).launch {
                        val user = userDao.getUserByEmail(email)
                        withContext(Dispatchers.Main) {
                            if (user != null && user.password == password) {
                                userDao.updateUser(user.id, true)
                                navController.navigate("preLogin")
                                Toast.makeText(context, "Login success", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(
                                    context, "Email or password Is not valid", Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                }, buttonColor = Color(0xFFE80751)
            )
            Spacer(modifier = Modifier.height(20.dp))

            DividerTextComponent()

            Spacer(modifier = Modifier.height(20.dp))

            ClickableLoginTextComponent(tryingToLogin = false, onTextSelected = {
                navController.navigate("signUp")
            })
        }
    }
}

@Preview
@Composable
fun PreviewSignInScreen() {
    val navController = rememberNavController()
    SignInScreen(navController = navController)
}

