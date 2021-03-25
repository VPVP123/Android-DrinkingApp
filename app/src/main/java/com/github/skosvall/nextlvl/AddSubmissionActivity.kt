package com.github.skosvall.nextlvl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class AddSubmissionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_submission)

        val db = FirebaseFirestore.getInstance()

        val submissionTextField = this.findViewById<EditText>(R.id.add_submission_text)
        val exampleTextField = this.findViewById<TextView>(R.id.example_text_view)

        val buttonSubmit = this.findViewById<Button>(R.id.submit_button)
        val buttonBack = this.findViewById<Button>(R.id.dismiss_button)

        val gameSpinner = this.findViewById<Spinner>(R.id.game_spinner)
        val options = resources.getStringArray(R.array.game_array)
        val currentLang = getString(R.string.current_lang)
        var selectedGame: String = null.toString()

        gameSpinner.adapter = ArrayAdapter(this, R.layout.spinner_item, options)

        gameSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                exampleTextField.text = getString(R.string.submission_please_select_a_game)
                selectedGame = null.toString()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(options[position] == "Dare or drink"){
                    exampleTextField.text = getString(R.string.dare_or_drink_example)
                    submissionTextField.hint = getString(R.string.submission_placeholder_dare_or_drink)
                    selectedGame = ReviewSubmissionsActivity.DARE_OR_DRINK
                }else{
                    exampleTextField.text = getString(R.string.never_have_i_ever_example)
                    submissionTextField.hint = getString(R.string.submission_placeholder_never_have_i_ever)
                    selectedGame = ReviewSubmissionsActivity.NEVER_HAVE_I_EVER
                }
            }
        }

        fun onSuccess(){
            submissionTextField.setText("")
            Toast.makeText(applicationContext, getString(R.string.suggestion_successfully_approved), Toast.LENGTH_SHORT).show()
        }

        buttonSubmit.setOnClickListener{
            var succeded = false
            if(selectedGame != null.toString()){
                if(selectedGame == ReviewSubmissionsActivity.DARE_OR_DRINK){
                    val submission = submissionTextField.editableText.toString()
                    db.collection("mobileGamesData").document("dareOrDrink")
                        .collection(currentLang).document("questions")
                        .update("questionSuggestions", FieldValue.arrayUnion(submission))
                            .addOnSuccessListener {
                                onSuccess()
                                succeded = true
                            }
                    if(!succeded) {
                        displayError()
                    }
                }else{
                    val submission = submissionTextField.editableText.toString()
                    db.collection("mobileGamesData").document("neverHaveIEver")
                        .collection(currentLang).document("statements")
                        .update("statementSuggestions", FieldValue.arrayUnion(submission))
                            .addOnSuccessListener {
                                onSuccess()
                                succeded = true
                            }
                    if(!succeded) {
                        displayError()
                    }
                }
                Toast.makeText(applicationContext, getString(R.string.suggestion_successfully_submitted), Toast.LENGTH_SHORT).show()
            }
        }
        buttonBack.setOnClickListener{
            finish()
        }
    }

    private fun displayError(){
        Toast.makeText(applicationContext, getString(R.string.error_has_occured), Toast.LENGTH_LONG).show()
    }
}