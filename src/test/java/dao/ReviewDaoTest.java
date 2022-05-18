package dao;

import models.Review;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import parameter_resolver.ReviewParameterResolver;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ReviewParameterResolver.class)
class ReviewDaoTest {
  private static Connection connection;
  private static ReviewDao reviewDao;

  @BeforeAll
  public static void beforeAll() {
    Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/thrive_test", "anna", "pol1234");
    reviewDao = new ReviewDao(sql2o);
    connection = sql2o.open();
  }

  @Test
  @DisplayName("Test that a review can be added")
  public void add_addsReview_true(Review review) {
    reviewDao.add(review);
    assertTrue(reviewDao.findAll().contains(review));
  }

  @Test
  @DisplayName("Test that a review's id is set upon insertion")
  public void add_setsReviewId_true(Review review) {
    int initialId = review.getId();
    reviewDao.add(review);
    assertNotEquals(initialId, review.getId());
  }

  @Test
  @DisplayName("Test that a list of reviews can be retrieved")
  public void findAll_retrievesReviewList_true(Review review) {
    reviewDao.add(review);
    Review[] reviews = {review};
    assertEquals(Arrays.asList(reviews), reviewDao.findAll());
  }

  @Test
  @DisplayName("Test that an empty list is retrieved if no reviews listed")
  public void findAll_retrievesEmptyListIfNoReviews_true() {
    assertEquals(0, reviewDao.findAll().size());
  }

  @Test
  @DisplayName("Test that a review's data can be updated")
  public void update_updatesReviewData_true(Review review) {
    reviewDao.add(review);
    review.setAppointmentid(2);
    review.setFeedback("Great service");
    reviewDao.update(review);
    assertEquals(review, reviewDao.findById(review.getId()));
  }

  @Test
  @DisplayName("Test that a review's data can be deleted")
  public void delete_deletesReviewData_false(Review review) {
    reviewDao.add(review);
    reviewDao.delete(review.getId());
    assertFalse(reviewDao.findAll().contains(review));
  }

  @Test
  @DisplayName("Test that all reviews' data can be deleted")
  public void delete_deletesAllReviewsData_true(Review review) {
    reviewDao.add(review);
    reviewDao.delete();
    assertEquals(0, reviewDao.findAll().size());
  }

  @AfterEach
  public void tearDown() {
    reviewDao.delete();
  }

  @AfterAll
  public static void afterAll() {
    connection.close();
  }
}