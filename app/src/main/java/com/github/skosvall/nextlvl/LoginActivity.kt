package com.github.skosvall.nextlvl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val buttonLogin = this.findViewById<Button>(R.id.login)

        buttonLogin.setOnClickListener{
            startActivity(
                Intent(this, adminPanelActivity::class.java)
            )
        }
    }
}