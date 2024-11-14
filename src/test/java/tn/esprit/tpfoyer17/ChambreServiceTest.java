package tn.esprit.tpfoyer17;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.repositories.ChambreRepository;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;
import tn.esprit.tpfoyer17.services.impementations.ChambreService;

import java.util.List;
import java.util.Optional;

@SpringBootTest

public class ChambreServiceTest {/*
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @Autowired
    private ChambreService chambreService;

    @Autowired
    private ChambreRepository chambreRepository;

    private Chambre createChambre(TypeChambre typeChambre) {
        return Chambre.builder()
                .typeChambre(typeChambre)
                .build();
    }

    @Test
    @Order(1)
    void testRetrieveAllChambres() {
        // Arrange
        Chambre chambre1 = createChambre(TypeChambre.SIMPLE);
        Chambre chambre2 = createChambre(TypeChambre.DOUBLE);
        chambreRepository.save(chambre1);
        chambreRepository.save(chambre2);

        // Act
        List<Chambre> result = chambreService.retrieveAllChambres();

        // Assert
        Assertions.assertNotNull(result, "The result should not be null");
        Assertions.assertTrue(result.size() >= 2, "The result should contain at least 2 chambres");

        // Clean up
        chambreRepository.deleteAll(List.of(chambre1, chambre2));
    }

    @Test
    @Order(2)
    void testAddChambre() {
        // Arrange
        Chambre chambre = createChambre(TypeChambre.SIMPLE);

        // Act
        Chambre result = chambreService.addChambre(chambre);

        // Assert
        Assertions.assertNotNull(result, "The saved chambre should not be null");
        Assertions.assertNotNull(result.getIdChambre(), "The saved chambre should have a non-null ID");
        Assertions.assertEquals(TypeChambre.SIMPLE, result.getTypeChambre(), "The type of chambre should be SIMPLE");

        // Clean up
        chambreRepository.delete(result);
    }

    @Test
    @Order(3)
    void testUpdateChambre() {
        // Arrange
        Chambre chambre = createChambre(TypeChambre.SIMPLE);
        Chambre savedChambre = chambreRepository.save(chambre);

        // Act
        savedChambre.setTypeChambre(TypeChambre.DOUBLE);
        Chambre result = chambreService.updateChambre(savedChambre);

        // Assert
        Assertions.assertNotNull(result, "The updated chambre should not be null");
        Assertions.assertEquals(TypeChambre.DOUBLE, result.getTypeChambre(), "The type of chambre should be updated to DOUBLE");

        // Clean up
        chambreRepository.delete(result);
    }

    @Test
    @Order(4)
    void testRemoveChambre() {
        // Arrange
        Chambre chambre = createChambre(TypeChambre.SIMPLE);
        Chambre savedChambre = chambreRepository.save(chambre);

        // Act
        chambreService.removeChambre(savedChambre.getIdChambre());

        // Assert
        Assertions.assertFalse(chambreRepository.existsById(savedChambre.getIdChambre()), "The chambre should no longer exist in the repository");
    }

    @Test
    @Order(5)
    void testUpdateChambreType() {
        // Arrange
        Chambre chambre = createChambre(TypeChambre.SIMPLE);
        Chambre savedChambre = chambreRepository.save(chambre);

        // Act
        savedChambre.setTypeChambre(TypeChambre.DOUBLE);
        Chambre updatedChambre = chambreService.updateChambre(savedChambre);

        // Assert
        Assertions.assertNotNull(updatedChambre, "The updated chambre should not be null");
        Assertions.assertEquals(TypeChambre.DOUBLE, updatedChambre.getTypeChambre(), "The type of chambre should be DOUBLE");

        // Clean up
        chambreRepository.delete(updatedChambre);
    }

    @Test
    @Order(6)


    void testRetrieveChambreById() {
        // Arrange
        Chambre chambre = createChambre(TypeChambre.SIMPLE);
        Chambre savedChambre = chambreRepository.save(chambre);

        // Act
        Chambre foundChambre = chambreService.retrieveChambre(savedChambre.getIdChambre());

        // Assert
        Assertions.assertNotNull(foundChambre, "The chambre retrieved by ID should not be null");
        Assertions.assertEquals(savedChambre.getIdChambre(), foundChambre.getIdChambre(),
                "The ID of the retrieved chambre should match the saved chambre ID");

        // Clean up
        chambreRepository.delete(savedChambre);
    }


    @Test
    @Order(7)
    void testRetrieveChambresCount() {
        // Arrange
        Chambre chambre1 = createChambre(TypeChambre.SIMPLE);
        Chambre chambre2 = createChambre(TypeChambre.DOUBLE);
        chambreRepository.saveAll(List.of(chambre1, chambre2));

        // Act
        long count = chambreService.retrieveAllChambres().size();

        // Assert
        Assertions.assertTrue(count >= 2, "The chambre count should be at least 2");

        // Clean up
        chambreRepository.deleteAll(List.of(chambre1, chambre2));
    }
*/}
