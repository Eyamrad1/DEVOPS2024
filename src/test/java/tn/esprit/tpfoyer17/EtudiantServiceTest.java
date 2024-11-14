package tn.esprit.tpfoyer17;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.tpfoyer17.entities.Etudiant;
import tn.esprit.tpfoyer17.repositories.EtudiantRepository;
import tn.esprit.tpfoyer17.services.impementations.EtudiantService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Slf4j
@Transactional
@ActiveProfiles("test")
 class EtudiantServiceTest {
    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private EtudiantRepository etudiantRepository;
    Etudiant etudiant;

    @BeforeEach
    void setUp() {
       // Create Etudiant objects using the builder pattern or constructor
       Etudiant etudiant1 = Etudiant.builder()
               .nomEtudiant("ahmed")
               .prenomEtudiant("lass")
               .cinEtudiant(12345678L)
               .dateNaissance(java.sql.Date.valueOf("2000-01-15"))
               .build();

       Etudiant etudiant2 = Etudiant.builder()
               .nomEtudiant("ali")
               .prenomEtudiant("black")
               .cinEtudiant(87654321L)
               .dateNaissance(java.sql.Date.valueOf("2001-03-22"))
               .build();

       // Save entities to the repository
       etudiantRepository.save(etudiant1);
       etudiantRepository.save(etudiant2);
    }


   @Test
   @Order(1)
   void testRetrieveAllEtudiants() {
      // Act: Retrieve all Etudiants from the service
      List<Etudiant> etudiants = etudiantService.retrieveAllEtudiants();

      // Assert: Ensure the list is not null and contains exactly 2 Etudiants
      assertNotNull(etudiants);
      assertEquals(2, etudiants.size());  // Assuming 2 Etudiants were added during setup
   }

   @Test
   void testAddEtudiants() {
      // Arrange: Create new Etudiants using the default constructor and setters
      Etudiant etudiant3 = new Etudiant();
      etudiant3.setNomEtudiant("ahmed");
      etudiant3.setPrenomEtudiant("Brown");
      etudiant3.setCinEtudiant(11223344L);
      etudiant3.setDateNaissance(java.sql.Date.valueOf("2002-05-10"));

      Etudiant etudiant4 = new Etudiant();
      etudiant4.setNomEtudiant("David");
      etudiant4.setPrenomEtudiant("Clark");
      etudiant4.setCinEtudiant(99887766L);
      etudiant4.setDateNaissance(java.sql.Date.valueOf("2003-07-18"));

      // Act: Add Etudiants to the database using the service
      List<Etudiant> addedEtudiants = etudiantService.addEtudiants(List.of(etudiant3, etudiant4));

      // Assert: Ensure 2 Etudiants were added
      assertNotNull(addedEtudiants);
      assertEquals(2, addedEtudiants.size());

      // Act: Retrieve all Etudiants to ensure they are in the database
      List<Etudiant> allEtudiants = etudiantService.retrieveAllEtudiants();

      // Assert: Ensure the total count of Etudiants is 4
      assertEquals(4, allEtudiants.size());
   }

   @Test
   void testUpdateEtudiant() {
      // Arrange: Create an Etudiant using the default constructor and setters
      Etudiant etudiant5 = new Etudiant();
      etudiant5.setNomEtudiant("Initial Etudiant");
      etudiant5.setPrenomEtudiant("Prenom");
      etudiant5.setCinEtudiant(12345678L);
      etudiant5.setDateNaissance(new java.sql.Date(System.currentTimeMillis()));

      // Act: Save the initial Etudiant
      Etudiant savedEtudiant = etudiantService.addEtudiants(List.of(etudiant5)).get(0);

      // Update the saved Etudiant
      savedEtudiant.setNomEtudiant("Updated Etudiant");

      // Act: Update Etudiant in the database
      Etudiant updatedEtudiant = etudiantService.updateEtudiant(savedEtudiant);

      // Assert: Ensure the update was successful
      assertNotNull(updatedEtudiant);
      assertEquals("Updated Etudiant", updatedEtudiant.getNomEtudiant());
   }

   @Test
   void testRetrieveEtudiant() {
      // Arrange: Create and save an Etudiant using the default constructor and setters
      Etudiant etudiante = new Etudiant();
      etudiante.setNomEtudiant("ali");
      etudiante.setPrenomEtudiant("Black");
      etudiante.setCinEtudiant(87654321L);
      etudiante.setDateNaissance(java.sql.Date.valueOf("2001-03-22"));
      etudiantService.addEtudiants(List.of(etudiante));

      // Act: Retrieve the Etudiant by its ID
      Etudiant retrievedEtudiant = etudiantService.retrieveEtudiant(etudiante.getIdEtudiant());

      // Assert: Ensure the Etudiant was retrieved and matches expected values
      assertNotNull(retrievedEtudiant);
      assertEquals("ali", retrievedEtudiant.getNomEtudiant());
   }

   @Test
   void testRemoveEtudiant() {
      // Arrange: Create and save a new Etudiant to delete using the default constructor and setters
      Etudiant newEtudiantToDelete = new Etudiant();
      newEtudiantToDelete.setNomEtudiant("abdo");
      newEtudiantToDelete.setPrenomEtudiant("ali");
      newEtudiantToDelete.setCinEtudiant(11123197L);
      newEtudiantToDelete.setDateNaissance(java.sql.Date.valueOf("2002-05-10"));
      newEtudiantToDelete = etudiantRepository.save(newEtudiantToDelete);

      // Act: Remove the Etudiant by its ID
      etudiantService.removeEtudiant(newEtudiantToDelete.getIdEtudiant());

      // Assert: Ensure the Etudiant has been deleted from the repository
      Etudiant deletedEtudiant = etudiantRepository.findById(newEtudiantToDelete.getIdEtudiant()).orElse(null);
      assertNull(deletedEtudiant); // Should be null after removal
   }

}
