package tn.esprit.tpfoyer17.ReservationTest;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.tpfoyer17.entities.Reservation;
import tn.esprit.tpfoyer17.repositories.ReservationRepository;
import tn.esprit.tpfoyer17.services.impementations.ReservationService;

public class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService; // Service à tester

    @Autowired
    private ReservationRepository reservationRepository; // Repository pour interagir avec la base de données

    // Méthode utilitaire pour créer une réservation
    private Reservation createReservation() {
        return Reservation.builder()
                .clientName("John Doe")
                .chambreId(1L) // Supposons que 1L est l'ID d'une chambre existante
                .startDate("2024-11-01")
                .endDate("2024-11-10")
                .build();
    }

    @Test
    @Order(1)
    void testRetrieveAllReservations() {
        // Arrange
        Reservation reservation1 = createReservation();
        Reservation reservation2 = createReservation();
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        // Act
        List<Reservation> result = reservationService.retrieveAllReservations();

        // Assert
        assertNotNull(result, "The result should not be null");
        assertTrue(result.size() >= 2, "The result should contain at least 2 reservations");

        // Clean up
        reservationRepository.deleteAll(List.of(reservation1, reservation2));
    }

    @Test
    @Order(2)
    void testAddReservation() {
        // Arrange
        Reservation reservation = createReservation();

        // Act
        Reservation result = reservationService.addReservation(reservation);

        // Assert
        assertNotNull(result, "The saved reservation should not be null");
        assertNotNull(result.getIdReservation(), "The saved reservation should have a non-null ID");
        assertEquals("John Doe", result.getClientName(), "The client name should be John Doe");

        // Clean up
        reservationRepository.delete(result);
    }

    @Test
    @Order(3)
    void testUpdateReservation() {
        // Arrange
        Reservation reservation = createReservation();
        Reservation savedReservation = reservationRepository.save(reservation);

        // Act
        savedReservation.setClientName("Jane Smith");
        Reservation result = reservationService.updateReservation(savedReservation);

        // Assert
        assertNotNull(result, "The updated reservation should not be null");
        assertEquals("Jane Smith", result.getClientName(), "The client name should be updated to Jane Smith");

        // Clean up
        reservationRepository.delete(result);
    }

    @Test
    @Order(4)
    void testRemoveReservation() {
        // Arrange
        Reservation reservation = createReservation();
        Reservation savedReservation = reservationRepository.save(reservation);

        // Act
        reservationService.removeReservation(savedReservation.getIdReservation());

        // Assert
        assertFalse(reservationRepository.existsById(savedReservation.getIdReservation()), "The reservation should no longer exist in the repository");
    }

    @Test
    @Order(5)
    void testRetrieveReservationById() {
        // Arrange
        Reservation reservation = createReservation();
        Reservation savedReservation = reservationRepository.save(reservation);

        // Act
        Reservation foundReservation = reservationService.retrieveReservation(savedReservation.getIdReservation());

        // Assert
        assertNotNull(foundReservation, "The reservation retrieved by ID should not be null");
        assertEquals(savedReservation.getIdReservation(), foundReservation.getIdReservation(),
                "The ID of the retrieved reservation should match the saved reservation ID");

        // Clean up
        reservationRepository.delete(savedReservation);
    }

    @Test
    @Order(6)
    void testRetrieveReservationsCount() {
        // Arrange
        Reservation reservation1 = createReservation();
        Reservation reservation2 = createReservation();
        reservationRepository.saveAll(List.of(reservation1, reservation2));

        // Act
        long count = reservationService.retrieveAllReservations().size();

        // Assert
        assertTrue(count >= 2, "The reservation count should be at least 2");

        // Clean up
        reservationRepository.deleteAll(List.of(reservation1, reservation2));
    }
}
