package com.github.skosvall.nextlvl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore

class PlayMobileGamesFragment : Fragment() {
    private lateinit var statements: MutableList<String>
    private lateinit var statementsCopy: MutableList<String>
    private lateinit var questions: List<DareOrDrinkQuestion>
    private lateinit var questionsCopy: MutableList<DareOrDrinkQuestion>
    private lateinit var currentQuestion: DareOrDrinkQuestion
    private lateinit var players: List<String>
    private lateinit var playersCopy: MutableList<String>
    private lateinit var previouslyDisplayedString: String
    private lateinit var loadingSpinner: ProgressBar
    private lateinit var textView: TextView
    private lateinit var nextButton: Button
    private var activityJustRestarted: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_play_mobile_games, container, false)
        textView = view.findViewById(R.id.statement_textview)
        nextButton = view.findViewById(R.id.never_have_i_ever_next_button)
        loadingSpinner = view.findViewById(R.id.progressSpinner)

        loadingSpinner.visibility = View.VISIBLE

        nextButton.setOnClickListener {
            nextButtonClick()
        }

        initializeGame(savedInstanceState)

        return view
    }

    private fun initializeGame(savedInstanceState: Bundle?) {
        val db = FirebaseFirestore.getInstance()
        val currentLang = getString(R.string.current_lang)

        if (activity is PlayNeverHaveIEverActivity) {
            statements = mutableListOf()
            statementsCopy = mutableListOf()

            if (savedInstanceState == null) {
                loadStatementsFromDb(db, currentLang)
            } else {
                val previousLang = savedInstanceState.getString(PREVIOUS_LANGUAGE)
                if (previousLang != currentLang) {
                    loadStatementsFromDb(db, currentLang)
                } else if (previousLang == currentLang) {
                    statements = savedInstanceState.getStringArray(STATEMENTS)!!.toMutableList()
                    statementsCopy =
                        savedInstanceState.getStringArray(STATEMENTS_COPY)!!.toMutableList()

                    val previousStatement = savedInstanceState.getString(CURRENT_STATEMENT)
                    statementsCopy.add(previousStatement as String)
                    loadingSpinner.visibility = View.INVISIBLE
                    changeNeverHaveIEverStatement()
                }
            }
        } else if (activity is PlayDareOrDrinkActivity) {
            questions = mutableListOf()
            questionsCopy = mutableListOf()
            players = mutableListOf()

            if (savedInstanceState == null) {
                loadQuestionsFromDb(db, currentLang)

                arguments.let {
                    val playersArray = it?.getStringArray(PLAYER_NAMES)
                    if ((playersArray as Array<String>).count() > 0) {
                        players = playersArray.toList()
                    }
                }
            } else {
                val previousLang = savedInstanceState.getString(PREVIOUS_LANGUAGE)

                if (previousLang != currentLang) {
                    loadQuestionsFromDb(db, currentLang)
                } else {
                    questions = savedInstanceState.getParcelableArray(QUESTIONS)!!
                        .filterIsInstance<DareOrDrinkQuestion>()
                    questionsCopy = savedInstanceState.getParcelableArray(QUESTIONS_COPY)!!
                        .filterIsInstance<DareOrDrinkQuestion>().toMutableList()
                    players = savedInstanceState.getStringArray(PLAYER_NAMES)!!.toMutableList()

                    val question = savedInstanceState.getString(CURRENT_QUESTION)
                    previouslyDisplayedString = question!!
                    activityJustRestarted = true

                    loadingSpinner.visibility = View.INVISIBLE

                    changeDareOrDrinkQuestion()
                }
            }
        }
    }

    private fun loadStatementsFromDb(db: FirebaseFirestore, currentLang: String) {
        val getStatements =
            db.collection("mobileGamesData").document("neverHaveIEver").collection(currentLang)
                .document("statements")

        getStatements.get()
            .addOnSuccessListener { statement ->
                if (statement != null) {
                    val myArray = statement.get("statements") as List<String>?
                    if (myArray != null) {
                        for (item in myArray) {
                            statements.add(item)
                        }
                        loadingSpinner.visibility = View.INVISIBLE
                        statementsCopy = statements.toMutableList()
                        statementsCopy.shuffle()

                        changeNeverHaveIEverStatement()
                    }
                }
            }
            .addOnFailureListener { exception ->
                displayError()
            }
    }

    private fun loadQuestionsFromDb(db: FirebaseFirestore, currentLang: String) {
        val getQuestions =
            db.collection("mobileGamesData").document("dareOrDrink").collection(currentLang)
                .document("questions")

        getQuestions.get()
            .addOnSuccessListener { question ->
                if (question != null) {
                    val myArray = question.get("questions") as List<String>?
                    if (myArray != null) {
                        for (item in myArray) {
                            val newQuestion = DareOrDrinkQuestion(item)
                            (questions as MutableList<DareOrDrinkQuestion>).add(newQuestion)
                            loadingSpinner.visibility = View.INVISIBLE;
                        }
                        questionsCopy = questions.toMutableList()
                        questionsCopy.shuffle()

                        changeDareOrDrinkQuestion()
                    }
                } else {
                    displayError()
                }
            }
            .addOnFailureListener { _ ->
                displayError()
            }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (activity is PlayNeverHaveIEverActivity) {
            outState.putStringArray(STATEMENTS, statements.toTypedArray())
            outState.putStringArray(STATEMENTS_COPY, statementsCopy.toTypedArray())
            outState.putString(CURRENT_STATEMENT, textView.text as String)
        } else if (activity is PlayDareOrDrinkActivity) {
            outState.putString(CURRENT_QUESTION, textView.text as String)
            outState.putParcelableArray(QUESTIONS, questions.toTypedArray())
            outState.putParcelableArray(QUESTIONS_COPY, questionsCopy.toTypedArray())
            outState.putStringArray(PLAYER_NAMES, players.toTypedArray())
        }
        outState.putString(PREVIOUS_LANGUAGE, getString(R.string.current_lang))
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
        val currentQuestionPlayers = mutableListOf<String>()
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
                            for (i in 0 until nrOfPlayersRequiredForQuestion) {
                                val randomPlayer = playersCopy.shuffled().takeLast(1)[0]
                                currentQuestionPlayers.add(randomPlayer)
                                playersCopy.remove(randomPlayer)
                            }
                            textView.text =
                                currentQuestion.getCompleteQuestion(currentQuestionPlayers)
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

    private fun displayError() {
        Toast.makeText(context, getString(R.string.db_error_message), Toast.LENGTH_LONG).show()
    }

    companion object {
        const val CURRENT_STATEMENT = "CURRENT_STATEMENT"
        const val CURRENT_QUESTION = "CURRENT_QUESTION"
        const val STATEMENTS = "STATEMENTS"
        const val STATEMENTS_COPY = "STATEMENTS_COPY"
        const val QUESTIONS = "QUESTIONS"
        const val QUESTIONS_COPY = "QUESTIONS_COPY"
        const val PLAYER_NAMES = "PLAYER_NAMES"
        const val PREVIOUS_LANGUAGE = "PREVIOUS_LANG"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param playerNamesArray Show the text for mobile games.
         * @return A new instance of fragment PlayMobileGamesFragment.
         */
        @JvmStatic
        fun newInstance(playerNamesArray: Array<String>) =
            PlayMobileGamesFragment().apply {
                arguments = Bundle().apply {
                    putStringArray(PLAYER_NAMES, playerNamesArray)
                }
            }
    }
}