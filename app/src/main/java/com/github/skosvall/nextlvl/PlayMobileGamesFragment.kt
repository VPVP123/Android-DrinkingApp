package com.github.skosvall.nextlvl

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import java.util.stream.IntStream.range


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
    lateinit var players: List<String>
    lateinit var playersCopy: MutableList<String>

    lateinit var textView: TextView
    lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = FirebaseFirestore.getInstance()

        val getQuestions = db.collection("mobileGamesData").document("dareOrDrink")
        val getStatements = db.collection("mobileGamesData").document("neverHaveIEver")

        statements = mutableListOf()

        //Get statements from
        getStatements.get()
            .addOnSuccessListener { statement ->
                if(statement != null){
                    Log.d("exist", "DocumentSnapshot data: ${statement.data}")
                    val myArray = statement.get("statements") as MutableList<String>
                    if(myArray != null) {
                        for(item in myArray){
                            (statements.add(item) as String)
                        }
                    }
                }else{
                    Log.d("noExist", "No document found")
                }
            }
            .addOnFailureListener {exception ->
                Log.d("errorDB", "get failed with ", exception)

            }

        getQuestions.get()
            .addOnSuccessListener { question ->
                if(question != null){
                    Log.d("exist", "DocumentSnapshot data: ${question.data}")
                }else{
                    Log.d("noExist", "No document found")
                }
            }
            .addOnFailureListener {exception ->
                Log.d("errorDB", "get failed with ", exception)

            }

        /**
        statements = listOf("Never have I ever kissed a stranger",
            "Never have I ever taken a shower selfie",
            "Never have I ever been to a nude beach",
            "Never have I ever been to Spain",
            "Never have I ever slept in the buff",
            "Never have I ever worn speedos",
            "Never have I ever lied about anything",
            "Never have I ever spied on a girl online",
            "Never have I ever been dumped")
        **/

        questions = listOf(DareOrDrinkQuestion("1, tell everybody about you worst intimate experience, or drink four sips"),
                DareOrDrinkQuestion("1, dance without music for one minute or drink three sips"),
                DareOrDrinkQuestion("1, let 2 DM whoever they want from your instagram account, or drink three sips"),
                DareOrDrinkQuestion("1, show everyone the last thing you searched for, or drink three sips"))

        players = listOf("Fich", "Hugo", "vp")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_play_mobile_games, container, false)
        textView = view.findViewById<TextView>(R.id.statementTextview)
        nextButton = view.findViewById<Button>(R.id.neverHaveIEverNextButton)

        if(textView != null && nextButton != null){
            if(activity is PlayNeverHaveIEverActivity){
                statementsCopy = statements.toMutableList() //Copy list
                changeNeverHaveIEverStatement()
            }else if(activity is PlayDareOrDrinkActivity){

                questionsCopy = questions.toMutableList()
                changeDareOrDrinkQuestion()
            }

            nextButton.setOnClickListener {
                nextButtonClick()
            }
        }
        return view
    }

    private fun nextButtonClick(){
        if(activity is PlayNeverHaveIEverActivity){
            changeNeverHaveIEverStatement()
        }else if(activity is PlayDareOrDrinkActivity){
            changeDareOrDrinkQuestion()
        }
    }

    private fun changeNeverHaveIEverStatement(){
        val currentStatement = statementsCopy.shuffled().takeLast(1)[0]
        textView.text = currentStatement
        statementsCopy.remove(currentStatement)
        if(statementsCopy.count() == 0){
            statementsCopy = statements.toMutableList()
        }
    }

    private fun changeDareOrDrinkQuestion(){
        var currentQuestion: DareOrDrinkQuestion
        var currentQuestionPlayers = mutableListOf<String>()
        var nrOfPlayersRequiredForQuestion: Int

        while(true){
            if(questionsCopy.count() > 0){
                currentQuestion = questionsCopy.shuffled().takeLast(1)[0]
                questionsCopy.remove(currentQuestion)
                nrOfPlayersRequiredForQuestion = currentQuestion.getNrOfPlayersRequired()
                playersCopy = players.toMutableList()


                if(nrOfPlayersRequiredForQuestion <= playersCopy.count()){
                    if(nrOfPlayersRequiredForQuestion > 0){
                        for (i in range(0, nrOfPlayersRequiredForQuestion)) {
                            val randomPlayer = playersCopy.shuffled().takeLast(1)[0]
                            currentQuestionPlayers.add(randomPlayer)
                            playersCopy.remove(randomPlayer)
                        }
                        textView.text = currentQuestion.getCompleteQuestion(currentQuestionPlayers)
                        break
                    }else{
                        textView.text = currentQuestion.question
                        break
                    }
                }
            }else{
                questionsCopy = questions.toMutableList()
                continue
            }
        }
    }

    companion object {
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
                        putStringArray("PLAYER_NAMES", arrayOf<String>())
                    }
                }
    }
}