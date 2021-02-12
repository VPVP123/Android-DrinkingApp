package com.github.skosvall.nextlvl

import java.util.stream.IntStream.range

class DareOrDrinkQuestion(var question: String){
    var nrOfPlayers: Int = 0

    init{
        if(question.filter { it == '1' }.count() > 0){
            nrOfPlayers++
        }
        if(question.filter { it == '2' }.count() > 0){
            nrOfPlayers++
        }
    }

    fun getNrOfPlayersRequired(): Int{
        return nrOfPlayers
    }

    fun getCompleteQuestion(playerNames: List<String>): String{
        var questionToReturn = question
        for(i in range(0, nrOfPlayers-1)){
            questionToReturn.replace((i+1).toString(), playerNames[i])
        }
        return questionToReturn
    }

    override fun toString() = ""
}
