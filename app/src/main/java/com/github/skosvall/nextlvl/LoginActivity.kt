package com.github.skosvall.nextlvl

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

const val RC_SIGN_IN = 1

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var listOfAdminAccounts: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        listOfAdminAccounts = mutableListOf()

        val accounts = db.collection("adminAccounts").document("accounts")
        val buttonLogin = this.findViewById<Button>(R.id.login_button)
        val googleLogin = this.findViewById<SignInButton>(R.id.google_sign_in_button)

        accounts.get()
            .addOnSuccessListener { fields ->
                if (fields != null) {
                    val myArray = fields.get("accounts") as List<String>?
                    if (myArray != null) {
                        for (item in myArray) {
                            listOfAdminAccounts.add(item)
                        }
                    }
                } else {
                    displayError()
                }
                googleLogin.setOnClickListener{
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .build()

                    // Build a GoogleSignInClient with the options specified by gso.
                    val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

                    val signInIntent = mGoogleSignInClient.signInIntent
                    startActivityForResult(signInIntent, RC_SIGN_IN)
                }
            }.addOnFailureListener {
                    displayError()
            }

        buttonLogin.setOnClickListener{
            val email = findViewById<EditText>(R.id.email)
            val password = findViewById<EditText>(R.id.password)

            if(email.text.toString().isNotEmpty() || password.text.toString().isNotEmpty()){
                auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            startActivity(
                                Intent(this, AdminPanelActivity::class.java)
                            )
                            finish()
                        } else {
                            val popUpError1 = androidx.appcompat.app.AlertDialog.Builder(this)
                            popUpError1.setTitle("Login failed")
                            popUpError1.setMessage("The email and/or password you entered is incorrect")
                            popUpError1.setPositiveButton( "Ok") { dialog, _ ->
                                dialog.dismiss()
                            }
                            popUpError1.show()
                        }
                    }
            }else{
                val popUpError1 = androidx.appcompat.app.AlertDialog.Builder(this)
                popUpError1.setTitle("Enter all fields")
                popUpError1.setMessage("The email and password fields cannot be empty")
                popUpError1.setPositiveButton( "Ok") { dialog, _ ->
                    dialog.dismiss()
                }
                popUpError1.show()
            }
        }

    }
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            startActivity(
                Intent(this, AdminPanelActivity::class.java)
            )
            finish()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val task = GoogleSignIn.getSignedInAccountFromIntent(data)

        try{
            val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
            if (account != null){
                val token = account.idToken
                auth.signInWithCredential(GoogleAuthProvider.getCredential(token, null))
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val currentUser = auth.currentUser

                            if(currentUser != null && listOfAdminAccounts.contains(currentUser.email!!)){
                                startActivity(
                                    Intent(this, AdminPanelActivity::class.java)
                                )
                                finish()
                            }else{
                                val popUpError1 = androidx.appcompat.app.AlertDialog.Builder(this)
                                popUpError1.setTitle("Login failed")
                                popUpError1.setMessage("The email and/or password you entered is incorrect")
                                popUpError1.setPositiveButton("Ok") { dialog, _ ->
                                    dialog.dismiss()
                                }
                                popUpError1.show()
                                FirebaseAuth.getInstance().signOut()
                            }
                        } else {
                            val popUpError1 = androidx.appcompat.app.AlertDialog.Builder(this)
                            popUpError1.setTitle("Login failed")
                            popUpError1.setMessage("The email and/or password you entered is incorrect")
                            popUpError1.setPositiveButton("Ok") { dialog, _ ->
                                dialog.dismiss()
                            }
                            popUpError1.show()
                        }
                    }
            }
        } catch (exception: Exception){
            displayError()
        }
    }

    private fun displayError(){
        Toast.makeText(applicationContext, getString(R.string.db_error_message), Toast.LENGTH_LONG).show()
    }
}