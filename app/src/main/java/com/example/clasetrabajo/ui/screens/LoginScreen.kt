package com.example.clasetrabajo.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.clasetrabajo.data.model.UserModel
import com.example.clasetrabajo.data.viewmodel.UserViewModel

@Composable
fun LoginScreen(navController: NavHostController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ){
       LoginForm(navController)
    }
}
@Composable
fun LoginForm(navController: NavController, ViewModel: UserViewModel = viewModel()){
    //for Toast
    val context = LocalContext.current
    Card(
        colors = CardDefaults.cardColors(
            contentColor = Color.White,
            containerColor = MaterialTheme.colorScheme.tertiary
        ),
        modifier = Modifier
            .padding(40.dp, 0.dp)
    ){
        Column(
            modifier = Modifier
                    //to avoid the column elements to overlap in the card border
                .padding(20.dp)

        ) {
            var user by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            AsyncImage(
                model = "https://logos-world.net/wp-content/uploads/2020/04/Adidas-Logo-1971-present.png",
                contentDescription = "Adidas logo",
                contentScale = ContentScale.Fit,
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = user,
                maxLines = 1,
                onValueChange = {user = it},
                label = {Text("User")},
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = Color.Transparent,
                    unfocusedTextColor = Color.Black,
                    focusedTextColor = Color.White,
                    unfocusedLabelColor = Color.Black,
                    focusedLabelColor = Color.White
                )
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = password,
                maxLines = 1,
                onValueChange = {password = it},
                label = {Text("Password")},
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = Color.Transparent,
                    unfocusedTextColor = Color.Black,
                    focusedTextColor = Color.White,
                    unfocusedLabelColor = Color.Black,
                    focusedLabelColor = Color.White
                )
            )
            FilledTonalButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp),
                shape = CutCornerShape(4.dp),
                //need to send and receive user, password and context
                onClick = {TryLogin(user, password, context, ViewModel, navController)},
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.DarkGray,
                    containerColor = MaterialTheme.colorScheme.secondary
                )

            ) {
                Text("Log In")
            }
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp),
                shape = CutCornerShape(4.dp),
                onClick = {TryCreate(user, password, context, ViewModel)},
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Black,
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Create Account")
            }
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 10.dp),
                shape = CutCornerShape(4.dp),
                onClick = {TryCreate(user, password, context, ViewModel)},
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Black,
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Update Account")
            }
        }
    }
}
fun TryLogin(
    user:String,
    password:String,
    context: Context,
    viewModel: UserViewModel,
    navController: NavController
){
    if(user == "" || password == ""){
        //requires a context
        Toast.makeText(
            //will show a message only when both user and password are empty
            context,
            "User or password cannot be empty",
            Toast.LENGTH_SHORT //the message will show for a shorter timespan
        ).show()
    } else {
        val user_model = UserModel(0, user, password)
        viewModel.loginAPI(user_model){ jsonResponse ->
            val loginStatus = jsonResponse?.get("login")?.asString
            Log.d("debug", "LOGIN STATUS: $loginStatus")
            if(loginStatus == "success"){
                navController.navigate("accountsScreen")
            } else {
                Toast.makeText(
                    context,
                    "Failed login, check your credentials",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

fun TryCreate(
    user:String,
    password:String,
    context: Context,
    viewModel: UserViewModel
){
    if(user == "" || password == ""){
        //requires a context
        Toast.makeText(
            //will show a message only when both user and password are empty
            context,
            "User or password cannot be empty",
            Toast.LENGTH_SHORT //the message will show for a shorter timespan
        ).show()
    } else {
        val user_model = UserModel(0, user, password)
        viewModel.loginAPI(user_model){ jsonResponse ->
            val createStatus = jsonResponse?.get("store")?.asString
            Log.d("debug", "CREATE STATUS: $createStatus")
            if(createStatus == "success"){
                Toast.makeText(
                    context,
                    "Account created successfully\n" + "you can login now",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    context,
                    "Failed create",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}


