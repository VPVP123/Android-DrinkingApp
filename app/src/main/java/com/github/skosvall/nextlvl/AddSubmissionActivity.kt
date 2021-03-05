package com.github.skosvall.nextlvl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.google.api.LogDescriptor
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class AddSubmissionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_submission)

        val db = FirebaseFirestore.getInstance()

        val submissionTextField = this.findViewById<EditText>(R.id.addSubmissionText)
        val buttonSubmit = this.findViewById<Button>(R.id.buttonSubmit)

        buttonSubmit.setOnClickListener{
            val submission = submissionTextField.editableText.toString()
            db.collection("mobileGamesData").document("dareOrDrink").update("questionSuggestions", FieldValue.arrayUnion(submission) )
        }
    }
}