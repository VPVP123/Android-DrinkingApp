package com.github.skosvall.nextlvl

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RemoteViews
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore


/**
 * Implementation of App Widget functionality.
 */
class AppWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            val intent = Intent(context, AppWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)

            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            val rv = RemoteViews(context.packageName, R.layout.app_widget)
            rv.setRemoteAdapter(R.id.widgetStack, intent)
            //rv.setEmptyView(R.id.widgetStack, R.id.testEmpty)
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.layout.app_widget)
            appWidgetManager.updateAppWidget(appWidgetId, rv);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    override fun onEnabled(context: Context) {
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

