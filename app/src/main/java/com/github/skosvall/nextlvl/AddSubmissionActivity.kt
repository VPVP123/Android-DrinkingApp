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

        val spinner: Spinner = findViewById(R.id.sip_spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.sipArray,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }



        val submissionTextField = this.findViewById<EditText>(R.id.addSubmissionText)
        val buttonSubmit = this.findViewById<Button>(R.id.buttonSubmit)

        buttonSubmit.setOnClickListener{
            val submission = submissionTextField.editableText.toString()
            db.collection("mobileGamesData").document("dareOrDrink").update("questionSuggestions", FieldValue.arrayUnion(submission) )
        }
    }
}