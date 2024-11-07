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
@SpringBootTest
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
     Universite universite;
    Bloc bloc;

    @BeforeEach
    void setUp() {
        // Arrange: Setup entities for each test
        universite = universiteRepository.save(new Universite(1L, "University A", "Some Address", null));
        foyer = foyerRepository.save(new Foyer(1L, "Foyer A", 100, null, null));  // Use the constructor with parameters
    }

    @AfterEach
    void tearDown() {
        // Clean up the database after each test
        foyerRepository.deleteAll();
        universiteRepository.deleteAll();
        blocRepository.deleteAll();
    }

    @Test
    void testRetrieveAllFoyers() {
        // Arrange: Ensure the database has some foyers to retrieve
        foyerRepository.save(new Foyer(2L, "Foyer B", 200, null, null)); // This ensures that there are multiple foyers

        // Act: Call the method under test
        List<Foyer> foyers = foyerService.retrieveAllFoyers();

        // Assert: Verify the result
        assertNotNull(foyers);
        assertTrue(foyers.size() > 0);
        assertEquals("Foyer A", foyers.get(0).getNomFoyer());
    }

    @Test
    void testAddFoyer() {
        // Arrange: Prepare a new foyer object to add using the correct constructor
        Foyer newFoyer = new Foyer(2L, "New Foyer", 100, null, null); // Use the correct constructor

        // Act: Add the new foyer
        Foyer result = foyerService.addFoyer(newFoyer);

        // Assert: Verify the result
        assertNotNull(result);
        assertEquals("New Foyer", result.getNomFoyer());
    }

    @Test
    void testUpdateFoyer() {
        // Arrange: Update the existing foyer with new values
        foyer.setNomFoyer("Updated Foyer");

        // Act: Update the foyer in the database
        Foyer updatedFoyer = foyerService.updateFoyer(foyer);

        // Assert: Verify the updated name
        assertNotNull(updatedFoyer);
        assertEquals("Updated Foyer", updatedFoyer.getNomFoyer());
    }

    @Test
    void testRetrieveFoyer() {
        // Arrange: Ensure the foyer is already in the database (this should already be the case due to @BeforeEach)

        // Act: Retrieve the foyer using its ID
        Foyer result = foyerService.retrieveFoyer(foyer.getIdFoyer());

        // Assert: Verify the retrieved foyer matches the expected name
        assertNotNull(result);
        assertEquals("Foyer A", result.getNomFoyer());
    }

    @Test
    void testRemoveFoyer() {
        // Arrange: Ensure the foyer is in the repository before removal (it should be from @BeforeEach)

        // Act: Remove the foyer by its ID
        foyerService.removeFoyer(foyer.getIdFoyer());

        // Assert: Verify that the foyer is no longer in the repository
        assertNull(foyerRepository.findById(foyer.getIdFoyer()).orElse(null));
    }

    /*@Test
    void testAjouterFoyerEtAffecterAUniversite() {
        // Arrange: Create a new Universite and Foyer to set up the test environment
        Universite universites = new Universite();
        universites.setIdUniversite(1L);
        universites.setNomUniversite("University A");
        universiteRepository.save(universites); // Save the university to the database

        Foyer newFoyer = new Foyer();
        newFoyer.setIdFoyer(2L);
        newFoyer.setNomFoyer("Foyer B");
        newFoyer.setCapaciteFoyer(150);
        // Optionally set other properties

        // Act: Add the foyer and associate it with the university
        Foyer result = foyerService.ajouterFoyerEtAffecterAUniversite(newFoyer, universites.getIdUniversite());

        // Assert: Verify the foyer and university association
        assertNotNull(result); // Make sure the result is not null
        assertEquals("Foyer B", result.getNomFoyer()); // Ensure the Foyer's name is correct
        assertNotNull(result.getUniversite()); // Ensure the Foyer is associated with a Universite
        assertEquals("University A", result.getUniversite().getNomUniversite()); // Verify the Universite name

        // Verify the actual saving of the foyer
        Foyer savedFoyer = foyerRepository.findById(result.getIdFoyer()).orElse(null);
        assertNotNull(savedFoyer); // Ensure the foyer is saved
        assertEquals("Foyer B", savedFoyer.getNomFoyer()); // Verify the saved Foyer has the correct name
    }*/
}
