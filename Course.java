/*
 * Course contains course name, summary, when taught, list of students and
 * maximum limit of students which can enroll
 */
import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.GregorianCalendar;

@SuppressWarnings("serial")

public class Course implements Serializable, Comparable<Course>  {
	private int id;    // unique course identification number
	private String courseName;
	private String summary;
	private GregorianCalendar startDate;
	private GregorianCalendar endDate;
	private ArrayList<Student> studentList;
	private int studentLimit;

	// Constructor
	public Course() {
		id = 0;
		courseName = null;
		summary = null;
		studentLimit = 0;
		studentList = new ArrayList<Student>();
		startDate = new GregorianCalendar();
		endDate = new GregorianCalendar();
	}
	// Constructor
	public Course(int id, String courseName, String summary, int studentLimit,
				int startMonth, int startDay, int startYear, int endMonth, int endDay, int endYear) {
		this.id = id;
		this.courseName = courseName;
		this.summary = summary;
		this.studentLimit = studentLimit;
		startDate = new GregorianCalendar(startYear, startMonth, startDay);
		endDate = new GregorianCalendar(endYear, endMonth, endDay);

		studentList = new ArrayList<Student>();
	}

	public boolean equals(Course c) {
		// Each course id is unique, only id checked
		if(id == c.id) {
			return true;
		} else {
			return false;
		}
	}

	public void setCourseId(int id) {
		this.id = id;
	}

	public void setCourseName(String name) {
		courseName = name;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setDate( GregorianCalendar startDate,  GregorianCalendar endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public void setStudentLimit(int limit) {
		studentLimit = limit;
	}

	public ArrayList<Student> getStudentList() {
		return studentList;
	}

	public int getCourseId() {
		return id;
	}

	public String getCourseName() {
		return courseName;
	}

	public String getSummary() {
		return summary;
	}

	public  GregorianCalendar getStartDate() {
		return startDate;
	}

	public  GregorianCalendar getEndDate() {
		return endDate;
	}

	public int getAvailableSeats() {
		return studentLimit - studentList.size();
    }

	public boolean isSeatAvailable() {
	        if(studentList.size() >= studentLimit) {
	            return false;
	        } else {
	            return true;
	        }
    }

	public boolean register(Student student) {
		if(studentList.size() + 1 <= studentLimit && !(studentList.contains(student)))  {
			studentList.add(student);
			return true;
		} else {
			// course full;
			return false;
		}
	}


	public boolean unregister(Student student) {
		// Check student enrolled in course
		if(studentList.size() > 0 && isStudent(student)) {
			studentList.remove(student);
			return true;
		} else {
			// Not student enrolled in the course
			return false;
		}

	}

	// Check student enrolled
	public boolean isStudent(Student student) {
		for(Student s: studentList) {
			if(s.equals(student)) {
				return true;
			}
		}
		return false;
	}

	// Output course information to console
	// Course identification number, course dates, name, brief summary,
	// the enrollment limit, and the number of students already enrolled.
	public void viewCourseInfo() {
		System.out.println("Course ID: " + id);
		System.out.println("Course Dates: " +
				startDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
				+ " " + startDate.get(Calendar.DATE) + ", " + startDate.get(Calendar.YEAR) + " to "
				+ endDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
				+ " "  + endDate.get(Calendar.DATE) + ", " + endDate.get(Calendar.YEAR));
		System.out.println("Course Name: " + courseName);
		System.out.println("Course Summary: " + summary);
		System.out.println("Enrollment Limit " + studentLimit + "    Enrolled Students " + studentList.size());
	}

	// Output Course identification number, course dates, name
	public void viewCourseHeader() {
		System.out.println("Course ID: " + id);
		System.out.println("Course Dates: " +
				startDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
				+ " " + startDate.get(Calendar.DATE) + ", " + startDate.get(Calendar.YEAR) + " to "
				+ endDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
				+ " "  + endDate.get(Calendar.DATE) + ", " + endDate.get(Calendar.YEAR));
		System.out.println("Course Name: " + courseName);
	}

	public int compareTo(Course course) {
		return this.courseName.compareTo(course.getCourseName());
	}
}
