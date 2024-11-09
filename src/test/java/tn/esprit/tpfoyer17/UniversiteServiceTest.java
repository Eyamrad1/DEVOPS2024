package tn.esprit.tpfoyer17;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.services.impementations.FoyerService;
import tn.esprit.tpfoyer17.services.impementations.UniversiteService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class UniversiteServiceTest {

    @Autowired
    UniversiteService universiteService;
    FoyerService foyerService;

    Universite universite;
    Foyer foyer;

    @BeforeEach
    void setUp() {
        universite = Universite.builder()
                .nomUniversite("Université de Test")
                .adresse("Adresse Test")
                .build();

        foyer = Foyer.builder()
                .nomFoyer("Foyer Test")
                .capaciteFoyer(100)
                .build();
    }

    @Test
    @Order(1)
    void testRetrieveAllUniversities() {
        // Arrange
        Universite addedUniversite = universiteService.addUniversity(universite);

        // Act
        List<Universite> result = universiteService.retrieveAllUniversities();

        // Assert
        assertFalse(result.isEmpty(), "La liste des universités ne devrait pas être vide.");
        log.info("testRetrieveAllUniversities passed");

        // Cleanup
        universiteService.deleteUniversity(addedUniversite.getIdUniversite());
    }


    @Test
    @Order(2)
    void testAddUniversity() {
        // Arrange (l'université est déjà prête dans setUp)

        // Act
        Universite result = universiteService.addUniversity(universite);

        // Assert
        assertNotNull(result);
        assertEquals("Université de Test", result.getNomUniversite());
        log.info("testAddUniversity passed");

        // Cleanup
        universiteService.deleteUniversity(result.getIdUniversite());
    }

    @Test
    @Order(3)
    void testUpdateUniversity() {
        // Arrange
        Universite savedUniversite = universiteService.addUniversity(universite);
        savedUniversite.setAdresse("Nouvelle Adresse");

        // Act
        Universite result = universiteService.updateUniversity(savedUniversite);

        // Assert
        assertEquals("Nouvelle Adresse", result.getAdresse());
        log.info("testUpdateUniversity passed");

        // Cleanup
        universiteService.deleteUniversity(savedUniversite.getIdUniversite());
    }

    @Test
    @Order(4)
    void testRetrieveUniversity() {
        // Arrange
        Universite savedUniversite = universiteService.addUniversity(universite);

        // Act
        Universite result = universiteService.retrieveUniversity(savedUniversite.getIdUniversite());

        // Assert
        assertNotNull(result);
        assertEquals(savedUniversite.getIdUniversite(), result.getIdUniversite());
        log.info("testRetrieveUniversity passed");

        // Cleanup
        universiteService.deleteUniversity(savedUniversite.getIdUniversite());
    }

    @Test
    @Order(5)
    void testDesaffecterFoyerAUniversite() {
        // Arrange
        Universite savedUniversite = universiteService.addUniversity(universite);
        savedUniversite.setFoyer(foyer);
        universiteService.updateUniversity(savedUniversite);

        // Act
        Universite result = universiteService.desaffecterFoyerAUniversite(savedUniversite.getIdUniversite());

        // Assert
        assertNull(result.getFoyer());
        log.info("testDesaffecterFoyerAUniversite passed");

        // Cleanup
        universiteService.deleteUniversity(savedUniversite.getIdUniversite());
    }

    @Test
    @Order(6)
    void testAffecterFoyerAUniversite() {
        // Arrange
        Universite addedUniversite = universiteService.addUniversity(universite);
        Foyer addedFoyer = foyerService.addFoyer(foyer); // Assurez-vous que `addFoyer` existe dans le service

        // Act
        Universite result = universiteService.affecterFoyerAUniversite(addedFoyer.getIdFoyer(), addedUniversite.getNomUniversite());

        // Assert
        assertNotNull(result.getFoyer());
        assertEquals("Foyer Test", result.getFoyer().getNomFoyer());
        log.info("testAffecterFoyerAUniversite passed");

        // Cleanup
        universiteService.desaffecterFoyerAUniversite(addedUniversite.getIdUniversite());
        universiteService.deleteUniversity(addedUniversite.getIdUniversite());
        foyerService.removeFoyer(addedFoyer.getIdFoyer());
    }

}
