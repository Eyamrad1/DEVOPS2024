package tn.esprit.tpfoyer17;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.services.impementations.UniversiteService;
import tn.esprit.tpfoyer17.services.interfaces.IUniversiteService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) // Extension Spring pour les tests JUnit 5
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Assure que les tests sont exécutés dans l'ordre spécifié
@Slf4j
public class UniversiteServiceTest {

    @Autowired
    private UniversiteService universiteService; // Utilisation du service au lieu du repository

    private static Universite universite;

    @BeforeAll
    static void setup() {
        // Phase d'arrangement - Préparation des données avant l'exécution des tests
        log.info("Initialisation des données avant les tests.");
        universite = Universite.builder()
                .nomUniversite("Université Test")
                .build();
    }

    @Test
    @Order(1)
    void testAddUniversity() {
        // Phase d'arrangement - Création de l'université à ajouter
        Universite universityToSave = Universite.builder()
                .nomUniversite("Université 1")
                .build();

        // Phase d'action - Appel de la méthode via le service
        Universite savedUniversity = universiteService.addUniversity(universityToSave);

        // Phase d'assertion - Vérification du résultat
        assertNotNull(savedUniversity);
        assertEquals("Université 1", savedUniversity.getNomUniversite());

        // Phase de nettoyage - Suppression de l'université après test
        universiteService.deleteUniversity(savedUniversity.getIdUniversite());
    }

    @Test
    @Order(2)
    void testUpdateUniversity() {
        // Phase d'arrangement - Préparation des données avec un nom universitaire modifié
        universite.setNomUniversite("Université Modifiée");

        // Phase d'action - Mise à jour de l'université via le service
        Universite updatedUniversite = universiteService.updateUniversity(universite);

        // Phase d'assertion - Vérification du nom mis à jour
        assertNotNull(updatedUniversite);
        assertEquals("Université Modifiée", updatedUniversite.getNomUniversite());

        // Phase de nettoyage - Suppression de l'université après test
        universiteService.deleteUniversity(updatedUniversite.getIdUniversite());
    }

    @Test
    @Order(3)
    void testRetrieveUniversity() {
        // Phase d'arrangement - Préparation d'une université existante dans la base de données
        Universite universityToRetrieve = universiteService.addUniversity(universite);

        // Phase d'action - Récupération de l'université par son ID
        Universite retrievedUniversity = universiteService.retrieveUniversity(universityToRetrieve.getIdUniversite());

        // Phase d'assertion - Vérification de la récupération correcte
        assertNotNull(retrievedUniversity);
        assertEquals(universityToRetrieve.getNomUniversite(), retrievedUniversity.getNomUniversite());

        // Phase de nettoyage - Suppression de l'université après test
        universiteService.deleteUniversity(retrievedUniversity.getIdUniversite());
    }

    @Test
    @Order(4)
    void testDeleteUniversity() {
        // Phase d'arrangement - Créer une université à supprimer
        Universite universityToDelete = Universite.builder()
                .nomUniversite("Université à Supprimer")
                .build();
        Universite savedUniversity = universiteService.addUniversity(universityToDelete);

        // Phase d'action - Suppression de l'université
        universiteService.deleteUniversity(savedUniversity.getIdUniversite());

        // Phase d'assertion - Vérifier que l'université a bien été supprimée
        Universite retrievedUniversity = universiteService.retrieveUniversity(savedUniversity.getIdUniversite());

        // Ajouter un message de log pour déboguer
        log.info("Vérification si l'université avec ID {} est supprimée: {}", savedUniversity.getIdUniversite(), retrievedUniversity);

        // Vérifier que l'université est nulle après la suppression
        assertNull(retrievedUniversity, "L'université doit être supprimée.");
    }



}
