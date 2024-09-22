package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Contains unit test for the Course class to validate  
 * its functionality, including methods and the constructor. 
 */
@SpringBootTest
@ContextConfiguration
public class CourseUnitTests {

  @BeforeAll
  public static void setupCourseForTesting() {
    testCourse = new Course("Griffin Newbold", "417 IAB", "11:40-12:55", 250);
  }

  @Test
  public void toStringTest() {
    String expectedResult = "\nInstructor: Griffin Newbold; Location: 417 IAB; Time: 11:40-12:55";
    assertEquals(expectedResult, testCourse.toString());
  }

  @Test
  public void enrollStudentTest1() {
    testCourse.setEnrolledStudentCount(200);
    assertTrue(testCourse.enrollStudent());
  }

  @Test
  public void enrollStudentTest2() {
    testCourse.setEnrolledStudentCount(250);
    assertFalse(testCourse.enrollStudent());
  }

  @Test
  public void dropStudentTest1() {
    testCourse.setEnrolledStudentCount(200);
    assertTrue(testCourse.dropStudent());
  }

  @Test
  public void dropStudentTest2() {
    testCourse.setEnrolledStudentCount(0);
    assertFalse(testCourse.dropStudent());
  }

  @Test
  public void getCourseLocationTest() {
    assertEquals("417 IAB", testCourse.getCourseLocation());
  }

  @Test
  public void getInstructorNameTest() {
    assertEquals("Griffin Newbold", testCourse.getInstructorName());
  }

  @Test
  public void getCourseTimeSlotTest() {
    assertEquals("11:40-12:55", testCourse.getCourseTimeSlot());
  }

  @Test
  public void reassignInstructorTest() {
    String newInstructorName = "Daniel Bauer";
    testCourse.reassignInstructor("Daniel Bauer");
    assertEquals(newInstructorName, testCourse.getInstructorName());
    testCourse.reassignInstructor("Griffin Newbold");
  }

  @Test
  public void reassignLocationTest() {
    String newLocation = "313 Pupin";
    testCourse.reassignLocation(newLocation);
    assertEquals(newLocation, testCourse.getCourseLocation());
    testCourse.reassignLocation("417 IAB");
  }

  @Test
  public void reassignTimeTest() {
    String newTime = "10:10-13:10";
    testCourse.reassignTime(newTime);
    assertEquals(newTime, testCourse.getCourseTimeSlot());
    testCourse.reassignTime("11:40-12:55");
  }

  @Test
  public void isCourseFullTest1() {
    testCourse.setEnrolledStudentCount(200);
    assertFalse(testCourse.isCourseFull());
  }

  @Test
  public void isCourseFullTest2() {
    testCourse.setEnrolledStudentCount(250);
    assertTrue(testCourse.isCourseFull());
  }

  /** The test course instance used for testing. */
  public static Course testCourse;
}

