package com.example.progettobiblioteca

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


class MyApplication : Application() {

 lateinit var db: FirebaseFirestore

 override fun onCreate() {
  super.onCreate()
  // Inizializza Firestore
  db = Firebase.firestore
 }

 companion object {
  private lateinit var instance: MyApplication

  fun getInstance(): MyApplication {
   return instance
  }
 }

 init {
  instance = this
 }


}

