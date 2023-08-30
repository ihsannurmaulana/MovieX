package com.example.moviex.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moviex.R
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

data class SignUpInput(
    val name: String,
    val email: String,
    val password: String
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController) {
    val context = LocalContext.current
    val signUpInput = remember {
        mutableStateOf(SignUpInput("", "", ""))
    }
    val scrollState = rememberScrollState()
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
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            HeadTextComponent(value = stringResource(id = R.string.create_account))
            Spacer(modifier = Modifier.height(40.dp))
            Image(
                painter = painterResource(id = R.drawable.img_logo),
                contentDescription = "",
                modifier = Modifier
                    .height(50.dp)
                    .width(200.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            // Full Name
            OutlinedTextField(
                value = signUpInput.value.name,
                onValueChange = {newValue ->
                    signUpInput.value = signUpInput.value.copy(name = newValue)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(componentShapes.small),
                label = { Text("Full Name") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFFE80751),
                    focusedLabelColor = Color(0xFFE80751),
                    cursorColor = Primary,
                    containerColor = BgColor
                ),
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.ic_person), contentDescription = "") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                singleLine = true,
                maxLines = 1,
            )

            Spacer(modifier = Modifier.height(10.dp))
            // Email
            OutlinedTextField(
                value = signUpInput.value.email,
                onValueChange = {newValue ->
                    signUpInput.value = signUpInput.value.copy(email = newValue)
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
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.ic_email), contentDescription = "") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                singleLine = true,
                maxLines = 1,
            )
            Spacer(modifier = Modifier.height(10.dp))

            // PasswordFieldComponent for Password
            val passwordVisible = remember {
                mutableStateOf(false)
            }
            OutlinedTextField(
                value = signUpInput.value.password,
                onValueChange = {newValue ->
                    signUpInput.value = signUpInput.value.copy(password = newValue)
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
                leadingIcon = { Icon(painter = painterResource(id = R.drawable.ic_lock), contentDescription = "") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
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
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(48.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFE80751)),
                onClick = {
                    val userDao = (context.applicationContext as MyApplication).database.UserDao()
                    val input = signUpInput.value

                    val email = signUpInput.value.email
                    val password = signUpInput.value.password

                    if (!email.contains("@")) {
                        Toast.makeText(context, "Invalid email address", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val passwordPattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%?&])[A-Za-z\\d@$!%?&]{8,}\$")
                    if (!password.matches(passwordPattern)) {
                        Toast.makeText(context, "Password must be 8+ chars, with at least 1 uppercase, 1 lowercase, 1 digit, and 1 special character.", Toast.LENGTH_LONG).show()
                        return@Button
                    }

                    if (input.name.isNotEmpty() &&
                        input.email.isNotEmpty() &&
                        input.password.isNotEmpty()){

                        val entity = User(name = input.name, email = input.email, password = input.password)
                        CoroutineScope(Dispatchers.IO).launch {
                            userDao.insert(entity)
                        }
                        navController.navigate("signIn")
                        Toast.makeText(context, "Register Successfully", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(context, "Fields must not be empty", Toast.LENGTH_SHORT).show()
                    }

                },
                contentPadding = PaddingValues(),
                colors = ButtonDefaults.buttonColors(Color.Transparent),
            ) {
                Text(
                    text = "Register",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            DividerTextComponent()

            Spacer(modifier = Modifier.height(20.dp))

            ClickableLoginTextComponent(
                onTextSelected = {
                    navController.navigate("signIn")
                })
        }
    }
}

@Preview
@Composable
fun PreviewSignUpScreen() {
    val navController = rememberNavController()
    SignUpScreen(navController = navController)
}
