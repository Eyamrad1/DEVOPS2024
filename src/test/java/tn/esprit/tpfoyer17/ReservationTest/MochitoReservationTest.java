package tn.esprit.tpfoyer17.ReservationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer17.entities.Reservation;
import tn.esprit.tpfoyer17.repositories.ReservationRepository;
import tn.esprit.tpfoyer17.services.impementations.ReservationService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTestMockito {

    @Mock
    private ReservationRepository reservationRepository;  // Simule le repository pour les tests

    @InjectMocks
    private ReservationService reservationService;  // Injecte le mock ReservationRepository dans le service à tester

    private Reservation reservation;  // Instance de reservation pour les tests

    @BeforeEach
    public void setUp() {
        // Initialisation des objets pour chaque test
        reservation = Reservation.builder()
                .idReservation("R12345")
                .anneeUniversitaire(LocalDate.of(2024, 9, 1))
                .estValide(true)
                .build();
    }

    /**
     * Teste la récupération de toutes les réservations.
     */
    @Test
    public void testRetrieveAllReservations() {
        // Simulation du comportement du repository
        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation));

        // Appel de la méthode à tester
        List<Reservation> reservations = reservationService.retrieveAllReservations();

        // Vérifications
        assertNotNull(reservations);  // Vérifie que la liste n'est pas nulle
        assertEquals(1, reservations.size());  // Vérifie qu'il y a une seule réservation dans la liste
        verify(reservationRepository).findAll();  // Vérifie que la méthode findAll a bien été appelée
    }

    /**
     * Teste l'ajout d'une réservation.
     */
    @Test
    public void testAddReservation() {
        // Simulation du comportement du repository
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        // Appel de la méthode à tester
        Reservation savedReservation = reservationService.addReservation(reservation);

        // Vérifications
        assertNotNull(savedReservation);  // Vérifie que la réservation sauvegardée n'est pas nulle
        assertEquals(reservation.getIdReservation(), savedReservation.getIdReservation());  // Vérifie l'ID de la réservation
        verify(reservationRepository).save(reservation);  // Vérifie que la méthode save a bien été appelée
    }

    /**
     * Teste la récupération d'une réservation par son ID.
     */
    @Test
    public void testRetrieveReservation() {
        // Simulation du comportement du repository
        when(reservationRepository.findById("R12345")).thenReturn(Optional.of(reservation));

        // Appel de la méthode à tester
        Reservation retrievedReservation = reservationService.retrieveReservation("R12345");

        // Vérifications
        assertNotNull(retrievedReservation);  // Vérifie que la réservation récupérée n'est pas nulle
        assertEquals("R12345", retrievedReservation.getIdReservation());  // Vérifie que l'ID correspond à celui de la réservation
        verify(reservationRepository).findById("R12345");  // Vérifie que la méthode findById a bien été appelée
    }

    /**
     * Teste la mise à jour de la validité d'une réservation.
     */
    @Test
    public void testUpdateReservationValidity() {
        reservation.setEstValide(false);
        // Simulation du comportement du repository
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        // Appel de la méthode à tester
        Reservation updatedReservation = reservationService.updateReservation(reservation);

        // Vérifications
        assertFalse(updatedReservation.isEstValide());  // Vérifie que la réservation est maintenant invalide
        verify(reservationRepository).save(reservation);  // Vérifie que la méthode save a bien été appelée
    }

}