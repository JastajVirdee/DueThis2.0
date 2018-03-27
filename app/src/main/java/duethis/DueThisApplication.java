package duethis;

// Created by Oli on February-132018.

import android.app.Application;

import controller.AccountController;
import controller.DueThisController;
import model.Student;

public class DueThisApplication extends Application {

    public static Student student;
    public static DueThisController controller;
    public static AccountController controllerAccount;

    public DueThisApplication() {
        super();
        // Global Application used to store students, events, assignments
        model.Application.getInstance();

        // Controller which manipulates domain model objects.
        controller = new DueThisController();

        // Controller which creates and deletes accounts
        controllerAccount = new AccountController();
    }
}
