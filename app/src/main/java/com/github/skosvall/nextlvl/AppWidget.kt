package com.github.skosvall.nextlvl

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RemoteViews
import android.widget.TextView
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Implementation of App Widget functionality.
 */
class AppWidget : AppWidgetProvider() {

    lateinit var statements: MutableList<String>
    lateinit var statementsCopy: MutableList<String>

    val db = FirebaseFirestore.getInstance()

    private lateinit var loadingSpinner: ProgressBar
    lateinit var textView: TextView
    lateinit var nextButton: Button


    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        //for (appWidgetId in appWidgetIds) {
          //  updateAppWidget(context, appWidgetManager, appWidgetId)
        //}
        RemoteViews(context.packageName, R.layout.app_widget)

        textView = textView.findViewById<TextView>(R.id.widgetStatementTextView)
        nextButton = textView.findViewById<Button>(R.id.widgetNeverHaveIEverNextButton)

        val getStatements = db.collection("mobileGamesData").document("neverHaveIEver").collection("english").document("statements")

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

                            changeNeverHaveIEverStatement()
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("errorDB", "get failed with ", exception)
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
            changeNeverHaveIEverStatement()
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}


internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {


    val views = RemoteViews(context.packageName, R.layout.app_widget)
    appWidgetManager.updateAppWidget(appWidgetId, views)

    views.setTextViewText(R.id.widgetStatementTextView, "Hello everybody")
    val db = FirebaseFirestore.getInstance()




 /**
    val views = RemoteViews(context.packageName, R.layout.app_widget)
    appWidgetManager.updateAppWidget(appWidgetId, views)
    views.setTextViewText(R.id.cardGameTitle, context.getText(R.string.widget_title))

    **/
}

