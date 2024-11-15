package tn.esprit.tpfoyer17;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.tpfoyer17.entities.Etudiant;
import tn.esprit.tpfoyer17.repositories.EtudiantRepository;
import tn.esprit.tpfoyer17.services.impementations.EtudiantService;

import java.util.Arrays;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
 class EtudiantServiceMockTest {
    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantService etudiantService;

    @BeforeEach
    void setUp() {
        // Create Etudiant objects using the builder pattern or constructor
        Etudiant etudiante = Etudiant.builder()
                .nomEtudiant("ahmed")
                .prenomEtudiant("lass")
                .cinEtudiant(12345678L)
                .dateNaissance(java.sql.Date.valueOf("2000-01-15"))
                .build();

        Etudiant etudiantt = Etudiant.builder()
                .nomEtudiant("ali")
                .prenomEtudiant("black")
                .cinEtudiant(87654321L)
                .dateNaissance(java.sql.Date.valueOf("2001-03-22"))
                .build();

        // Save entities to the repository
        etudiantRepository.save(etudiante);
        etudiantRepository.save(etudiantt);
    }
     Etudiant etudiant1;
     Etudiant etudiant2;

    @Test
    void testRetrieveAllEtudiants() {
        // Arrange: Mock the behavior of the repository
        when(etudiantRepository.findAll()).thenReturn(Arrays.asList(etudiant1, etudiant2));

        // Act: Call the service method
        var etudiants = etudiantService.retrieveAllEtudiants();

        // Assert: Verify the results
        assertNotNull(etudiants);
        assertEquals(2, etudiants.size());
    }

    @Test
    void testAddEtudiants() {
        // Arrange: Mock the behavior of the repository
        when(etudiantRepository.saveAll(Arrays.asList(etudiant1, etudiant2))).thenReturn(Arrays.asList(etudiant1, etudiant2));

        // Act: Call the service method
        var addedEtudiants = etudiantService.addEtudiants(Arrays.asList(etudiant1, etudiant2));

        // Assert: Verify the results
        assertNotNull(addedEtudiants);
        assertEquals(2, addedEtudiants.size());
    }

    @Test
    void testUpdateEtudiant() {
        // Arrange
        Etudiant etudiant = new Etudiant();
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        // Act
        Etudiant result = etudiantService.updateEtudiant(etudiant);

        // Assert
        assertNotNull(result);
        assertEquals(etudiant, result);
    }


    @Test
    void testFindByReservationsAnneeUniversitaire() {
        // Arrange: Mock the behavior of the repository
        // Assuming a method in the repository that filters by year
        when(etudiantRepository.findByReservationsAnneeUniversitaire(any())).thenReturn(Arrays.asList(etudiant1));

        // Act: Call the service method
        var etudiants = etudiantService.findByReservationsAnneeUniversitaire();

        // Assert: Verify the results
        assertNotNull(etudiants);
        assertEquals(1, etudiants.size());
        verify(etudiantRepository, times(1)).findByReservationsAnneeUniversitaire(any()); // Verify the method was called once
    }
}
