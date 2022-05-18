package dao;

import models.Appointment;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import parameter_resolver.AppointmentParameterResolver;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(AppointmentParameterResolver.class)
class AppointmentDaoTest {
  private static Connection connection;
  private static AppointmentDao appointmentDao;

  @BeforeAll
  public static void beforeAll() {
    Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/thrive_test", "anna", "pol1234");
    appointmentDao = new AppointmentDao(sql2o);
    connection = sql2o.open();
  }

  @Test
  @DisplayName("Test that an appointment can be added")
  public void add_addsAppointment_true(Appointment appointment) {
    appointmentDao.add(appointment);
    assertTrue(appointmentDao.findAll().contains(appointment));
  }

  @Test
  @DisplayName("Test that an appointment's id is set upon insertion")
  public void add_setsAppointmentId_true(Appointment appointment) {
    int initialId = appointment.getId();
    appointmentDao.add(appointment);
    assertNotEquals(initialId, appointment.getId());
  }

  @Test
  @DisplayName("Test that a list of appointments can be retrieved")
  public void findAll_retrievesAppointmentList_true(Appointment appointment) {
    appointmentDao.add(appointment);
    Appointment[] appointments = {appointment};
    assertEquals(Arrays.asList(appointments), appointmentDao.findAll());
  }

  @Test
  @DisplayName("Test that an empty list is retrieved if no appointments listed")
  public void findAll_retrievesEmptyListIfNoAppointments_true() {
    assertEquals(0, appointmentDao.findAll().size());
  }

  @Test
  @DisplayName("Test that appointment's data can be updated")
  public void update_updatesAppointmentData_true(Appointment appointment) {
    appointmentDao.add(appointment);
    appointment.setStarttime(new Timestamp(new Date().getTime()));
    appointment.setEndtime(new Timestamp(new Date().getTime()));
    appointment.setPatient(2);
    appointment.setTherapist(2);
    appointment.setLink("https://meet.google.com/meet1");
    appointmentDao.update(appointment);
    assertEquals(appointment, appointmentDao.findById(appointment.getId()));
  }

  @Test
  @DisplayName("Test that an appointment's data can be deleted")
  public void delete_deletesAppointmentData_false(Appointment appointment) {
    appointmentDao.add(appointment);
    appointmentDao.delete(appointment.getId());
    assertFalse(appointmentDao.findAll().contains(appointment));
  }

  @Test
  @DisplayName("Test that all appointments' data can be deleted")
  public void delete_deletesAllAppointmentsData_true(Appointment appointment) {
    appointmentDao.add(appointment);
    appointmentDao.delete();
    assertEquals(0, appointmentDao.findAll().size());
  }


  @AfterEach
  public void tearDown() {
    appointmentDao.delete();
  }

  @AfterAll
  public static void afterAll() {
    connection.close();
  }
}