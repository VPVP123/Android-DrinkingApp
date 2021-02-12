package com.github.skosvall.nextlvl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()


        val buttonLogin = this.findViewById<Button>(R.id.login)

        buttonLogin.setOnClickListener{

            val email = findViewById<EditText>(R.id.email) as EditText
            val password = findViewById<EditText>(R.id.password) as EditText






            if(!email.text.toString().isEmpty() || !password.text.toString().isEmpty()){
                auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            startActivity(
                                Intent(this, adminPanelActivity::class.java)
                            )
                        } else {
                            Toast.makeText(baseContext, "Login failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Toast.makeText(baseContext, "One or more field is empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
        override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            startActivity(
                Intent(this, adminPanelActivity::class.java)
            )
        }
    }


}