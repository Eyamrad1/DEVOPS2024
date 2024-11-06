package tn.esprit.tpfoyer17;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;
import tn.esprit.tpfoyer17.repositories.ChambreRepository;
import tn.esprit.tpfoyer17.services.impementations.ChambreService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class ChambreServiceTestMockito {

    @MockBean
    private ChambreRepository chambreRepository;

    @InjectMocks
    private ChambreService chambreService;

    private Chambre chambre;
    private Bloc bloc;

    @BeforeEach
    public void setUp() {
        // Initializing the objects using builder pattern
        chambre = Chambre.builder()
                .idChambre(1L)
                .numeroChambre(101L)
                .typeChambre(TypeChambre.SIMPLE)
                .build();

        bloc = Bloc.builder()
                .idBloc(1L)
                .nomBloc("Bloc A")
                .build();
    }

    @Test
    public void testRetrieveAllChambres() {
        // Using Mockito.any() for argument matching
        when(chambreRepository.findAll()).thenReturn(Arrays.asList(chambre));

        // When
        List<Chambre> chambres = chambreService.retrieveAllChambres();

        // Then
        assertNotNull(chambres);
        assertEquals(1, chambres.size());
        verify(chambreRepository, times(1)).findAll();
    }

    @Test
    public void testAddChambre() {
        // Using Mockito.any() to match any Chambre object passed to save
        when(chambreRepository.save(Mockito.any(Chambre.class))).thenReturn(chambre);

        // When
        Chambre savedChambre = chambreService.addChambre(chambre);

        // Then
        assertNotNull(savedChambre);
        assertEquals(chambre.getNumeroChambre(), savedChambre.getNumeroChambre());
        verify(chambreRepository, times(1)).save(Mockito.any(Chambre.class));
    }

    @Test
    public void testUpdateChambre() {
        // Mocking the save method using doReturn for updates
        doReturn(chambre).when(chambreRepository).save(Mockito.any(Chambre.class));

        // When
        Chambre updatedChambre = chambreService.updateChambre(chambre);

        // Then
        assertNotNull(updatedChambre);
        assertEquals(chambre.getTypeChambre(), updatedChambre.getTypeChambre());
        verify(chambreRepository, times(1)).save(Mockito.any(Chambre.class));
    }

    @Test
    public void testRetrieveChambre() {
        // Using Mockito.anyLong() for argument matching
        when(chambreRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(chambre));

        // When
        Chambre retrievedChambre = chambreService.retrieveChambre(1L);

        // Then
        assertNotNull(retrievedChambre);
        assertEquals(1L, retrievedChambre.getIdChambre());
        verify(chambreRepository, times(1)).findById(Mockito.anyLong());
    }

    @Test
    public void testAffecterChambresABloc() {
        List<Long> numChambres = Arrays.asList(101L);

        // Mocking findByNumeroChambreIn and saveAll
        when(chambreRepository.findByNumeroChambreIn(Mockito.anyList())).thenReturn(Arrays.asList(chambre));
        when(chambreRepository.saveAll(Mockito.anyList())).thenReturn(Arrays.asList(chambre));

        // When
        Bloc resultBloc = chambreService.affecterChambresABloc(numChambres, bloc.getIdBloc());

        // Then
        assertNotNull(resultBloc);
        assertEquals(bloc.getIdBloc(), resultBloc.getIdBloc());
        verify(chambreRepository, times(1)).findByNumeroChambreIn(Mockito.anyList());
        verify(chambreRepository, times(1)).saveAll(Mockito.anyList());
    }

    @Test
    public void testUpdateChambreType() {
        // Changing type of chambre for update
        chambre.setTypeChambre(TypeChambre.DOUBLE);

        // Mocking save method
        when(chambreRepository.save(Mockito.any(Chambre.class))).thenReturn(chambre);

        // When
        Chambre updatedChambre = chambreService.updateChambre(chambre);

        // Then
        assertEquals(TypeChambre.DOUBLE, updatedChambre.getTypeChambre());
        verify(chambreRepository, times(1)).save(Mockito.any(Chambre.class));
    }

    @Test
    public void testRetrieveNonExistentChambre() {
        // Mocking a non-existent chambre
        when(chambreRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> {
            chambreService.retrieveChambre(999L);
        });
    }


    @Test
    void testGetChambresParNomUniversite() {
        List<Chambre> chambres = new ArrayList<>();
        chambres.add(chambre);
        when(chambreRepository.findByBlocFoyerUniversiteNomUniversiteLike("UniversityName")).thenReturn(chambres);

        List<Chambre> result = chambreService.getChambresParNomUniversite("UniversityName");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(chambreRepository, times(1)).findByBlocFoyerUniversiteNomUniversiteLike("UniversityName");
    }

    @Test
    void testGetChambresParBlocEtType() {
        List<Chambre> chambres = new ArrayList<>();
        chambres.add(chambre);
        when(chambreRepository.findByBlocIdBlocAndTypeChambre(1L, TypeChambre.SIMPLE)).thenReturn(chambres);

        List<Chambre> result = chambreService.getChambresParBlocEtType(1L, TypeChambre.SIMPLE);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(chambreRepository, times(1)).findByBlocIdBlocAndTypeChambre(1L, TypeChambre.SIMPLE);
    }

    @Test
    void testGetChambresNonReserve() {
        List<Chambre> chambres = new ArrayList<>();
        chambres.add(chambre);
        when(chambreRepository.getChambresNonReserve()).thenReturn(chambres);

        chambreService.getChambresNonReserve();

        verify(chambreRepository, times(1)).getChambresNonReserve();
    }

}
