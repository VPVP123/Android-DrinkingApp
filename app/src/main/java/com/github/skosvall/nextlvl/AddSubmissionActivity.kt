package com.github.skosvall.nextlvl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.api.LogDescriptor
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class AddSubmissionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_submission)

        val db = FirebaseFirestore.getInstance()

        val submissionTextField = this.findViewById<EditText>(R.id.addSubmissionText)
        val exampleTextField = this.findViewById<TextView>(R.id.exampleTextView)
        val buttonSubmit = this.findViewById<Button>(R.id.buttonSubmit)
        val gameSpinner = this.findViewById<Spinner>(R.id.gameSpinner)
        val options = resources.getStringArray(R.array.gameArray)
        val currentLang = getString(R.string.currentLang)
        var selectedGame: String = null.toString()

        gameSpinner.adapter = ArrayAdapter<String>(this, R.layout.spinner_item, options)


        gameSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                exampleTextField.text = "Please select a game"
                selectedGame = null.toString()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(options.get(position) == "Dare or drink"){
                    exampleTextField.text = "Example: 'DOR is selected'"
                    submissionTextField.hint = "Enter a dare"
                    selectedGame = ReviewSubmissionsActivity.DOR
                }else{
                    exampleTextField.text = "Example: 'NHIE is selected'"
                    submissionTextField.hint = "Enter a never have i ever"
                    selectedGame = ReviewSubmissionsActivity.NHIE
                }
            }

        }

        buttonSubmit.setOnClickListener{
            if(selectedGame != null.toString()){
                if(selectedGame == ReviewSubmissionsActivity.DOR){
                    val submission = submissionTextField.editableText.toString()
                    db.collection("mobileGamesData").document("dareOrDrink")
                        .collection(currentLang).document("questions")
                        .update("questionSuggestions", FieldValue.arrayUnion(submission))
                }else{
                    val submission = submissionTextField.editableText.toString()
                    db.collection("mobileGamesData").document("neverHaveIEver")
                        .collection(currentLang).document("statements")
                        .update("statementSuggestions", FieldValue.arrayUnion(submission))
                }
            }
        }
    }
}