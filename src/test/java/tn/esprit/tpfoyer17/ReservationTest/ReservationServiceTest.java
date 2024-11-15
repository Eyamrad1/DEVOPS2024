package tn.esprit.tpfoyer17.ReservationTest;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer17.entities.Reservation;
import tn.esprit.tpfoyer17.repositories.ReservationRepository;
import tn.esprit.tpfoyer17.services.impementations.ReservationService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
/*@TestMethodOrder(MethodOrderer.OrderAnnotation.class)*/
public class ReservationServiceTest {/*

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    // Méthode utilitaire pour créer une réservation
    private Reservation createReservation(boolean estValide) {
        return Reservation.builder()
                .idReservation("TEST-" + System.currentTimeMillis())
                .anneeUniversitaire(LocalDate.now())
                .estValide(estValide)
                .build();
    }

    @Test
    @Order(1)
    void testRetrieveAllReservations() {
        // Arrange
        Reservation reservation1 = createReservation(true);
        Reservation reservation2 = createReservation(false);
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        // Act
        List<Reservation> result = reservationService.retrieveAllReservation();

        // Assert
        assertNotNull(result, "The result should not be null");


        // Clean up
        reservationRepository.deleteAll(List.of(reservation1, reservation2));
    }



    @Test
    @Order(2)
    void testUpdateReservation() {
        // Arrange
        Reservation reservation = createReservation(true);
        Reservation savedReservation = reservationRepository.save(reservation);

        // Act
        savedReservation.setEstValide(false);
        Reservation result = reservationService.updateReservation(savedReservation);

        // Assert
        assertNotNull(result, "The updated reservation should not be null");
        assertFalse(result.isEstValide(), "The reservation should be marked as not valid");

        // Clean up
        reservationRepository.delete(result);
    }



    @Test
    @Order(3)
    void testRetrieveReservationById() {
        // Arrange
        Reservation reservation = createReservation(true);
        Reservation savedReservation = reservationRepository.save(reservation);

        // Act
        Reservation foundReservation = reservationService.retrieveReservation(savedReservation.getIdReservation());

        // Assert
        assertNotNull(foundReservation, "The reservation retrieved by ID should not be null");


        // Clean up
        reservationRepository.delete(savedReservation);
    }



*/}
