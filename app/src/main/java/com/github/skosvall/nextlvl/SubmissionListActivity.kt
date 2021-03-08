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
        val currentLang = getString(R.string.currentLang)

        loadingSpinner.visibility = View.VISIBLE;

        val gameType = intent.getStringExtra("gameType")

        if (gameType == chooseSubmissionActivity.DOR) {
            val dareOrDrinkDbEng =
                db.collection("mobileGamesData").document("dareOrDrink").collection("english")
                    .document("questions")
            val dareOrDrinkDbSwe =
                db.collection("mobileGamesData").document("dareOrDrink").collection("swedish")
                    .document("questions")
            adapter = ArrayAdapter<Submission>(
                this,
                R.layout.submissionrow,
                android.R.id.text1,
                submissionRepository.getAllSubmissions()
            )

            listView.adapter = adapter

            submissionRepository.clear()
            adapter.notifyDataSetChanged()

            dareOrDrinkDbEng.get()
                .addOnSuccessListener { fields ->
                    if (fields != null) {
                        val myArray = fields.get("questionSuggestions") as List<String>?
                        if (myArray != null) {
                            for (item in myArray) {
                                submissionRepository.addSubmission(item, "english")
                            }
                            adapter.notifyDataSetChanged()
                        }
                    } else {
                        Log.d("noExist", "No document found")
                    }
                    dareOrDrinkDbSwe.get()
                        .addOnSuccessListener { fields ->
                            if (fields != null) {
                                val myArray = fields.get("questionSuggestions") as List<String>?
                                if (myArray != null) {
                                    for (item in myArray) {
                                        submissionRepository.addSubmission(item, "swedish")
                                    }
                                    adapter.notifyDataSetChanged()
                                    loadingSpinner.visibility = View.INVISIBLE
                                }
                            } else {
                                Log.d("noExist", "No document found")
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.d("errorDB", "get failed with ", exception)
                        }
                }
                .addOnFailureListener { exception ->
                    Log.d("errorDB", "get failed with ", exception)
                }

        } else if (gameType == chooseSubmissionActivity.NHIE) {
            val neverHaveIEverDbEng =
                db.collection("mobileGamesData").document("neverHaveIEver").collection("english")
                    .document("statements")
            val neverHaveIEverDbSwe =
                db.collection("mobileGamesData").document("neverHaveIEver").collection("swedish")
                    .document("statements")

            adapter = ArrayAdapter<Submission>(
                this,
                R.layout.submissionrow,
                android.R.id.text1,
                submissionRepository.getAllSubmissions()
            )

            listView.adapter = adapter

            submissionRepository.clear()
            adapter.notifyDataSetChanged()

            neverHaveIEverDbEng.get()
                .addOnSuccessListener { fields ->
                    if (fields != null) {
                        val myArray = fields.get("statementSuggestions") as List<String>?
                        if (myArray != null) {
                            for (item in myArray) {
                                submissionRepository.addSubmission(item, "english")
                            }
                            adapter.notifyDataSetChanged()
                        }
                    } else {
                        Log.d("noExist", "No document found")
                    }
                    neverHaveIEverDbSwe.get()
                        .addOnSuccessListener { fields ->
                            if (fields != null) {
                                val myArray = fields.get("statementSuggestions") as List<String>?
                                if (myArray != null) {
                                    for (item in myArray) {
                                        submissionRepository.addSubmission(item, "swedish")
                                        loadingSpinner.visibility = View.INVISIBLE
                                    }
                                    adapter.notifyDataSetChanged()

                                }
                            } else {
                                Log.d("noExist", "No document found")
                            }
                        }.addOnFailureListener { exception ->
                            Log.d("errorDB", "get failed with ", exception)
                        }
                }.addOnFailureListener { exception ->
                    Log.d("errorDB", "get failed with ", exception)
                }
        }

        listView.setOnItemClickListener { parent, view, position, id ->

            val submission = listView.adapter.getItem(position) as Submission

            val popUpError1 = AlertDialog.Builder(this)
            popUpError1.setTitle("Submission")
            popUpError1.setMessage("This is a test")
            popUpError1.setPositiveButton("Remove") { dialog, which ->
                if (gameType == chooseSubmissionActivity.DOR) {
                    db.collection("mobileGamesData").document("dareOrDrink")
                        .collection(submission.lang).document("questions")
                        .update("questionSuggestions", FieldValue.arrayRemove(submission.text))
                } else if (gameType == chooseSubmissionActivity.NHIE) {
                    db.collection("mobileGamesData").document("neverHaveIEver")
                        .collection(submission.lang).document("statements")
                        .update("statementSuggestions", FieldValue.arrayRemove(submission.text))
                }
                submissionRepository.deleteSubmissionById(submission.id)
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            popUpError1.setNegativeButton("Edit/Submit") { dialog, which ->
                val intent = Intent(this, EditSubmissionActivity::class.java)
                intent.putExtra("submissionId", submission.id)
                intent.putExtra("gameType", gameType)
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