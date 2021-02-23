package com.github.skosvall.nextlvl

import android.app.AlertDialog
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
                            val popUpError1 = AlertDialog.Builder(this)
                            popUpError1.setTitle("Login failed")
                            popUpError1.setMessage("The email and/or password you entered is incorrect")
                            popUpError1.setPositiveButton( "Ok") { dialog, which ->
                                dialog.dismiss()
                            }
                            popUpError1.show()
                        }
                    }
            }else{
                val popUpError1 = AlertDialog.Builder(this)
                popUpError1.setTitle("Enter all fields")
                popUpError1.setMessage("The email and password fields cannot be empty")
                popUpError1.setPositiveButton( "Ok") { dialog, which ->
                    dialog.dismiss()
                }
                popUpError1.show()
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