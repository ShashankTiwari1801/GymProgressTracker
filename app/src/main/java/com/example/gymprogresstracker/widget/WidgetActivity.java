package com.example.gymprogresstracker.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.provider.ContactsContract;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import com.example.gymprogresstracker.DatabaseManager;
import com.example.gymprogresstracker.R;
import com.example.gymprogresstracker.ui.ExerciseViewerCard;
import com.example.gymprogresstracker.util.DayUtil;
import com.example.gymprogresstracker.util.Exercise;
import com.example.gymprogresstracker.util.ExerciseDirectoryManager;
import com.example.gymprogresstracker.util.JSONHelper;
import com.example.gymprogresstracker.widget.ui.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link WidgetActivityConfigureActivity WidgetActivityConfigureActivity}
 */
public class WidgetActivity extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Log.e("E", "WIDGET ipdateAppWIdfet");
        CharSequence widgetText = WidgetActivityConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_activity);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        List<String> names = fetchData(context);
        Log.e("E", names.toString());
        for(String name: names){
           RemoteViews temp = new RemoteViews(context.getPackageName(), R.layout.widget_card);
           temp.setTextViewText(R.id.TV_WIDGET_NAME, name);
           views.addView(R.id.LL_WIDGET_CONTENTS, temp);
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    public static List<String> fetchData(Context context){
        DayUtil dayUtil = new DayUtil();
        DatabaseManager databaseManager = new DatabaseManager(context);
        List<Integer> ids = databaseManager.getRecord(dayUtil.getTodayDayID());
        ExerciseDirectoryManager exerciseDirectoryManager = new ExerciseDirectoryManager(new JSONHelper(context));
        List<String> names = new ArrayList<>();
        for(int i: ids){
            names.add(exerciseDirectoryManager.getExerciseName(i));
        }
        return names;
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.e("E", "onUpdate");
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            WidgetActivityConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}