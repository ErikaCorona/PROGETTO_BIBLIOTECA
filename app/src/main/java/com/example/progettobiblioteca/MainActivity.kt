package com.example.progettobiblioteca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var userId:EditText
    private lateinit var password:EditText
    private lateinit var btnLogin:Button
    private lateinit var btnRegistrati:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnLogin=findViewById(R.id.buttonLogin)
        btnRegistrati=findViewById(R.id.buttonRegistrati)
        userId=findViewById(R.id.userId)
        password=findViewById(R.id.pass)
        btnLogin.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}