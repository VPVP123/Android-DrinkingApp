package com.github.skosvall.nextlvl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class EditSubmissionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_submission)

        val db = FirebaseFirestore.getInstance()
        val gameType = intent.getStringExtra("gameType")

        val submissionId = intent.getIntExtra("submissionId", -1)
        val submission = submissionRepository.getSubmissionById(submissionId)


        val editSubmissionTextViewEng = findViewById<EditText>(R.id.editSubmissionEng)as EditText
        val editSubmissionTextViewSwe = findViewById<EditText>(R.id.editSubmissionSwe)as EditText

        if(submission != null){
            if(submission.lang == "swedish"){
                editSubmissionTextViewSwe.setText(submission.text)
            }else{
                editSubmissionTextViewEng.setText(submission.text)
            }
        }

        val buttonSubmit = this.findViewById<Button>(R.id.buttonSubmit)
        val buttonDismiss = this.findViewById<Button>(R.id.buttonDismiss)


        buttonSubmit.setOnClickListener{
            if(gameType == chooseSubmissionActivity.DOR){
                val newEditSubmissionTextViewEng = findViewById<EditText>(R.id.editSubmissionEng)as EditText
                val newEditSubmissionTextViewSwe = findViewById<EditText>(R.id.editSubmissionSwe)as EditText
                val newTextEng = newEditSubmissionTextViewEng.editableText.toString()
                val newTextSwe = newEditSubmissionTextViewSwe.editableText.toString()
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
                    submissionRepository.deleteSubmissionById(submissionId)
                    this.finish()
                }
            }else{
                val newEditSubmissionTextViewEng = findViewById<EditText>(R.id.editSubmissionEng)as EditText
                val newEditSubmissionTextViewSwe = findViewById<EditText>(R.id.editSubmissionSwe)as EditText
                val newTextEng = newEditSubmissionTextViewEng.editableText.toString()
                val newTextSwe = newEditSubmissionTextViewSwe.editableText.toString()


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
                    submissionRepository.deleteSubmissionById(submissionId)
                    this.finish()
                }
            }
        }

        buttonDismiss.setOnClickListener {
            this.finish()
        }

    }
}