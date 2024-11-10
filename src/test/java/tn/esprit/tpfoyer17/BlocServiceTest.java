package tn.esprit.tpfoyer17;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.services.impementations.BlocService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class BlocServiceTest {

    @InjectMocks
    BlocService blocService;

    @Mock
    BlocRepository blocRepository;

    @Test
    void testRetrieveBloc() {
        // Arrange
        Bloc bloc = Bloc.builder()
                .idBloc(1L)
                .nomBloc("Bloc A")
                .build();
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));

        // Act
        Bloc foundBloc = blocService.retrieveBloc(1L);

        // Assert
        assertNotNull(foundBloc);
        assertEquals("Bloc A", foundBloc.getNomBloc());
        verify(blocRepository, times(1)).findById(1L);
    }

    @Test
    void testAddBloc() {
        // Arrange
        Bloc bloc = Bloc.builder()
                .nomBloc("Bloc B")
                .capaciteBloc(100L)
                .build();
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        // Act
        Bloc result = blocService.addBloc(bloc);

        // Assert
        assertNotNull(result);
        assertEquals("Bloc B", result.getNomBloc());
        assertEquals(100L, result.getCapaciteBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testUpdateBloc() {
        // Arrange
        Bloc bloc = Bloc.builder()
                .idBloc(2L)
                .nomBloc("Bloc C")
                .capaciteBloc(150L)
                .build();
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        // Act
        Bloc updatedBloc = blocService.updateBloc(bloc);

        // Assert
        assertNotNull(updatedBloc);
        assertEquals("Bloc C", updatedBloc.getNomBloc());
        assertEquals(150L, updatedBloc.getCapaciteBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testRemoveBloc() {
        // Arrange
        long idBloc = 3L;
        doNothing().when(blocRepository).deleteById(idBloc);

        // Act
        blocService.removeBloc(idBloc);

        // Assert
        verify(blocRepository, times(1)).deleteById(idBloc);
    }

    @Test
    void testRetrieveNonExistentBloc() {
        // Arrange
        when(blocRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Bloc foundBloc = blocService.retrieveBloc(99L);

        // Assert
        assertNull(foundBloc);
        verify(blocRepository, times(1)).findById(99L);
        verifyNoMoreInteractions(blocRepository);
    }
}
