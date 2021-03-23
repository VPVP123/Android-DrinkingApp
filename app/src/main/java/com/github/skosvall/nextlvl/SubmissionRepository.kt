package com.github.skosvall.nextlvl

import android.os.Parcel
import android.os.Parcelable


val dorSubmissionRepository = SubmissionRepository().apply {

}

val nhieSubmissionRepository = SubmissionRepository().apply {

}

val submissionRepository = SubmissionRepository().apply {

}

class SubmissionRepository() : Parcelable{

    private val submissions = mutableListOf<Submission>()

    constructor(parcel: Parcel) : this() {
    }

    fun addSubmission(text: String, lang: String): Int{
        val id = when {
            submissions.count() == 0 -> 1
            else -> submissions.last().id+1
        }
        submissions.add(
            Submission(
            id,
            text,
            lang
        )
        )
        return id
    }

    fun clear(){
        submissions.removeAll(submissions)
    }

    fun getAllSubmissions() = submissions

    fun getSubmissionById(id: Int) =
        submissions.find {
            it.id == id
        }

    fun deleteSubmissionById(id: Int) =
        submissions.remove(
            submissions.find {
                it.id == id
            }
        )

    fun updateSubmissionById(id: Int, newText: String){

        getSubmissionById(id)?.run{
            text = newText
        }

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SubmissionRepository> {
        override fun createFromParcel(parcel: Parcel): SubmissionRepository {
            return SubmissionRepository(parcel)
        }

        override fun newArray(size: Int): Array<SubmissionRepository?> {
            return arrayOfNulls(size)
        }
    }


}
