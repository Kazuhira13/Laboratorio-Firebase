package com.example.firebase.ViewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebase.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel:ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    var showAlert by mutableStateOf(false)


    fun login(email:String,password:String,onSucces: ()-> Unit){
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(){task ->
                        if(task.isSuccessful){
                            onSucces()
                        }else{
                            Log.e("LoginViedmodel","login failed",task.exception)
                            showAlert = true
                        }
                    }
            }catch (e:Exception){
                Log.e("LoginViedmodel","login failed",e)
                showAlert = true
            }
        }
    }
    fun register(email:String,password:String,userName: String,onSucces: ()-> Unit){
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(){task ->
                        if(task.isSuccessful){
                            saverUser(userName)
                            onSucces()
                        }else{
                            Log.e("LoginViedmodel","register failed",task.exception)
                            showAlert = true
                        }
                    }
            }catch (e:Exception){
                Log.e("LoginViedmodel","register failed",e)
                showAlert = true
            }
        }
    }

    private fun saverUser(userName: String) {
        val id = auth.currentUser?.uid
        val email  = auth.currentUser?.email

        viewModelScope.launch (Dispatchers.IO){
            val user = User(userId = id.toString(),email= email.toString(),name = userName)
            FirebaseFirestore.getInstance().collection("user")
                .add(user.toMap())
                .addOnCompleteListener(){task ->
                    if (task.isSuccessful){
                        Log.d("login","usersave")
                    }else{
                        Log.e("asda","asd",task.exception)
                    }
                }
        }
    }

    fun closeAlert(){
        showAlert = false
    }
}