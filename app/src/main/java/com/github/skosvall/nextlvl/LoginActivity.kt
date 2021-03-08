package com.github.skosvall.nextlvl

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.lang.Exception

const val RC_SIGN_IN = 1

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        auth = FirebaseAuth.getInstance()


        val buttonLogin = this.findViewById<Button>(R.id.login)
        val googleLogin = this.findViewById<SignInButton>(R.id.google_sign_in_button)

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

        googleLogin.setOnClickListener{
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

            // Build a GoogleSignInClient with the options specified by gso.
            val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
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
                            val currentUserEmail = auth.currentUser.email

                            if(currentUser != null && currentUserEmail == "ADMIN EMAIL HERE")
                            startActivity(
                                Intent(this, adminPanelActivity::class.java)
                            )else{
                                val popUpError1 = AlertDialog.Builder(this)
                                popUpError1.setTitle("Login failed")
                                popUpError1.setMessage("The email and/or password you entered is incorrect")
                                popUpError1.setPositiveButton("Ok") { dialog, which ->
                                    dialog.dismiss()
                                }
                                popUpError1.show()
                                FirebaseAuth.getInstance().signOut()
                            }
                        } else {
                            val popUpError1 = AlertDialog.Builder(this)
                            popUpError1.setTitle("Login failed")
                            popUpError1.setMessage("The email and/or password you entered is incorrect")
                            popUpError1.setPositiveButton("Ok") { dialog, which ->
                                dialog.dismiss()
                            }
                            popUpError1.show()
                        }
                    }
            }
        } catch (exception: Exception){
            Log.d("LOGIN", exception.toString())
        }
    }
}