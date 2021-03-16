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

        val buttonSubmission = this.findViewById<Button>(R.id.manageSubmissionsButton)
        val buttonLogout = this.findViewById<Button>(R.id.buttonLogout)

        buttonSubmission.setOnClickListener{
            startActivity(
                Intent(this, ReviewSubmissionsActivity::class.java)
            )
        }

        buttonLogout.setOnClickListener{
            signOut()
        }
    }

    private fun signOut(){
        FirebaseAuth.getInstance().signOut()
        startActivity(
                Intent(this, MainActivity::class.java)
        )
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser == null){
            signOut()
        }
    }


}