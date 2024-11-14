package tn.esprit.tpfoyer17.ReservationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.entities.Etudiant;
import tn.esprit.tpfoyer17.entities.Reservation;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;
import tn.esprit.tpfoyer17.repositories.ChambreRepository;
import tn.esprit.tpfoyer17.repositories.EtudiantRepository;
import tn.esprit.tpfoyer17.repositories.ReservationRepository;
import tn.esprit.tpfoyer17.services.impementations.ReservationService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MochitoReservationTest {

    @Nested
    @ExtendWith(MockitoExtension.class)
    class ReservationServiceTestMockito {

        @Mock
        private ReservationRepository reservationRepository;  // Simule le repository de réservation

        @Mock
        private EtudiantRepository etudiantRepository;  // Simule le repository des étudiants

        @Mock
        private ChambreRepository chambreRepository;  // Simule le repository des chambres

        @InjectMocks
        private ReservationService reservationService;  // Service à tester

        private Reservation reservation;
        private Etudiant etudiant;
        private Chambre chambre;

        @BeforeEach
        public void setUp() {
            // Initialisation des objets pour chaque test
            etudiant = new Etudiant();
            etudiant.setCinEtudiant(12345L);

            chambre = Chambre.builder()
                    .idChambre(1L)
                    .numeroChambre(101L)
                    .typeChambre(TypeChambre.SIMPLE)
                    .build();

            reservation = Reservation.builder()
                    .idReservation("101-BlocA-2024")
                    .anneeUniversitaire(LocalDate.now())
                    .estValide(true)
                    .etudiants(new HashSet<>())
                    .build();
        }

        @Test
        public void testAjouterReservation() {
            // Simulation du comportement des repositories
            when(etudiantRepository.findByCinEtudiant(12345L)).thenReturn(etudiant);
            when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre));
            when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

            // Appel de la méthode à tester
            Reservation savedReservation = reservationService.ajouterReservation(1L, 12345L);

            // Vérifications
            assertNotNull(savedReservation);  // Vérifie que la réservation est non nulle
            assertEquals("101-BlocA-2024", savedReservation.getIdReservation());  // Vérifie l'ID de la réservation
            verify(etudiantRepository).findByCinEtudiant(12345L);  // Vérifie que la méthode findByCinEtudiant a été appelée
            verify(chambreRepository).findById(1L);  // Vérifie que la méthode findById a été appelée
            verify(reservationRepository).save(any(Reservation.class));  // Vérifie que save a été appelé sur reservationRepository
        }

        @Test
        public void testRetrieveAllReservations() {
            // Simulation du comportement du repository
            when(reservationRepository.findAll()).thenReturn(List.of(reservation));

            // Appel de la méthode à tester
            List<Reservation> reservations = reservationService.retrieveAllReservation();

            // Vérifications
            assertNotNull(reservations);  // Vérifie que la liste n'est pas nulle
            assertEquals(1, reservations.size());  // Vérifie que la liste contient une réservation
            verify(reservationRepository).findAll();  // Vérifie que la méthode findAll a été appelée
        }

        @Test
        public void testRetrieveReservation() {
            // Simulation du comportement du repository
            when(reservationRepository.findById("101-BlocA-2024")).thenReturn(Optional.of(reservation));

            // Appel de la méthode à tester
            Reservation retrievedReservation = reservationService.retrieveReservation("101-BlocA-2024");

            // Vérifications
            assertNotNull(retrievedReservation);  // Vérifie que la réservation est non nulle
            assertEquals("101-BlocA-2024", retrievedReservation.getIdReservation());  // Vérifie l'ID de la réservation
            verify(reservationRepository).findById("101-BlocA-2024");  // Vérifie que findById a été appelé
        }

        // Ajouter d'autres tests comme pour annuler une réservation, ou récupérer des réservations par année universitaire
    }
}
