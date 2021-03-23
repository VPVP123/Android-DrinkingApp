package com.github.skosvall.nextlvl

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews


/**
 * Implementation of App Widget functionality.
 */
class AppWidget : AppWidgetProvider() {
    companion object{
        const val ACTION_DATA_UPDATED = "data_updated"
    }

    override fun onReceive(context: Context?, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == ACTION_DATA_UPDATED) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(ComponentName(context!!, javaClass))
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetStack)
        }
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {

    val intent = Intent(context, AppWidgetService::class.java)
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
    intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))

    val rv = RemoteViews(context.packageName, R.layout.app_widget)
    rv.setRemoteAdapter(R.id.widgetStack, intent)
    rv.setEmptyView(R.id.widgetStack, R.id.testEmpty)

    appWidgetManager.updateAppWidget(appWidgetId, rv)
}
