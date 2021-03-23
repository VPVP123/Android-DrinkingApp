package com.github.skosvall.nextlvl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        const val DOR = "Dare or Drink"
        const val NHIE = "Never have i ever"
        const val DOD_SUBMISSIONS = "DOD_SUBMISSIONS"
        const val NHIE_SUBMISSIONS = "NHIE_SUBMISSIONS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_submissions)

        val db = FirebaseFirestore.getInstance()

        val dareOrDrinkListView = this.findViewById<ListView>(R.id.dare_or_drink_list)
        val neverHaveIEverListView = this.findViewById<ListView>(R.id.never_have_i_ever_list)

        val dorLoadingSpinner = this.findViewById<ProgressBar>(R.id.dare_or_drink_spinner)
        val nhieLoadingSpinner = this.findViewById<ProgressBar>(R.id.never_have_i_ever_spinner)

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

        dareOrDrinkListView.adapter = dorAdapter

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

        neverHaveIEverListView.adapter = nhieAdapter

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
                            displayDbError()
                        }
                        dareOrDrinkDbSwe.get()
                                .addOnSuccessListener { fields ->
                                    if (fields != null) {
                                        val myArray = fields.get("questionSuggestions") as List<String>?
                                        if (myArray != null) {
                                            for (item in myArray) {
                                                dorSubmissionRepository.addSubmission(item, "swedish")
                                            }
                                            dorAdapter.notifyDataSetChanged()
                                        }
                                        dorLoadingSpinner.visibility = View.INVISIBLE
                                    } else {
                                        displayDbError()
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    displayDbError()
                                }
                    }
                    .addOnFailureListener { exception ->
                        displayDbError()
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
                            displayDbError()
                        }
                        neverHaveIEverDbSwe.get()
                                .addOnSuccessListener { fields ->
                                    if (fields != null) {
                                        val myArray = fields.get("statementSuggestions") as List<String>?
                                        if (myArray != null) {
                                            for (item in myArray) {
                                                nhieSubmissionRepository.addSubmission(item, "swedish")
                                            }
                                            nhieAdapter.notifyDataSetChanged()
                                        }
                                        nhieLoadingSpinner.visibility = View.INVISIBLE
                                    } else {
                                        displayDbError()
                                    }
                                }.addOnFailureListener { exception ->
                                    displayDbError()
                                }
                    }.addOnFailureListener { exception ->
                        displayDbError()
                    }
        } else {
            dorLoadingSpinner.visibility = View.INVISIBLE;
            nhieLoadingSpinner.visibility = View.INVISIBLE;
        }


        dareOrDrinkListView.setOnItemClickListener { _, _, position, _ ->

            val submission = dareOrDrinkListView.adapter.getItem(position) as Submission

            val dareOrDrinkPopUp = androidx.appcompat.app.AlertDialog.Builder(this)
            dareOrDrinkPopUp.setTitle(getString(R.string.submission))
            dareOrDrinkPopUp.setMessage(getString(R.string.submission_edit_popup_message))
            dareOrDrinkPopUp.setPositiveButton(getString(R.string.remove)) { dialog, _ ->
                db.collection("mobileGamesData").document("dareOrDrink")
                        .collection(submission.lang).document("questions")
                        .update("questionSuggestions", FieldValue.arrayRemove(submission.text))
                dorSubmissionRepository.deleteSubmissionById(submission.id)
                dorAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            dareOrDrinkPopUp.setNegativeButton(getString(R.string.edit_submit)) { dialog, _ ->
                val intent = Intent(this, EditSubmissionActivity::class.java)
                intent.putExtra(EditSubmissionActivity.SUBMISSION_ID, submission.id)
                intent.putExtra(EditSubmissionActivity.GAME_TYPE, ReviewSubmissionsActivity.DOR)
                startActivity(
                        intent
                )
                dialog.dismiss()
            }
            dareOrDrinkPopUp.show()
        }


        neverHaveIEverListView.setOnItemClickListener { _, _, position, _ ->

            val submission = neverHaveIEverListView.adapter.getItem(position) as Submission

            val neverHaveIEverPopUp = androidx.appcompat.app.AlertDialog.Builder(this)
            neverHaveIEverPopUp.setTitle(getString(R.string.submission))
            neverHaveIEverPopUp.setMessage(getString(R.string.submission_edit_popup_message))
            neverHaveIEverPopUp.setPositiveButton(getString(R.string.remove)) { dialog, _ ->
                db.collection("mobileGamesData").document("neverHaveIEver")
                        .collection(submission.lang).document("statements")
                        .update("statementSuggestions", FieldValue.arrayRemove(submission.text))
                nhieSubmissionRepository.deleteSubmissionById(submission.id)
                nhieAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            neverHaveIEverPopUp.setNegativeButton(getString(R.string.edit_submit)) { dialog, _ ->
                val intent = Intent(this, EditSubmissionActivity::class.java)
                intent.putExtra(EditSubmissionActivity.SUBMISSION_ID, submission.id)
                intent.putExtra(EditSubmissionActivity.GAME_TYPE, ReviewSubmissionsActivity.NHIE)
                startActivity(
                        intent
                )
                dialog.dismiss()
            }
            neverHaveIEverPopUp.show()
        }


    }

    fun displayDbError(){
        val errorPopUp = androidx.appcompat.app.AlertDialog.Builder(this)
        errorPopUp.setTitle(getString(R.string.error))
        errorPopUp.setMessage(getString(R.string.db_error_message))
        errorPopUp.setPositiveButton(getString(R.string.back)) { dialog, _ ->
            this.finish()
            dialog.dismiss()
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