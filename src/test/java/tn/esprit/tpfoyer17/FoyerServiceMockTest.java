package tn.esprit.tpfoyer17;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.repositories.FoyerRepository;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;
import tn.esprit.tpfoyer17.services.impementations.FoyerService;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class FoyerServiceMockTest {

    @Mock
    private FoyerRepository foyerRepository;

    @Mock
    private BlocRepository blocRepository;

    @Mock
    private UniversiteRepository universiteRepository;

    @InjectMocks
    private FoyerService foyerService;

    @Test
     void testRetrieveAllFoyers() {
        // Arrange
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(1L);
        when(foyerRepository.findAll()).thenReturn(Arrays.asList(foyer));

        // Act
        var foyers = foyerService.retrieveAllFoyers();

        // Assert
        assertNotNull(foyers);
        assertEquals(1, foyers.size());
        assertEquals(foyer, foyers.get(0));
    }

    @Test
     void testAddFoyer() {
        // Arrange
        Foyer foyer = new Foyer();
        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);

        // Act
        Foyer result = foyerService.addFoyer(foyer);

        // Assert
        assertNotNull(result);
        assertEquals(foyer, result);
    }

    @Test
     void testUpdateFoyer() {
        // Arrange
        Foyer foyer = new Foyer();
        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);

        // Act
        Foyer result = foyerService.updateFoyer(foyer);

        // Assert
        assertNotNull(result);
        assertEquals(foyer, result);
    }

    @Test
    void testRetrieveFoyer() {
        // Arrange
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(1L);
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));

        // Act
        Foyer result = foyerService.retrieveFoyer(1L);

        // Assert
        assertNotNull(result);
        assertEquals(foyer, result);
    }

    @Test
     void testRemoveFoyer() {
        // Arrange
        doNothing().when(foyerRepository).deleteById(1L);

        // Act
        foyerService.removeFoyer(1L);

        // Assert
        verify(foyerRepository, times(1)).deleteById(1L);
    }

    @Test
     void testAjouterFoyerEtAffecterAUniversite() {
        // Arrange
        Foyer foyer = new Foyer();
        Universite universite = new Universite();
        universite.setIdUniversite(1L);
        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));
        doNothing().when(blocRepository).save(any(Bloc.class));

        // Act
        Foyer result = foyerService.ajouterFoyerEtAffecterAUniversite(foyer, 1L);

        // Assert
        assertNotNull(result);
        assertEquals(foyer, result);
        verify(blocRepository, times(1)).save(any(Bloc.class));
        verify(universiteRepository, times(1)).save(universite);
    }
}
