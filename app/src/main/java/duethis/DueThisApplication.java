package duethis;

/**
 * Created by Oli on February-132018.
 */
import android.app.Application;

import java.util.UUID;

import controller.DueThisController;
import model.ExperiencedStudent;
import model.Student;
import model.StudentRole;

public class DueThisApplication extends Application {

    public static Student student;
    public static DueThisController controller;

    public DueThisApplication(){
        super();
        //Global Student
        String uuid = UUID.randomUUID().toString();
        student = new Student(uuid, "Tim");

        StudentRole role = new ExperiencedStudent(student,
                                0,
                                0,
                                0,
                                0,
                                0,
                                0,
                                0);

        student.addStudentRole(role);
        controller = new DueThisController();
    }



}
