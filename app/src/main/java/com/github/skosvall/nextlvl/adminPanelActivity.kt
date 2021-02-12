package com.github.skosvall.nextlvl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth


class adminPanelActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_panel)

        auth = FirebaseAuth.getInstance()

        val buttonSubmission = this.findViewById<Button>(R.id.manageSubmissionsButton)
        val buttonLogout = this.findViewById<Button>(R.id.buttonLogout)


        buttonSubmission.setOnClickListener{
            startActivity(
                Intent(this, SubmissionActivity::class.java)
            )
        }

        buttonLogout.setOnClickListener{

            FirebaseAuth.getInstance().signOut()
            startActivity(
                Intent(this, MainActivity::class.java)
            )
        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){

        }
    }


}