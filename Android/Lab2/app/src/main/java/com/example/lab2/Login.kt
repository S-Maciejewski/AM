package com.example.lab2

import android.os.Bundle
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        DBService(applicationContext)
    }

    fun login(view: View) {
        if (loginField.text!!.isNotEmpty() and passwordField.text!!.isNotEmpty()) {
            DBService.service.loginOrRegisterUser(loginField.text.toString(), passwordField.text.toString())

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this@Login, "Wprowadź login i hasło", Toast.LENGTH_SHORT).show()
        }



    }
}