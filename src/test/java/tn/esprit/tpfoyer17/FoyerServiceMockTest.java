package tn.esprit.tpfoyer17;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.repositories.FoyerRepository;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;
import tn.esprit.tpfoyer17.services.impementations.FoyerService;
import tn.esprit.tpfoyer17.exceptions.ResourceNotFoundException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
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
    void testRetrieveAllFoyers_EmptyList() {
        // Arrange
        when(foyerRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        var foyers = foyerService.retrieveAllFoyers();

        // Assert
        assertNotNull(foyers);
        assertEquals(0, foyers.size());
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
    void testAddFoyer_NullInput() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> foyerService.addFoyer(null));
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
    void testUpdateFoyer_NotFound() {
        // Arrange
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(999L); // Assuming this ID doesn't exist
        when(foyerRepository.save(any(Foyer.class))).thenThrow(new ResourceNotFoundException("Foyer not found"));

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> foyerService.updateFoyer(foyer));
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
    void testRetrieveFoyer_NotFound() {
        // Arrange
        when(foyerRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> foyerService.retrieveFoyer(1L));
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
        foyer.setIdFoyer(1L);

        // Creating and setting the bloc
        Bloc bloc1 = new Bloc();
        bloc1.setNomBloc("Bloc1");  // Providing a name for the Bloc

        // Using a Set instead of a List
        Set<Bloc> blocs = new HashSet<>();
        blocs.add(bloc1);
        foyer.setBlocs(blocs);

        // Mock repository behaviors
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));
        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc1);

        // Act
        Foyer result = foyerService.ajouterFoyerEtAffecterAUniversite(foyer, 1L);

        // Assert
        assertNotNull(result);
        assertEquals(foyer, result);
        verify(universiteRepository, times(1)).save(universite); // Ensure that universite is saved
        verify(blocRepository, times(1)).save(bloc1); // Ensure that bloc is saved
        verify(foyerRepository, times(1)).save(foyer); // Ensure that foyer is saved
    }


    @Test
    void testRemoveFoyer_Exception() {
        // Arrange
        doThrow(new RuntimeException("Database error")).when(foyerRepository).deleteById(1L);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> foyerService.removeFoyer(1L));
    }



    @Test
    void testRetrieveAllFoyers_Values() {
        // Arrange
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer1");
        when(foyerRepository.findAll()).thenReturn(Arrays.asList(foyer));

        // Act
        var foyers = foyerService.retrieveAllFoyers();

        // Assert
        assertNotNull(foyers);
        assertEquals(1, foyers.size());
        assertEquals("Foyer1", foyers.get(0).getNomFoyer());
    }
}
