package duethis;

// Created by Oli on February-132018.

import android.content.Context;
import android.widget.Toast;


class Tools {
    static void exceptionToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}

