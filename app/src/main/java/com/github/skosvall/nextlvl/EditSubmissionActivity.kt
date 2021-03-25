package com.github.skosvall.nextlvl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.properties.Delegates

class EditSubmissionActivity : AppCompatActivity() {
    companion object{
        const val GAME_TYPE = "gameType"
        const val SUBMISSION_ID = "submissionId"
    }

    private lateinit var gameType: String
    private var submissionId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_submission)

        val db = FirebaseFirestore.getInstance()

        if(savedInstanceState == null){
            gameType = intent.getStringExtra(GAME_TYPE).toString()
            submissionId = intent.getIntExtra(SUBMISSION_ID, -1)
        } else {
            gameType = savedInstanceState.getString(GAME_TYPE).toString()
            submissionId = savedInstanceState.getInt(SUBMISSION_ID)
        }

        val submission: Submission? = if(gameType == ReviewSubmissionsActivity.DARE_OR_DRINK){
            dareOrDrinkSubmissionRepository.getSubmissionById(submissionId)
        }else{
            neverHaveIEverSubmissionRepository.getSubmissionById(submissionId)
        }

        val editSubmissionTextViewEng = findViewById<EditText>(R.id.edit_submission_edittext_english)
        val editSubmissionTextViewSwe = findViewById<EditText>(R.id.edit_submission_edittext_swedish)

        if(submission != null){
            if(submission.lang == "swedish"){
                editSubmissionTextViewSwe.setText(submission.text)
            }else{
                editSubmissionTextViewEng.setText(submission.text)
            }
        }

        val buttonSubmit = this.findViewById<Button>(R.id.submit_button)
        val buttonDismiss = this.findViewById<Button>(R.id.dismiss_button)

        buttonSubmit.setOnClickListener{
            var succeded = false
            if(gameType == ReviewSubmissionsActivity.DARE_OR_DRINK){
                val newTextEng = editSubmissionTextViewEng.editableText.toString()
                val newTextSwe = editSubmissionTextViewSwe.editableText.toString()

                if (submission != null) {
                    db.collection("mobileGamesData").document("dareOrDrink")
                        .collection("english").document("questions")
                        .update("questionSuggestions", FieldValue.arrayRemove(submission.text))
                    db.collection("mobileGamesData").document("dareOrDrink")
                        .collection("english").document("questions")
                        .update("questions", FieldValue.arrayUnion(newTextEng))
                    db.collection("mobileGamesData").document("dareOrDrink")
                        .collection("swedish").document("questions")
                        .update("questionSuggestions", FieldValue.arrayRemove(submission.text))
                    db.collection("mobileGamesData").document("dareOrDrink")
                        .collection("swedish").document("questions")
                        .update("questions", FieldValue.arrayUnion(newTextSwe))
                            .addOnSuccessListener {
                                onSuccess()
                                succeded = true
                                submissionRepository.deleteSubmissionById(submissionId)
                            }

                    if(!succeded) {
                        displayError()
                    }
                }
            }else{
                val newTextEng = editSubmissionTextViewEng.editableText.toString()
                val newTextSwe = editSubmissionTextViewSwe.editableText.toString()

                if (submission != null) {
                    db.collection("mobileGamesData").document("neverHaveIEver")
                        .collection("english").document("statements")
                        .update("statementSuggestions", FieldValue.arrayRemove(submission.text))
                    db.collection("mobileGamesData").document("neverHaveIEver")
                        .collection("english").document("statements")
                        .update("statements", FieldValue.arrayUnion(newTextEng))
                    db.collection("mobileGamesData").document("neverHaveIEver")
                        .collection("swedish").document("statements")
                        .update("statementSuggestions", FieldValue.arrayRemove(submission.text))
                    db.collection("mobileGamesData").document("neverHaveIEver")
                        .collection("swedish").document("statements")
                        .update("statements", FieldValue.arrayUnion(newTextSwe))
                            .addOnSuccessListener {
                                onSuccess()
                                succeded = true
                            }

                    if(!succeded) {
                        displayError()
                    }
                }
            }
        }

        buttonDismiss.setOnClickListener {
            this.finish()
        }
    }

    private fun onSuccess(){
        Toast.makeText(applicationContext, getString(R.string.suggestion_successfully_approved), Toast.LENGTH_SHORT).show()
        this.finish()
    }

    private fun displayError(){
        Toast.makeText(applicationContext, getString(R.string.error_has_occured), Toast.LENGTH_LONG).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(GAME_TYPE, gameType)
        outState.putInt(SUBMISSION_ID, submissionId)
    }
}