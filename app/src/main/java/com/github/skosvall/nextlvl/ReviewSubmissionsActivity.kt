package com.github.skosvall.nextlvl

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class ReviewSubmissionsActivity : AppCompatActivity() {
    lateinit var dorAdapter: ArrayAdapter<Submission>
    lateinit var nhieAdapter: ArrayAdapter<Submission>
    
    companion object FirebaseManager {
        val DOR = "Dare or Drink"
        val NHIE = "Never have i ever"
        const val DOD_SUBMISSIONS = "DOD_SUBMISSIONS"
        const val NHIE_SUBMISSIONS = "NHIE_SUBMISSIONS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_submissions)

        val db = FirebaseFirestore.getInstance()

        val dorListView = this.findViewById<ListView>(R.id.dorList)
        val nhieListView = this.findViewById<ListView>(R.id.nhieList)

        val dorLoadingSpinner = this.findViewById<ProgressBar>(R.id.dorSpinner)
        val nhieLoadingSpinner = this.findViewById<ProgressBar>(R.id.nhieSpinner)

        val dareOrDrinkDbEng =
                db.collection("mobileGamesData").document("dareOrDrink").collection("english")
                        .document("questions")
        val dareOrDrinkDbSwe =
                db.collection("mobileGamesData").document("dareOrDrink").collection("swedish")
                        .document("questions")
        dorAdapter = ArrayAdapter<Submission>(
                this,
                R.layout.submissionrow,
                android.R.id.text1,
                dorSubmissionRepository.getAllSubmissions()
        )

        dorListView.adapter = dorAdapter

        val neverHaveIEverDbEng =
                db.collection("mobileGamesData").document("neverHaveIEver").collection("english")
                        .document("statements")
        val neverHaveIEverDbSwe =
                db.collection("mobileGamesData").document("neverHaveIEver").collection("swedish")
                        .document("statements")

        nhieAdapter = ArrayAdapter<Submission>(
                this,
                R.layout.submissionrow,
                android.R.id.text1,
                nhieSubmissionRepository.getAllSubmissions()
        )

        nhieListView.adapter = nhieAdapter

        if(savedInstanceState == null) {
            dorLoadingSpinner.visibility = View.VISIBLE;
            nhieLoadingSpinner.visibility = View.VISIBLE;

            dorSubmissionRepository.clear()
            nhieSubmissionRepository.clear()

            dareOrDrinkDbEng.get()
                    .addOnSuccessListener { fields ->
                        if (fields != null) {
                            val myArray = fields.get("questionSuggestions") as List<String>?
                            if (myArray != null) {
                                for (item in myArray) {
                                    dorSubmissionRepository.addSubmission(item, "english")
                                }
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
                                            dorAdapter.notifyDataSetChanged()
                                            dorLoadingSpinner.visibility = View.INVISIBLE
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

            neverHaveIEverDbEng.get()
                    .addOnSuccessListener { fields ->
                        if (fields != null) {
                            val myArray = fields.get("statementSuggestions") as List<String>?
                            if (myArray != null) {
                                for (item in myArray) {
                                    nhieSubmissionRepository.addSubmission(item, "english")
                                }
                                nhieAdapter.notifyDataSetChanged()
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
                                                nhieSubmissionRepository.addSubmission(item, "swedish")
                                                nhieLoadingSpinner.visibility = View.INVISIBLE
                                            }
                                            nhieAdapter.notifyDataSetChanged()
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
        } else {
            dorLoadingSpinner.visibility = View.INVISIBLE;
            nhieLoadingSpinner.visibility = View.INVISIBLE;
        }


        dorListView.setOnItemClickListener { parent, view, position, id ->

            val submission = dorListView.adapter.getItem(position) as Submission

            val popUpError1 = AlertDialog.Builder(this)
            popUpError1.setTitle("Submission")
            popUpError1.setMessage("This is a test")
            popUpError1.setPositiveButton("Remove") { dialog, which ->
                db.collection("mobileGamesData").document("dareOrDrink")
                        .collection(submission.lang).document("questions")
                        .update("questionSuggestions", FieldValue.arrayRemove(submission.text))
                dorSubmissionRepository.deleteSubmissionById(submission.id)
                dorAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            popUpError1.setNegativeButton("Edit/Submit") { dialog, which ->
                val intent = Intent(this, EditSubmissionActivity::class.java)
                intent.putExtra("submissionId", submission.id)
                intent.putExtra("gameType", ReviewSubmissionsActivity.DOR)
                startActivity(
                        intent
                )
                dialog.dismiss()
            }
            popUpError1.show()
        }


        nhieListView.setOnItemClickListener { parent, view, position, id ->

            val submission = nhieListView.adapter.getItem(position) as Submission

            val popUpError1 = AlertDialog.Builder(this)
            popUpError1.setTitle("Submission")
            popUpError1.setMessage("This is a test")
            popUpError1.setPositiveButton("Remove") { dialog, which ->
                db.collection("mobileGamesData").document("neverHaveIEver")
                        .collection(submission.lang).document("statements")
                        .update("statementSuggestions", FieldValue.arrayRemove(submission.text))
                nhieSubmissionRepository.deleteSubmissionById(submission.id)
                nhieAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            popUpError1.setNegativeButton("Edit/Submit") { dialog, which ->
                val intent = Intent(this, EditSubmissionActivity::class.java)
                intent.putExtra("submissionId", submission.id)
                intent.putExtra("gameType", ReviewSubmissionsActivity.NHIE)
                startActivity(
                        intent
                )
                dialog.dismiss()
            }
            popUpError1.show()
        }
    }

    /*override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(DOD_SUBMISSIONS, dorSubmissionRepository)
        outState.putParcelable(NHIE_SUBMISSIONS, nhieSubmissionRepository)
    }*/

    override fun onResume() {
        super.onResume()
        nhieAdapter.notifyDataSetChanged()
        dorAdapter.notifyDataSetChanged()
    }
}