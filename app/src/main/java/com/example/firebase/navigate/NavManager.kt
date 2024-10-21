package com.example.firebase.navigate

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebase.ViewModel.LoginViewModel
import com.example.firebase.ViewModel.NoteViewModel
import com.example.firebase.view.Login.BlackView
import com.example.firebase.view.Login.TabsView
import com.example.firebase.view.Notas.HomeView

@Composable
fun NavManager(loginViewModel: LoginViewModel,noteViewModel: NoteViewModel){
    val navController = rememberNavController()
    NavHost(navController= navController, startDestination = "login"){
        composable("black"){
            BlackView(navController = navController)
        }
        composable("login"){
            TabsView(navController = navController, loginViewModel = loginViewModel)
        }

        composable("home"){
            HomeView(navController = navController, viewModel = noteViewModel)
        }
    }
}