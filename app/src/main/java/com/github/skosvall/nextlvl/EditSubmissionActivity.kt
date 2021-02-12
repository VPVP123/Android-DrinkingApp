package com.github.skosvall.nextlvl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class EditSubmissionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_submission)

        val submissionId = intent.getIntExtra("submissionId", -1)
        val submission = submissionRepository.getSubmissionById(submissionId)

        val editSubmissionTextView = findViewById<EditText>(R.id.editSubmission)as EditText

        editSubmissionTextView.setText(submission?.text)

        val buttonSubmit = this.findViewById<Button>(R.id.buttonSubmit)
        val buttonDismiss = this.findViewById<Button>(R.id.buttonDismiss)


        buttonSubmit.setOnClickListener{
            val newEditSubmissionTextView = findViewById<EditText>(R.id.editSubmission)as EditText
            val newText = newEditSubmissionTextView.editableText.toString()
            submissionRepository.updateSubmissionById(submissionId, newText)
            this.finish()
        }

        buttonDismiss.setOnClickListener {
            this.finish()
        }

    }
}