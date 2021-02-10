package com.github.skosvall.nextlvl


val submissionRepository = SubmissionRepository().apply {
    // Let's add two initial ToDos.
    addSubmission(
        "Give the cat a fish and the dog a cat."
    )
    addSubmission(
        "Take a walk and listen to music."
    )
}


class SubmissionRepository{

    private val submissions = mutableListOf<Submission>()

    fun addSubmission(text: String): Int{
        val id = when {
            submissions.count() == 0 -> 1
            else -> submissions.last().id+1
        }
        submissions.add(
            Submission(
            id,
            text
        )
        )
        return id
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

}
