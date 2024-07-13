package com.example.teraroon

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userLogin = findViewById<EditText>(R.id.user_login_auth)
        val userPass = findViewById<EditText>(R.id.user_pass_auth)
        val button = findViewById<Button>(R.id.button_log)
        val linkToReg = findViewById<Button>(R.id.Switch_button)

        linkToReg.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

        button.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val pass = userPass.text.toString().trim()

            if (login == "" || pass == "")
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_LONG).show()
            else {
                val db = DbHelper(this, null)
                val isUserSign = db.getUser(login, pass)
                if (isUserSign) {
                    Toast.makeText(this, "Ты вошел в свой аккаунт, богатырь!", Toast.LENGTH_LONG).show()
                    userLogin.text.clear()
                    userPass.text.clear()

                    val intent = Intent(this, GameActivity::class.java)
                    intent.putExtra("login", login)
                    intent.putExtra("pass", pass)
                    startActivity(intent)
                }
                else {
                    Toast.makeText(this, "А ты ящер, еще не зарегистрировался", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}