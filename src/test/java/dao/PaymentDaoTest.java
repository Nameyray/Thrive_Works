package dao;

import models.Payment;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import parameter_resolver.PatientParameterResolver;
import parameter_resolver.PaymentParameterResolver;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(PaymentParameterResolver.class)
class PaymentDaoTest {
  private static Connection connection;
  private static PaymentDao paymentDao;

  @BeforeAll
  public static void beforeAll() {
    Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/thrive_test", "anna", "pol1234");
    paymentDao = new PaymentDao(sql2o);
    connection = sql2o.open();
  }

  @Test
  @DisplayName("Test that a payment can be added")
  public void add_addsPayment_true(Payment payment) {
    paymentDao.add(payment);
    assertTrue(paymentDao.findAll().contains(payment));
  }

  @Test
  @DisplayName("Test that a payment's id is set upon insertion")
  public void add_setsPaymentId_true(Payment payment) {
    int initialId = payment.getId();
    paymentDao.add(payment);
    assertNotEquals(initialId, payment.getId());
  }

  @Test
  @DisplayName("Test that a list of payments can be retrieved")
  public void findAll_retrievesPaymentList_true(Payment payment) {
    paymentDao.add(payment);
    Payment[] payments = {payment};
    assertEquals(Arrays.asList(payments), paymentDao.findAll());
  }

  @Test
  @DisplayName("Test that an empty list is retrieved if no payments listed")
  public void findAll_retrievesEmptyListIfNoPayments_true() {
    assertEquals(0, paymentDao.findAll().size());
  }

  @Test
  @DisplayName("Test that payment's data can be updated")
  public void update_updatesPaymentData_true(Payment payment) {
    paymentDao.add(payment);
    payment.setAppointmentid(2);
    payment.setAmount(3000.00);
    paymentDao.update(payment);
    assertEquals(payment, paymentDao.findById(payment.getId()));
  }

  @Test
  @DisplayName("Test that a payment's data can be deleted")
  public void delete_deletesPaymentData_false(Payment payment) {
    paymentDao.add(payment);
    paymentDao.delete(payment.getId());
    assertFalse(paymentDao.findAll().contains(payment));
  }

  @Test
  @DisplayName("Test that all payments' data can be deleted")
  public void delete_deletesAllPaymentsData_true(Payment payment) {
    paymentDao.add(payment);
    paymentDao.delete();
    assertEquals(0, paymentDao.findAll().size());
  }

  @AfterEach
  public void tearDown() {
    paymentDao.delete();
  }

  @AfterAll
  public static void afterAll() {
    connection.close();
  }
}