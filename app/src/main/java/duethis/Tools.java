package duethis;

// Created by Oli on February-132018.

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import model.Assignment;
import model.Event;


class Tools {
    static void exceptionToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    static String getFormattedString(long value) {
        return String.format(Locale.CANADA, "%d", value);
    }

    static String getFormattedString(int value) {
        return String.format(Locale.CANADA, "%d", value);
    }

    static String getFormattedString(String value) {
        return String.format(Locale.CANADA, "%s", value);
    }

    static ArrayList<String> getAssignmentStringList(List<Assignment> assignments) {
        ArrayList<String> assignmentStrings = new ArrayList<>();
        for (Assignment a : assignments) {
            String toDo = "To Do";
            if (a.isIsCompleted()) {
                toDo = "Done";
            }

            assignmentStrings.add("Name: " + a.getName() + ", Course: " + a.getCourse() + " " + toDo);
        }

        return assignmentStrings;
    }

    static List<String> getEventStringList(List<Event> events) {
        List<String> eventStrings = new ArrayList<>();

        for (Event e : events) {
            eventStrings.add(e.getName());
        }

        return eventStrings;
    }
}
