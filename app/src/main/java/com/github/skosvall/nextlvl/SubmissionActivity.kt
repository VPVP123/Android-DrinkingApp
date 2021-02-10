package com.github.skosvall.nextlvl

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class SubmissionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submission)

        val listView = this.findViewById<ListView>(R.id.submissionList)

        var adapter = ArrayAdapter<Submission>(
            this,
            android.R.layout.simple_list_item_1,
            android.R.id.text1,
            submissionRepository.getAllSubmissions()
        )
        listView.adapter = adapter

        listView.setOnItemClickListener {parent, view, position, id ->

            val submission = listView.adapter.getItem(position) as Submission

            val popUpError1 = AlertDialog.Builder(this)
            popUpError1.setTitle("Submission")
            popUpError1.setMessage("This is a test")
            popUpError1.setNeutralButton( "Submit") { dialog, which ->
                dialog.dismiss()
            }
            popUpError1.setPositiveButton( "Remove") { dialog, which ->
                submissionRepository.deleteSubmissionById(submission.id)
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            popUpError1.setNegativeButton( "Edit") { dialog, which ->
                dialog.dismiss()
            }
            popUpError1.show()
        }
    }
}