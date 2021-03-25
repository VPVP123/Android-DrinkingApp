package com.github.skosvall.nextlvl

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class AdminPanelActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_panel)

        auth = FirebaseAuth.getInstance()

        val buttonSubmissions = this.findViewById<Button>(R.id.manage_submissions_button)
        val buttonLogout = this.findViewById<Button>(R.id.logout_button)

        buttonSubmissions.setOnClickListener{
            startActivity(Intent(this, ReviewSubmissionsActivity::class.java))
        }

        buttonLogout.setOnClickListener{
            signOut()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        signOut()
    }

    private fun signOut(){
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, MainActivity::class.java))
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser == null){
            signOut()
        }
    }
}