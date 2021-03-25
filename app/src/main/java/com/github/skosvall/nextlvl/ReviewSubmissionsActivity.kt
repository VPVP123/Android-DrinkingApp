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
    lateinit var dareOrDrinkAdapter: ArrayAdapter<Submission>
    lateinit var neverHaveIEverAdapter: ArrayAdapter<Submission>
    
    companion object FirebaseManager {
        const val DARE_OR_DRINK = "Dare or Drink"
        const val NEVER_HAVE_I_EVER = "Never have i ever"
        const val DOD_SUBMISSIONS = "DOD_SUBMISSIONS"
        const val NHIE_SUBMISSIONS = "NHIE_SUBMISSIONS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_submissions)

        val db = FirebaseFirestore.getInstance()

        val dareOrDrinkListView = this.findViewById<ListView>(R.id.dare_or_drink_list)
        val neverHaveIEverListView = this.findViewById<ListView>(R.id.never_have_i_ever_list)

        val dareOrDrinkLoadingSpinner = this.findViewById<ProgressBar>(R.id.dare_or_drink_spinner)
        val neverHaveIEverLoadingSpinner = this.findViewById<ProgressBar>(R.id.never_have_i_ever_spinner)

        val dareOrDrinkDbEng =
                db.collection("mobileGamesData").document("dareOrDrink").collection("english")
                        .document("questions")
        val dareOrDrinkDbSwe =
                db.collection("mobileGamesData").document("dareOrDrink").collection("swedish")
                        .document("questions")
        dareOrDrinkAdapter = ArrayAdapter<Submission>(
                this,
                R.layout.submissionrow,
                android.R.id.text1,
                dareOrDrinkSubmissionRepository.getAllSubmissions()
        )

        dareOrDrinkListView.adapter = dareOrDrinkAdapter

        val neverHaveIEverDbEng =
                db.collection("mobileGamesData").document("neverHaveIEver").collection("english")
                        .document("statements")
        val neverHaveIEverDbSwe =
                db.collection("mobileGamesData").document("neverHaveIEver").collection("swedish")
                        .document("statements")

        neverHaveIEverAdapter = ArrayAdapter<Submission>(
                this,
                R.layout.submissionrow,
                android.R.id.text1,
                neverHaveIEverSubmissionRepository.getAllSubmissions()
        )

        neverHaveIEverListView.adapter = neverHaveIEverAdapter

        if(savedInstanceState == null) {
            dareOrDrinkLoadingSpinner.visibility = View.VISIBLE;
            neverHaveIEverLoadingSpinner.visibility = View.VISIBLE;

            dareOrDrinkSubmissionRepository.clear()
            neverHaveIEverSubmissionRepository.clear()

            dareOrDrinkDbEng.get()
                    .addOnSuccessListener { fields ->
                        if (fields != null) {
                            val myArray = fields.get("questionSuggestions") as List<String>?
                            if (myArray != null) {
                                for (item in myArray) {
                                    dareOrDrinkSubmissionRepository.addSubmission(item, "english")
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
                                                dareOrDrinkSubmissionRepository.addSubmission(item, "swedish")
                                            }
                                            dareOrDrinkAdapter.notifyDataSetChanged()
                                        }
                                        dareOrDrinkLoadingSpinner.visibility = View.INVISIBLE
                                    } else {
                                        displayDbError()
                                    }
                                }
                                .addOnFailureListener { _ ->
                                    displayDbError()
                                }
                    }
                    .addOnFailureListener { _ ->
                        displayDbError()
                    }

            neverHaveIEverDbEng.get()
                    .addOnSuccessListener { fields ->
                        if (fields != null) {
                            val myArray = fields.get("statementSuggestions") as List<String>?
                            if (myArray != null) {
                                for (item in myArray) {
                                    neverHaveIEverSubmissionRepository.addSubmission(item, "english")
                                }
                                neverHaveIEverAdapter.notifyDataSetChanged()
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
                                                neverHaveIEverSubmissionRepository.addSubmission(item, "swedish")
                                            }
                                            neverHaveIEverAdapter.notifyDataSetChanged()
                                        }
                                        neverHaveIEverLoadingSpinner.visibility = View.INVISIBLE
                                    } else {
                                        displayDbError()
                                    }
                                }.addOnFailureListener { _ ->
                                    displayDbError()
                                }
                    }.addOnFailureListener { _ ->
                        displayDbError()
                    }
        } else {
            dareOrDrinkLoadingSpinner.visibility = View.INVISIBLE
            neverHaveIEverLoadingSpinner.visibility = View.INVISIBLE
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
                dareOrDrinkSubmissionRepository.deleteSubmissionById(submission.id)
                dareOrDrinkAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            dareOrDrinkPopUp.setNegativeButton(getString(R.string.edit_submit)) { dialog, _ ->
                val intent = Intent(this, EditSubmissionActivity::class.java)
                intent.putExtra(EditSubmissionActivity.SUBMISSION_ID, submission.id)
                intent.putExtra(EditSubmissionActivity.GAME_TYPE, ReviewSubmissionsActivity.DARE_OR_DRINK)
                startActivity(intent)
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
                neverHaveIEverSubmissionRepository.deleteSubmissionById(submission.id)
                neverHaveIEverAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            neverHaveIEverPopUp.setNegativeButton(getString(R.string.edit_submit)) { dialog, _ ->
                val intent = Intent(this, EditSubmissionActivity::class.java)
                intent.putExtra(EditSubmissionActivity.SUBMISSION_ID, submission.id)
                intent.putExtra(EditSubmissionActivity.GAME_TYPE, ReviewSubmissionsActivity.NEVER_HAVE_I_EVER)
                startActivity(intent)
                dialog.dismiss()
            }
            neverHaveIEverPopUp.show()
        }
    }

    private fun displayDbError(){
        val errorPopUp = androidx.appcompat.app.AlertDialog.Builder(this)
        errorPopUp.setTitle(getString(R.string.error))
        errorPopUp.setMessage(getString(R.string.db_error_message))
        errorPopUp.setPositiveButton(getString(R.string.back)) { dialog, _ ->
            this.finish()
            dialog.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        neverHaveIEverAdapter.notifyDataSetChanged()
        dareOrDrinkAdapter.notifyDataSetChanged()
    }
}