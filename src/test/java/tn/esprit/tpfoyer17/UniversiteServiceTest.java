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

    @Autowired
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
        Universite addedUniversite = universiteService.addUniversity(universite);

        List<Universite> result = universiteService.retrieveAllUniversities();

        assertFalse(result.isEmpty(), "La liste des universités ne devrait pas être vide.");
        log.info("testRetrieveAllUniversities passed");

        universiteService.deleteUniversity(addedUniversite.getIdUniversite());
    }

    @Test
    @Order(2)
    void testAddUniversity() {
        Universite result = universiteService.addUniversity(universite);

        assertNotNull(result);
        assertEquals("Université de Test", result.getNomUniversite());
        log.info("testAddUniversity passed");

        universiteService.deleteUniversity(result.getIdUniversite());
    }

    @Test
    @Order(3)
    void testUpdateUniversity() {
        Universite savedUniversite = universiteService.addUniversity(universite);
        savedUniversite.setAdresse("Nouvelle Adresse");

        Universite result = universiteService.updateUniversity(savedUniversite);

        assertEquals("Nouvelle Adresse", result.getAdresse());
        log.info("testUpdateUniversity passed");

        universiteService.deleteUniversity(savedUniversite.getIdUniversite());
    }

    @Test
    @Order(4)
    void testRetrieveUniversity() {
        Universite savedUniversite = universiteService.addUniversity(universite);

        Universite result = universiteService.retrieveUniversity(savedUniversite.getIdUniversite());

        assertNotNull(result);
        assertEquals(savedUniversite.getIdUniversite(), result.getIdUniversite());
        log.info("testRetrieveUniversity passed");

        universiteService.deleteUniversity(savedUniversite.getIdUniversite());
    }


}
