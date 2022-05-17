package models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import parameter_resolver.TherapistParameterResolver;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TherapistParameterResolver.class)
class TherapistTest {
  @Test
  @DisplayName("Test that a Therapist class instance instantiates correctly")
  public void newTherapist_instantiatesCorrectly_true(Therapist therapist) {
    assertNotNull(therapist);
  }

  @Test
  @DisplayName("Test that a Therapist class instance instantiates with specialization")
  public void newTherapist_instantiatesWithSpecialization(Therapist therapist) {
    assertEquals("Anxiety disorders", therapist.getSpecialization());
  }

  @Test
  @DisplayName("Test that a therapist's rating is set as specified")
  public void setRatings_setsTherapistRating_true(Therapist therapist) {
    therapist.setRatings(4);
    assertEquals(4, therapist.getRatings());
  }

  @Test
  @DisplayName("Test that a therapist's vetting status is set as specified")
  public void setIs_vetted_setsTherapistVettedStatus_true(Therapist therapist) {
    therapist.setIs_vetted(1);
    assertEquals(1, therapist.getIs_vetted());
  }

  @Test
  public void setSpecialization_setsTherapistsSpecialization_true(Therapist therapist) {
    therapist.setSpecialization("Anxiety disorders, Stress, Depression");
    assertEquals("Anxiety disorders, Stress, Depression", therapist.getSpecialization());
  }
}