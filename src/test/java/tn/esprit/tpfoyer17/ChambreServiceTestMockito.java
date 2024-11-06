package tn.esprit.tpfoyer17;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;
import tn.esprit.tpfoyer17.repositories.ChambreRepository;
import tn.esprit.tpfoyer17.services.impementations.ChambreService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ChambreServiceTestMockito {

    @Mock
    private ChambreRepository chambreRepository;

    @InjectMocks
    private ChambreService chambreService;

    private Chambre chambre;
    private Bloc bloc;

    @BeforeEach
    public void setUp() {
        // Initialize objects before each test
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
        when(chambreRepository.findAll()).thenReturn(Arrays.asList(chambre));

        List<Chambre> chambres = chambreService.retrieveAllChambres();

        assertNotNull(chambres);
        assertEquals(1, chambres.size());
        verify(chambreRepository).findAll();
    }

    @Test
    public void testAddChambre() {
        when(chambreRepository.save(chambre)).thenReturn(chambre);

        Chambre savedChambre = chambreService.addChambre(chambre);

        assertNotNull(savedChambre);
        assertEquals(chambre.getNumeroChambre(), savedChambre.getNumeroChambre());
        verify(chambreRepository).save(chambre);
    }

    @Test
    public void testUpdateChambre() {
        when(chambreRepository.save(chambre)).thenReturn(chambre);

        Chambre updatedChambre = chambreService.updateChambre(chambre);

        assertNotNull(updatedChambre);
        assertEquals(chambre.getTypeChambre(), updatedChambre.getTypeChambre());
        verify(chambreRepository).save(chambre);
    }

    @Test
    public void testRetrieveChambre() {
        when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre));

        Chambre retrievedChambre = chambreService.retrieveChambre(1L);

        assertNotNull(retrievedChambre);
        assertEquals(1L, retrievedChambre.getIdChambre());
        verify(chambreRepository).findById(1L);
    }

    @Test
    public void testAffecterChambresABloc() {
        List<Long> numChambres = Arrays.asList(101L);
        when(chambreRepository.findByNumeroChambreIn(numChambres)).thenReturn(Arrays.asList(chambre));
        when(chambreRepository.saveAll(Arrays.asList(chambre))).thenReturn(Arrays.asList(chambre));

        Bloc resultBloc = chambreService.affecterChambresABloc(numChambres, bloc.getIdBloc());

        assertNotNull(resultBloc);
        assertEquals(bloc.getIdBloc(), resultBloc.getIdBloc());
        verify(chambreRepository).findByNumeroChambreIn(numChambres);
        verify(chambreRepository).saveAll(Arrays.asList(chambre));
    }

    @Test
    public void testUpdateChambreType() {
        chambre.setTypeChambre(TypeChambre.DOUBLE);
        when(chambreRepository.save(chambre)).thenReturn(chambre);

        Chambre updatedChambre = chambreService.updateChambre(chambre);

        assertEquals(TypeChambre.DOUBLE, updatedChambre.getTypeChambre());
        verify(chambreRepository).save(chambre);
    }

    @Test
    public void testRetrieveNonExistentChambre() {
        when(chambreRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> chambreService.retrieveChambre(999L));
        verify(chambreRepository).findById(999L);
    }
}
