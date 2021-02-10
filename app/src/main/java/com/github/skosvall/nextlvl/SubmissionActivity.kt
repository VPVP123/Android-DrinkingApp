package com.github.skosvall.nextlvl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class SubmissionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submission)

        val listView = this.findViewById<ListView>(R.id.submissionList)

        listView.adapter = ArrayAdapter<Submission>(
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            submissionRepository.getAllSubmissions()
        )



    }
}