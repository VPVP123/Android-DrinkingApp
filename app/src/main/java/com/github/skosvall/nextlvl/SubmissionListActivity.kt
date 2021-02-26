package com.github.skosvall.nextlvl

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.getField

class SubmissionListActivity : AppCompatActivity() {

    lateinit var adapter: ArrayAdapter<Submission>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submission)
        val db = FirebaseFirestore.getInstance()

        val listView = this.findViewById<ListView>(R.id.submissionList)

        val loadingSpinner = this.findViewById<ProgressBar>(R.id.progressBar)

        loadingSpinner.visibility = View.VISIBLE;

        val dareOrDrinkDb = db.collection("mobileGamesData").document("dareOrDrink")

        adapter = ArrayAdapter<Submission>(
                this,
                R.layout.submissionrow,
                android.R.id.text1,
                submissionRepository.getAllSubmissions()
        )

        listView.adapter = adapter

        submissionRepository.clear()
        adapter.notifyDataSetChanged()

        dareOrDrinkDb.get()
                .addOnSuccessListener { fields ->
                    if(fields != null){
                        val myArray = fields.get("questionSuggestions") as List<String>?
                        if (myArray != null) {
                            for(item in myArray){
                                submissionRepository.addSubmission(item)
                            }
                            adapter.notifyDataSetChanged()
                            loadingSpinner.visibility = View.INVISIBLE;
                        }
                    }else{
                        Log.d("noExist", "No document found")
                    }
                }
                .addOnFailureListener {exception ->
                    Log.d("errorDB", "get failed with ", exception)

                }

        listView.setOnItemClickListener {parent, view, position, id ->

            val submission = listView.adapter.getItem(position) as Submission

            val popUpError1 = AlertDialog.Builder(this)
            popUpError1.setTitle("Submission")
            popUpError1.setMessage("This is a test")
            popUpError1.setNeutralButton( "Submit") { dialog, which ->
                db.collection("mobileGamesData").document("dareOrDrink").update("questionSuggestions", FieldValue.arrayRemove(submission.text) )
                db.collection("mobileGamesData").document("dareOrDrink").update("questions", FieldValue.arrayUnion(submission.text) )
                submissionRepository.deleteSubmissionById(submission.id)
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            popUpError1.setPositiveButton( "Remove") { dialog, which ->
                db.collection("mobileGamesData").document("dareOrDrink").update("questionSuggestions", FieldValue.arrayRemove(submission.text) )
                submissionRepository.deleteSubmissionById(submission.id)
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            popUpError1.setNegativeButton( "Edit") { dialog, which ->
                val intent = Intent(this, EditSubmissionActivity::class.java)
                intent.putExtra("submissionId", submission.id)
                startActivity(
                    intent
                )
                dialog.dismiss()
            }
            popUpError1.show()
        }

    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

}