package com.github.skosvall.nextlvl

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text
import java.util.stream.IntStream.range
import kotlin.properties.Delegates


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val LIST_OF_PLAYERS = "listOfPlayers"
private const val LIST_OF_STATEMENTS = "listOfStatements"
private const val LIST_OF_QUESTIONS = "listOfQuestions"

/**
 * A simple [Fragment] subclass.
 * Use the [PlayMobileGamesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class PlayMobileGamesFragment : Fragment() {
    lateinit var statements: MutableList<String>
    lateinit var statementsCopy: MutableList<String>

    lateinit var questions: List<DareOrDrinkQuestion>
    lateinit var questionsCopy: MutableList<DareOrDrinkQuestion>
    lateinit var currentQuestion: DareOrDrinkQuestion
    lateinit var players: List<String>
    lateinit var playersCopy: MutableList<String>
    var activityJustRestarted: Boolean = false
    lateinit var previouslyDisplayedString: String

    private lateinit var loadingSpinner: ProgressBar
    lateinit var textView: TextView
    lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = FirebaseFirestore.getInstance()

        val currentLang = getString(R.string.currentLang)

        if (activity is PlayNeverHaveIEverActivity) {
            val getStatements = db.collection("mobileGamesData").document("neverHaveIEver").collection(currentLang).document("statements")

            statements = mutableListOf()
            statementsCopy = mutableListOf()

            getStatements.get()
                .addOnSuccessListener { statement ->
                    if (statement != null) {
                        Log.d("exist", "DocumentSnapshot data: ${statement.data}")
                        val myArray = statement.get("statements") as List<String>?
                        if (myArray != null) {
                            for (item in myArray) {
                                statements.add(item)
                            }
                            loadingSpinner.visibility = View.INVISIBLE
                            statementsCopy = statements.toMutableList()
                            statementsCopy.shuffle()
                            if (savedInstanceState != null) {
                                val previousLang = savedInstanceState.getString(PREVIOUS_LANGUAGE)
                                val currentLang = getString(R.string.currentLang)
                                if (previousLang == currentLang) {
                                    val text = savedInstanceState.getString(CURRENT_STATEMENT)
                                    statementsCopy.add(text as String)
                                }
                            }
                            changeNeverHaveIEverStatement()
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("errorDB", "get failed with ", exception)
                }
        } else if (activity is PlayDareOrDrinkActivity) {
            val getQuestions = db.collection("mobileGamesData").document("dareOrDrink").collection(currentLang).document("questions")

            questions = mutableListOf()
            questionsCopy = mutableListOf()

            getQuestions.get()
                .addOnSuccessListener { question ->
                    if (question != null) {
                        Log.d("exist", "DocumentSnapshot data: ${question.data}")
                        val myArray = question.get("questions") as List<String>?
                        if (myArray != null) {
                            for (item in myArray) {
                                var newQuestion = DareOrDrinkQuestion(item)
                                (questions as MutableList<DareOrDrinkQuestion>).add(newQuestion)
                                loadingSpinner.visibility = View.INVISIBLE;
                            }
                            questionsCopy = questions.toMutableList()
                            questionsCopy.shuffle()
                            if (savedInstanceState != null) {
                                val previousLang = savedInstanceState.getString(PREVIOUS_LANGUAGE)
                                val currentLang = getString(R.string.currentLang)
                                if (previousLang == currentLang) {
                                    val question = savedInstanceState.getString(CURRENT_QUESTION)
                                    previouslyDisplayedString = question!!
                                    activityJustRestarted = true
                                }
                            }
                            changeDareOrDrinkQuestion()
                        }
                    } else {
                        Log.d("noExist", "No document found")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("errorDB", "get failed with ", exception)
                }
        }

        if (activity is PlayDareOrDrinkActivity) {
            if (savedInstanceState == null) {
                arguments.let {
                    val playersArray = it?.getStringArray(PLAYER_NAMES)
                    if ((playersArray as Array<String>).count() > 0) {
                        players = playersArray.toList()
                    }
                }
            } else {
                players = savedInstanceState.getStringArray(PLAYER_NAMES)!!.toMutableList()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_play_mobile_games, container, false)
        textView = view.findViewById<TextView>(R.id.statementTextview)
        nextButton = view.findViewById<Button>(R.id.neverHaveIEverNextButton)
        loadingSpinner = view.findViewById<ProgressBar>(R.id.progressSpinner)

        loadingSpinner.visibility = View.VISIBLE

        if (textView != null && nextButton != null) {
            nextButton.setOnClickListener {
                nextButtonClick()
            }
        }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (activity is PlayNeverHaveIEverActivity) {
            //if(statements.takeLast(1)[0] != textView.text) {
            outState.putString(CURRENT_STATEMENT, textView.text as String)
            //}
        } else if (activity is PlayDareOrDrinkActivity) {
            outState.putString(CURRENT_QUESTION, textView.text as String)
            outState.putStringArray(PLAYER_NAMES, players.toTypedArray())
        }
        outState.putString(PREVIOUS_LANGUAGE, getString(R.string.currentLang))
    }

    private fun nextButtonClick() {
        if (activity is PlayNeverHaveIEverActivity) {
            changeNeverHaveIEverStatement()
        } else if (activity is PlayDareOrDrinkActivity) {
            changeDareOrDrinkQuestion()
        }
    }

    private fun changeNeverHaveIEverStatement() {
        val currentStatement = statementsCopy.takeLast(1)[0]
        if (textView.text != currentStatement) {
            textView.text = currentStatement
            statementsCopy.remove(currentStatement)
            if (statementsCopy.count() == 0) {
                statementsCopy = statements.toMutableList()
                statementsCopy.shuffle()
            }
        } else {
            statementsCopy.remove(currentStatement)
            nextButtonClick()
        }
    }

    private fun changeDareOrDrinkQuestion() {
        var currentQuestionPlayers = mutableListOf<String>()
        var nrOfPlayersRequiredForQuestion: Int

        if (!activityJustRestarted) {
            while (true) {
                if (questionsCopy.count() > 0) {
                    currentQuestion = questionsCopy.takeLast(1)[0]
                    questionsCopy.remove(currentQuestion)
                    nrOfPlayersRequiredForQuestion = currentQuestion.getNrOfPlayersRequired()
                    playersCopy = players.toMutableList()

                    if (nrOfPlayersRequiredForQuestion <= playersCopy.count()) {
                        if (nrOfPlayersRequiredForQuestion > 0) {
                            for (i in range(0, nrOfPlayersRequiredForQuestion)) {
                                val randomPlayer = playersCopy.shuffled().takeLast(1)[0]
                                currentQuestionPlayers.add(randomPlayer)
                                playersCopy.remove(randomPlayer)
                            }
                            textView.text = currentQuestion.getCompleteQuestion(currentQuestionPlayers)
                            break
                        } else {
                            textView.text = currentQuestion.question
                            break
                        }
                    }
                } else {
                    questionsCopy = questions.toMutableList()
                    questionsCopy.shuffle()
                    continue
                }
            }
        } else {
            textView.text = previouslyDisplayedString
            activityJustRestarted = false
        }
    }

    companion object {
        const val CURRENT_STATEMENT = "CURRENT_STATEMENT"
        const val CURRENT_QUESTION = "CURRENT_QUESTION"
        const val STATEMENTS = "STATEMENTS"
        const val QUESTIONS = "QUESTIONS"
        const val PLAYER_NAMES = "PLAYER_NAMES"
        const val PREVIOUS_LANGUAGE = "PREVIOUS_LANG"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PlayMobileGamesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(playerNamesArray: Array<String>) =
            PlayMobileGamesFragment().apply {
                arguments = Bundle().apply {
                    putStringArray(PLAYER_NAMES, playerNamesArray)
                }
            }
    }
}