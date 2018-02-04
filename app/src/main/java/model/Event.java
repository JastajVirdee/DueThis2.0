/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.27.0.3779.deff010 modeling language!*/


import java.sql.Date;

// line 20 "model.ump"
// line 66 "model.ump"
public class Event
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Event Attributes
  private String id;
  private String name;
  private Date date;
  private int duration;

  //Event Associations
  private Student student;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Event(String aId, String aName, Date aDate, int aDuration, Student aStudent)
  {
    id = aId;
    name = aName;
    date = aDate;
    duration = aDuration;
    boolean didAddStudent = setStudent(aStudent);
    if (!didAddStudent)
    {
      throw new RuntimeException("Unable to create event due to student");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(String aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setDuration(int aDuration)
  {
    boolean wasSet = false;
    duration = aDuration;
    wasSet = true;
    return wasSet;
  }

  public String getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }

  public Date getDate()
  {
    return date;
  }

  public int getDuration()
  {
    return duration;
  }
  /* Code from template association_GetOne */
  public Student getStudent()
  {
    return student;
  }
  /* Code from template association_SetOneToMany */
  public boolean setStudent(Student aStudent)
  {
    boolean wasSet = false;
    if (aStudent == null)
    {
      return wasSet;
    }

    Student existingStudent = student;
    student = aStudent;
    if (existingStudent != null && !existingStudent.equals(aStudent))
    {
      existingStudent.removeEvent(this);
    }
    student.addEvent(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Student placeholderStudent = student;
    this.student = null;
    if(placeholderStudent != null)
    {
      placeholderStudent.removeEvent(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "name" + ":" + getName()+ "," +
            "duration" + ":" + getDuration()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "student = "+(getStudent()!=null?Integer.toHexString(System.identityHashCode(getStudent())):"null");
  }
}