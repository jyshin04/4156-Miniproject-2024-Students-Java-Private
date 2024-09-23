package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

/**
 * Contains unit test for the RouteController class to validate  
 * its functionality, including methods and the constructor. 
 */
@SpringBootTest
@ContextConfiguration
public class RouteControllerUnitTests {

  @BeforeAll
  public static void setupRouteControllerTest() {
    testrouteController = new RouteController();
  }

  @Test
  public void indexTest() {
    String expected = 
            "Welcome, in order to make an API call direct your browser or Postman to an endpoint "
            + "\n\n This can be done using the following format: \n\n http:127.0.0"
            + ".1:8080/endpoint?arg=value";
    assertEquals(expected, testrouteController.index());
  }

  @Test
  public void retrieveDepartmentTest() {
    HashMap<String, Department> departmentMapping = 
            IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
    assertEquals(new ResponseEntity<>("Department Not Found", HttpStatus.NOT_FOUND),
            testrouteController.retrieveDepartment("COSM"));
    assertEquals(new ResponseEntity<>(departmentMapping.get("COMS").toString(),
            HttpStatus.OK), testrouteController.retrieveDepartment("COMS"));
  }

  @Test
  public void retrieveCourseTest() {
    assertEquals(new ResponseEntity<>("Course Not Found", HttpStatus.NOT_FOUND),
            testrouteController.retrieveCourse("COMS", 010));
    ResponseEntity<?> responseEntity =
            testrouteController. retrieveCourse("COMS", 3203);
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    String expectedBody = 
            "\nInstructor: Ansaf Salleb-Aouissi; Location: 301 URIS; Time: 10:10-11:25";
    assertEquals(expectedBody, responseEntity.getBody());
  }
  
  @Test
  public void retrieveCoursesTest() {
    ResponseEntity<?> responseEntityNotFound = testrouteController.retrieveCourses(9999);
    assertEquals(HttpStatus.NOT_FOUND, responseEntityNotFound.getStatusCode());
    assertEquals("No courses with the given course code found.", responseEntityNotFound.getBody());

    ResponseEntity<?> responseEntityFound = testrouteController.retrieveCourses(1001);
    assertEquals(HttpStatus.OK, responseEntityFound.getStatusCode());

    HashMap<String, Department> departmentMapping;
    departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
    ArrayList<String> expectedCourses = new ArrayList<>();

    for (Department dept : departmentMapping.values()) {
      HashMap<String, Course> coursesMapping = dept.getCourseSelection();
      String courseCodeStr = Integer.toString(1001);
      if (coursesMapping.containsKey(courseCodeStr)) {
        Course course = coursesMapping.get(courseCodeStr);
        expectedCourses.add(course.toString());
      }
    }

    String expectedBody = expectedCourses.toString();
    assertEquals(expectedBody, responseEntityFound.getBody());
  }

  @Test
  public void isCourseFullTest() {
    assertEquals(HttpStatus.NOT_FOUND,
            testrouteController.isCourseFull("COSM", 3203).getStatusCode());
    assertEquals(HttpStatus.OK, 
            testrouteController.isCourseFull("COMS", 3203).getStatusCode());
  }

  @Test
  public void identifyDeptChairTest() {
    assertEquals(HttpStatus.NOT_FOUND,
              testrouteController.identifyDeptChair("COSM").getStatusCode());
    assertEquals(HttpStatus.OK,
              testrouteController.identifyDeptChair("COMS").getStatusCode());
  }

  @Test
  public void findCourseLocationTest() {
    assertEquals(HttpStatus.NOT_FOUND,
              testrouteController.findCourseLocation("COSM", 3203).getStatusCode());
    assertEquals(HttpStatus.OK,
              testrouteController.findCourseLocation("COMS", 3203).getStatusCode());
    assertEquals("301 URIS is where the course is located.",
              testrouteController.findCourseLocation("COMS", 3203).getBody());
  }

  @Test
  public void findCourseInstructorTest() {
    assertEquals(HttpStatus.NOT_FOUND,
            testrouteController.findCourseInstructor("COSM", 3203).getStatusCode());
    assertEquals(HttpStatus.OK,
            testrouteController.findCourseInstructor("COMS", 3203).getStatusCode());
    assertEquals("Ansaf Salleb-Aouissi is the instructor for the course.",
            testrouteController.findCourseInstructor("COMS", 3203).getBody());
  }

  @Test
  public void findCourseTimeTest() {
    assertEquals(HttpStatus.NOT_FOUND,
              testrouteController.findCourseTime("COSM", 3203).getStatusCode());
    assertEquals(HttpStatus.OK,
              testrouteController.findCourseTime("COMS", 3203).getStatusCode());
    assertEquals("The course meets at: 10:10-11:25.",
              testrouteController.findCourseTime("COMS", 3203).getBody());
  }

  @Test
  public void addMajorToDeptTest() {
    assertEquals(HttpStatus.NOT_FOUND,
              testrouteController.addMajorToDept("COSM").getStatusCode());
    assertEquals(HttpStatus.OK,
              testrouteController.addMajorToDept("COMS").getStatusCode());
  }

  @Test
  public void removeMajorFromDeptTest() {
    assertEquals(HttpStatus.NOT_FOUND,
              testrouteController.removeMajorFromDept("COSM").getStatusCode());
    assertEquals(HttpStatus.OK,
              testrouteController.removeMajorFromDept("COMS").getStatusCode());
  }

  @Test
  public void enrollStudentInCourseTest() {
    ResponseEntity<?> responseEntityNotFound;
    responseEntityNotFound = testrouteController.enrollStudentInCourse("COSM", 9999);
    assertEquals(HttpStatus.NOT_FOUND, responseEntityNotFound.getStatusCode());
    assertEquals("Course Not Found", responseEntityNotFound.getBody());

    ResponseEntity<?> responseEntityEnrolled;
    responseEntityEnrolled = testrouteController.enrollStudentInCourse("COMS", 3203);
    assertEquals(HttpStatus.OK, responseEntityEnrolled.getStatusCode());
    assertEquals("Student has been enrolled.", responseEntityEnrolled.getBody());

    HashMap<String, Department> departmentMapping;
    departmentMapping = IndividualProjectApplication.myFileDatabase.getDepartmentMapping();
    HashMap<String, Course> coursesMapping = departmentMapping.get("COMS").getCourseSelection();
    Course course = coursesMapping.get("3203");
    course.setEnrolledStudentCount(250);
    ResponseEntity<?> responseEntityFull = testrouteController.enrollStudentInCourse("COMS", 3203);
    assertEquals(HttpStatus.BAD_REQUEST, responseEntityFull.getStatusCode());
    assertEquals("Cannot enroll student.", responseEntityFull.getBody());
    course.setEnrolledStudentCount(215);
  }

  @Test
  public void dropStudentTest() {
    assertEquals(HttpStatus.NOT_FOUND,
              testrouteController.dropStudent("COSM", 3203).getStatusCode());
    assertEquals(HttpStatus.OK,
              testrouteController.dropStudent("COMS", 3203).getStatusCode());
    assertEquals("Student has been dropped.",
              testrouteController.dropStudent("COMS", 3203).getBody());
  }

  @Test
  public void setEnrollmentCountTest() {
    assertEquals(HttpStatus.NOT_FOUND,
              testrouteController.setEnrollmentCount("COSM", 3203, 100).getStatusCode());
    assertEquals(HttpStatus.OK,
              testrouteController.setEnrollmentCount("COMS", 3203, 10).getStatusCode());
    assertEquals("Attributed was updated successfully.",
              testrouteController.setEnrollmentCount("COMS", 3203, 50).getBody());
  }

  /** The test RouteController instance used for testing. */
  public static RouteController testrouteController;
}

