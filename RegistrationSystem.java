import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@SuppressWarnings({"serial", "unchecked"})

public class RegistrationSystem implements Serializable {

    private static final long serialVersionUID = 1L;

    //For User Input
    private File studentRegistryList;
    private Scanner fileScanner;
    private Scanner userInputScanner;


    private int maximumStudentCapacity = 30;
    private int courseMaxSize = 10;
    private int courseCurrentSize = 0;
    private CourseList courseList;  //may or may not be right
    private ArrayList<Student> studentList;

    boolean uniqueSsn; //validation of User

    public static void main(String[] args) {

        RegistrationSystem builder = new RegistrationSystem();
        builder.go();

    } // end of static void

    public void go() {


        //Student Registration Begins here
        try {
          //Set up Scanner object using system.in
          userInputScanner = new Scanner(System.in);

    			File checkExistence = new File("studentRegistryList.ser");
    			if( checkExistence.exists()) {
    			  try
    					{
    					  FileInputStream inputFileStream = new FileInputStream("studentRegistryList.ser");
    					  ObjectInputStream objectInputStream = new ObjectInputStream(inputFileStream);
    					  studentList = (ArrayList<Student>) objectInputStream.readObject();
    					  objectInputStream.close();
    					  inputFileStream.close();

    					}
    					catch(ClassNotFoundException e)
    					{
    					  e.printStackTrace();
    					}
    					catch(IOException i)
    					{
    					  i.printStackTrace();
    					}
    			}

          System.out.println("**************Welcome to the University of Irvine Online Registration System!**************");
          System.out.println("To create a new user press 1.");
          System.out.println("To begin course registration, press 2.");
          System.out.println("To view student directory, press 3.");
          System.out.println("To exit the program, press 4.");
          String numberString = userInputScanner.next();
          while(numberString.matches(".*[^1-3].*")) {
        	  System.out.println("*****Error, reenter menu choice*****");
        	  numberString = userInputScanner.next();
          }
          int number = Integer.parseInt(numberString);


        //  int number = userInputScanner.nextInt();
          if (number == 1) {
            userRegistration();
          }
          else if (number == 2) {
            courseRegistration();
          }
          else if (number == 3) {
            System.out.println("************************************Student Directory**************************************");
            viewStudentDirectory();
            go();
          }
          else if (number == 4) {
              //close program
          } else {
            System.out.println("Invalid input. Please choose again.");
            go();
          }

          userInputScanner.close();
        } catch (Exception e) {
          e.printStackTrace();
        }



    } //end of go()

    //*********************************To create a new User*************************************************
    public void userRegistration() {

        //Accepts user's input and validates information
        try {
          userInputScanner = new Scanner(System.in);

          System.out.println("Please enter your first name: ");
          String userFirstName = userInputScanner.next();
          while(userFirstName.matches(".*[^a-zA-Z].*")) {
        	  System.out.println("*****Error, please do not use numbers or special characters.*****");
              System.out.println("Please enter your first name: ");
              userFirstName = userInputScanner.next();
          }

          System.out.println("Please enter your last name: ");
          String userLastName = userInputScanner.next();
          while(userLastName.matches(".*[^a-zA-Z].*")) {
        	  System.out.println("*****Error, please do not use numbers or special characters.*****");
              System.out.println("Please enter your last name: ");
              userLastName = userInputScanner.next();
          }

          System.out.println("Please enter your age: ");
          String userAgeString = userInputScanner.next();
          while(userAgeString.matches(".*[^0-9].*")) {
              System.out.println("*******Error, this is not possible.*******");
              System.out.println("Please enter your age: ");
              userAgeString = userInputScanner.next();
          }
          int userAge = Integer.parseInt(userAgeString);

          System.out.println("Please enter your gender (M/F): ");
          System.out.println("(case sensitive)");
          char userGender = userInputScanner.next().charAt(0);
          System.out.println("Gender: " + userGender);
          while(userGender != 'M' && userGender != 'F') {
              System.out.println("*****Error, please enter M or F only*****");
              System.out.println("Please enter your gender (M/F): ");
              userGender = userInputScanner.next().charAt(0);
              System.out.println("userGender " + userGender);
          }

          System.out.println("Please enter your SSN: ");
          String userSsn = userInputScanner.next();
          boolean uniqueSsn = true;

          for (Student student : studentList) {
                if (student.getStudentSsn().equals(userSsn)) {
                    uniqueSsn = false;
                    System.out.println("---A user with that SSN already exists---");
                }
              }

          while(userSsn.length() != 9 || uniqueSsn == false) {
              System.out.println("*****Error, please enter a valid 9-digit SSN*****");
              System.out.println("Please enter your SSN: ");
              uniqueSsn = true;
              userSsn = userInputScanner.next();
          }
          int checkSsn = Integer.parseInt(userSsn);

          System.out.println("Please enter a 5-digit student ID number:");
          String studentId = userInputScanner.next();
          boolean uniqueId = true;

          for (Student student : studentList) {
                if (student.getStudentId().equals(studentId)) {
                    uniqueId = false;
                    System.out.println("---A user with that ID already exists---");
                }
              }
          while (studentId.length() != 5 || uniqueId == false) {
              System.out.println("*****Error, please enter a valid 5-digit ID*****");
              System.out.println("Please enter a 5-digit student ID number:");
              uniqueId = true;
              studentId = userInputScanner.next();
          }
          int checkId = Integer.parseInt(studentId);


          //Create a new User with User's input and add to studentList
          studentList.add(new Student(userFirstName, userLastName, userAge, userGender, userSsn, studentId));

          //save user's input to file
          FileOutputStream fileOut = new FileOutputStream("studentRegistryList.ser"); // creates a serial file in ouput stream
          ObjectOutputStream out = new ObjectOutputStream(fileOut); // routes an object into the output stream
            out.writeObject(studentList); // write specified object(s) into file
          out.close();
          fileOut.close();

          //use equals() to compare strings
          System.out.println("Thank you " + userFirstName + " " + userLastName);
          System.out.println("You have now been added to the database.");

          //Return screen to login page
          courseRegistration();

          userInputScanner.close();
        } catch (Exception e) {
            System.out.println("Error. Invalid Input. Please start over.");
            userRegistration();
        }
    }

    //*********************************When viewing the Available Course List*********************************
    public void miniMenu1() {
      try {
          userInputScanner = new Scanner(System.in);
          //Prompt User to choose how to view courseList
          System.out.println("To view list in alphabetical order, press 1.");
          System.out.println("To view list by course ID number, press 2.");
          System.out.println("To return to the main menu, press 3.");
            
          String numberString = userInputScanner.next();
          while(numberString.matches(".*[^1-3].*")) {
        	  System.out.println("*****Error, reenter menu choice*****");
        	  numberString = userInputScanner.next();
          }
          int number = Integer.parseInt(numberString);

          if (number == 1 ) {
              System.out.println("**************Sorted Alphabetically**************");
              courseList.viewAvailableCourseListAlphabetically();
              System.out.println("*************************************************");
              miniMenu1();
          }
          else if (number == 2) {
              System.out.println("**************Sorted by Course ID**************");
              courseList.viewAvailableCourseList();
              System.out.println("***********************************************");
              miniMenu1();
          }
          else if (number == 3) {
              courseRegistration();
          }
          else {
            System.out.println("**************Invalid Input. Please try again.**************");
            miniMenu1();
          }
          userInputScanner.close();
      } catch (Exception e) {
            e.printStackTrace();
      }
    }

    //*********************************When viewing the courseLists*********************************
    public void miniMenu2() {
      try {
          userInputScanner = new Scanner(System.in);
          //Prompt User to choose how to view courseList
          System.out.println("To view list in alphabetical order, press 1.");
          System.out.println("To view list by course ID number, press 2.");
          System.out.println("To return to the main menu, press 3.");
       
          String numberString = userInputScanner.next();
          while(numberString.matches(".*[^1-3].*")) {
        	  System.out.println("*****Error, reenter menu choice*****");
        	  numberString = userInputScanner.next();
          }
          int number = Integer.parseInt(numberString);

          if (number == 1 ) {
              System.out.println("**************Sorted Alphabetically**************");
              courseList.viewCourseListAlphabetically();
              System.out.println("*************************************************");
              miniMenu2();
          }
          else if (number == 2) {
              System.out.println("**************Sorted by Course ID****************");
              courseList.viewCourseList();
              System.out.println("*************************************************");
              miniMenu2();
          }
          else if (number == 3) {
              courseRegistration();
          }
          else {
              System.out.println("**************Invalid Input.*********************");
              System.out.println("**************Please try again.******************");
              miniMenu2();
          }
          userInputScanner.close();
      } catch (Exception e) {
            e.printStackTrace();
      }
    }

    //*********************************Course registration option*********************************
    public void courseRegistration() {

            try {
              userInputScanner = new Scanner(System.in);
              System.out.println("*******************Main Menu*********************");
              System.out.println("Please choose from the following options.");
              System.out.println("To return to the previous menu, press 1.");
              System.out.println("To view available courses, press 2.");
              System.out.println("To view all courses, press 3.");
              System.out.println("To view registered courses, press 4.");
              System.out.println("To register for a course, press 5.");
              System.out.println("To unregister from a course, press 6.");
              System.out.println("To save changes, press 7.");
              System.out.println("To save and exit, press 8.");
              
              String numberString = userInputScanner.next();
              while(numberString.matches(".*[^1-8].*")) {
            	  System.out.println("*****Error, reenter menu choice*****");
            	  numberString = userInputScanner.next();
              }         
              int number = Integer.parseInt(numberString);

              if (number == 1) {
                  go();
              } else if (number == 2) {
                  System.out.println("**************Available Courses***************");
                  courseList.viewAvailableCourseList(); // displays courses w/ seats available
                  System.out.println("**********************************************");
              	  miniMenu1();
              } else if (number == 3) {
                  //show all courses.
                  System.out.println("*****************All Courses******************");
                  courseList.viewCourseList();
                  System.out.println("**********************************************");
                  miniMenu2();
              } else if (number == 4) {
            	  // NOTE: should be ssn or student id
                  System.out.println("Enter Social Security Number");
                  String ssn = userInputScanner.next();


                  // Get student
                  Student student = null;
                  for(Student s: studentList) {
                      if(s.getStudentSsn().equals(ssn)) {
                          student = s;
                      }
                  }
                  if(student != null) {
                    System.out.println("***************Registered Courses****************");
                	  student.viewCourseList();
                    System.out.println("*************************************************");
                  } else {
                      // Failed to find student in studentList
                      System.out.println("**************Could not find student enrolled. Try again or register a new account.**************");
                  }
                  courseRegistration();
              } else if (number == 5) {
                  System.out.println("Enter course id");
                  String courseIdString = userInputScanner.next();
                  while((courseIdString.matches(".*[^0-9].*")) || 
                		  (!courseList.isCourse(Integer.parseInt(courseIdString)))) {
                	  System.out.println("*****Error, invalid course id*****");
                	  System.out.println("Enter course id");
                	  courseIdString = userInputScanner.next();
                  }
                  int courseId = Integer.parseInt(courseIdString);

                  // NOTE: should be ssn or student id
                  System.out.println("Enter Social Security Number");
                  String ssn = userInputScanner.next();
                  // Get student
                  Student student = null;
                  for(Student s: studentList) {
                      if(s.getStudentSsn().equals(ssn)) {
                          student = s;
                      }
                  }
                  if(student != null) {
                	  // Check student is not already registered for course
                	  Course registerCourse = courseList.getCourse(courseId);
                	  if(!student.isRegistered(registerCourse) && !registerCourse.isStudent(student)) {
                          // Register for course
                          boolean enrollmentStatus = courseList.registerForCourse(courseId, student);
                          if(enrollmentStatus == false) {
                              System.out.println("**************Student course registration failed - Class is full**************");
                              courseRegistration();
                          } else {
                              System.out.println("**************Registered for course**************");
                              // Add course to student's courseList
                              student.registerForCourse(registerCourse);
                              courseRegistration();
                          }
                	  } else {
                		  System.out.println("**************Student already registered for course id=" + courseId + ".**************");
                          courseRegistration();
                      }
                  } else {
                      // Failed to find student in studentList
                      System.out.println("**************Could not find student enrolled. Try again or quit and register.**************");
                      courseRegistration();
                  }
              }
              else if (number == 7 || number == 8) {
				            FileOutputStream fileOut = new FileOutputStream("studentRegistryList.ser"); // creates a serial file in ouput stream
				            ObjectOutputStream out = new ObjectOutputStream(fileOut); // routes an object into the output stream
				              out.writeObject(studentList); // write specified object(s) into file
				            out.close();
				            fileOut.close();

				            //use equals() to compare strings
          		System.out.println("**************Information saved to the database.**************");
          		if (number==7) {
				  courseRegistration();
					}
			  }


              else if (number == 6) {
                  System.out.println("Enter course id");
                  
                  String courseIdString = userInputScanner.next();
                  while((courseIdString.matches(".*[^0-9].*")) || 
                		  (!courseList.isCourse(Integer.parseInt(courseIdString)))) {
                	  System.out.println("*****Error, invalid course id*****");
                	  System.out.println("Enter course id");
                	  courseIdString = userInputScanner.next();
                  }
                  int courseId = Integer.parseInt(courseIdString);
  
                  // NOTE: should be ssn or student id
                  System.out.println("Enter Social Security Number");
                  String ssn = userInputScanner.next();
                  // Get student from social security number
                  Student student = null;
                  for(Student s: studentList) {
                      if(s.getStudentSsn().equals(ssn)) {
                          student = s;
                      }
                  }
                  if(student != null) {
                	  // Check student is registered for course
                	  Course unregisterCourse = courseList.getCourse(courseId);
                	  if(student.isRegistered(unregisterCourse) && unregisterCourse.isStudent(student)) {
                          // Unregister for course
                          boolean enrollmentStatus = courseList.unregisterForCourse(courseId, student);
                          if(enrollmentStatus == false) {
                              System.out.println("**************Course unregistration failed**************");
                              courseRegistration();
                          } else {
                              System.out.println("**************Unregistered for course**************");
                              // Remove course to student's courseList
                              student.unregisterForCourse(courseList.getCourse(courseId));
                              courseRegistration();
                          }
                      } else {
                          System.out.println("**************Course unregistration failed, student not registered for course id=" + courseId + "**************");
                          courseRegistration();
                      }

                  }




                  else {
                      // Failed to find student in studentList
                      System.out.println("**************Could not find student enrolled. Please try again.**************");
                      courseRegistration();
                  }
              } else {
                  //Return invalid input message and try again.
                  System.out.println("**************Invalid input. Please try again.**************");
                  courseRegistration();
              }

              userInputScanner.close();
            } catch (Exception e) {
                  e.printStackTrace();
            }

        } //end of courseRegistration()


        //Changing course size
        public int getCourseSize() {
            return courseCurrentSize;
        }

        public RegistrationSystem() {
        	System.out.println("RegistrationSystem");
            courseList = new CourseList();
            // TODO: remove the following call, temporary for testing
            //courseList.viewCourseList();
            studentList = new ArrayList<Student>();
        }

        public void viewStudentDirectory() {
            for (Student student : studentList){

                System.out.println(student.getLastName() + ", " + student.getFirstName());

            }
        }

} //end of Class