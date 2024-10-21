package com.example.firebase.model

import androidx.compose.ui.input.pointer.PointerEventPass

data class User(
    val email: String,
    val userId : String,
    val name : String
){
    fun toMap(): MutableMap<String,Any>{
        return mutableMapOf(
            "email" to email,
            "userId" to userId,
            "name" to name
        )
    }
}
