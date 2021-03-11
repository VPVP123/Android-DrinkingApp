package com.github.skosvall.nextlvl

import android.os.Parcelable

class Submission(
    val id: Int,
    var text:String,
    var lang:String
){
    override fun toString() = text
}
