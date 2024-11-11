package tn.esprit.tpfoyer17;


import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;
import tn.esprit.tpfoyer17.services.impementations.UniversiteService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class UniversiteServiceMockTest {

    @Autowired
    UniversiteService universiteService;

    @MockBean
    UniversiteRepository universiteRepository;

    @Test
    @Order(1)
    void testRetrieveAllUniversities() {
        // Arrange: Préparation des données
        List<Universite> universities = new ArrayList<>();
        universities.add(new Universite());
        when(universiteRepository.findAll()).thenReturn(universities);

        // Act: Appel de la méthode à tester
        List<Universite> result = universiteService.retrieveAllUniversities();

        // Assert: Vérification des résultats
        assertEquals(1, result.size());
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    @Order(2)
    void testAddUniversity() {
        // Arrange: Préparation de l'entité à ajouter
        Universite universite = new Universite();
        // Act: Ajout de l'université

        when(universiteRepository.save(universite)).thenReturn(universite);

        Universite result = universiteService.addUniversity(universite);

        // Assert: Vérification de l'ajout
        assertNotNull(result);
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    @Order(3)
    void testUpdateUniversity() {
        // Arrange: Préparation de l'entité à mettre à jour
        Universite universite = new Universite();

        // Act: Mise à jour de l'université

        when(universiteRepository.save(universite)).thenReturn(universite);

        Universite result = universiteService.updateUniversity(universite);

        // Assert: Vérification de la mise à jour
        assertNotNull(result);
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    @Order(4)
    void testRetrieveUniversity() {
        // Arrange: Préparation de l'entité et du mock pour le retrouver par ID
        Universite universite = new Universite();

        // Act: Récupération de l'université

        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));

        Universite result = universiteService.retrieveUniversity(1L);

        // Assert: Vérification de la récupération
        assertNotNull(result);
        verify(universiteRepository, times(1)).findById(1L);
    }

    @Test
    @Order(5)
    void testDeleteUniversity() {
        // Arrange: Préparation du mock pour l'entité à supprimer
        Universite universite = new Universite();

        // Act: Suppression de l'université

        
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));

        universiteService.deleteUniversity(1L);

        // Assert: Vérification de la suppression
        verify(universiteRepository, times(1)).findById(1L);
        verify(universiteRepository, times(1)).deleteById(1L);
    }
}
