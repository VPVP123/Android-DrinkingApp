package com.github.skosvall.nextlvl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class adminPanelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_panel)

        val buttonSubmission = this.findViewById<Button>(R.id.manageSubmissionsButton)


        buttonSubmission.setOnClickListener{
            startActivity(
                Intent(this, SubmissionActivity::class.java)
            )
        }


    }
}