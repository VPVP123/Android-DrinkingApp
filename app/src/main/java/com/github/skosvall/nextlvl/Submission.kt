package com.github.skosvall.nextlvl

data class Submission(
    val id: Int,
    var text:String
){
    override fun toString() = text
}
