package tn.esprit.tpfoyer17;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.repositories.FoyerRepository;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;
import tn.esprit.tpfoyer17.services.impementations.FoyerService;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = TpFoyer17Application.class)


@Slf4j
class FoyerServiceTest {

    @Autowired
    private FoyerService foyerService;

    @Autowired
    private FoyerRepository foyerRepository;

    @Autowired
    private UniversiteRepository universiteRepository;

    @Autowired
    private BlocRepository blocRepository;

    private Foyer foyer;
    private Universite universite;
    private Bloc bloc;

    @BeforeEach
    void setUp() {
        // Setup Universite entity
        universite = new Universite(1L, "University A", "Some Address", null);
        universite = universiteRepository.save(universite);

        // Setup Foyer entity
        foyer = new Foyer(1L, "Foyer A", 100, null, null);
        foyer = foyerRepository.save(foyer);

        // Setup Bloc entity associated with the foyer
        bloc = new Bloc(0L, "Bloc A", 50, foyer, null); // Adjusted constructor to use all fields
        bloc = blocRepository.save(bloc);
    }

    @AfterEach
    void tearDown() {
        // Clean up database after each test
        blocRepository.deleteAll();
        foyerRepository.deleteAll();
        universiteRepository.deleteAll();
    }

    @Test
    @Order(1)
    void testRetrieveAllFoyers() {
        // Act: Call the method under test
        List<Foyer> foyers = foyerService.retrieveAllFoyers();

        // Assert: Verify the result
        assertNotNull(foyers);
        assertTrue(foyers.size() > 0);
        assertEquals("Foyer A", foyers.get(0).getNomFoyer());
    }

    @Test
    @Order(2)
    void testAddFoyer() {
        // Prepare a new foyer object to add
        Foyer newFoyer = new Foyer(2L, "New Foyer", 100, null, null);

        // Act: Add the new foyer
        Foyer result = foyerService.addFoyer(newFoyer);

        // Assert: Verify the result
        assertNotNull(result);
        assertEquals("New Foyer", result.getNomFoyer());
        assertEquals(100, result.getCapaciteFoyer());
    }

    @Test
    @Order(3)
    void testUpdateFoyer() {
        // Update the existing foyer with new values
        foyer.setNomFoyer("Updated Foyer");

        // Act: Update the foyer in the database
        Foyer updatedFoyer = foyerService.updateFoyer(foyer);

        // Assert: Verify the updated name
        assertNotNull(updatedFoyer);
        assertEquals("Updated Foyer", updatedFoyer.getNomFoyer());
    }

    @Test
    @Order(4)
    void testRetrieveFoyer() {
        // Act: Retrieve the foyer by ID
        Foyer retrievedFoyer = foyerService.retrieveFoyer(foyer.getIdFoyer());

        // Assert: Verify the retrieved foyer
        assertNotNull(retrievedFoyer);
        assertEquals("Foyer A", retrievedFoyer.getNomFoyer());
    }

    @Test
    @Order(5)
    void testRemoveFoyer() {
        // Act: Remove the foyer by its ID
        foyerService.removeFoyer(foyer.getIdFoyer());

        // Assert: Verify that the foyer is no longer in the repository
        assertFalse(foyerRepository.existsById(foyer.getIdFoyer()));
    }





    @Test
    @Order(7)
    void testFoyerAndBlocRelationship() {
        // Ensure the bloc is properly associated with the foyer
        Bloc savedBloc = blocRepository.findById(bloc.getIdBloc()).orElse(null);
        assertNotNull(savedBloc);
        assertEquals(foyer.getIdFoyer(), savedBloc.getFoyer().getIdFoyer());
    }
}
