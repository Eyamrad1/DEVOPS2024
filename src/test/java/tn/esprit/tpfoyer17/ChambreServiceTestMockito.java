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
import org.springframework.boot.test.context.SpringBootTest;
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



public class ChambreServiceTestMockito {

    @Mock
    private ChambreRepository chambreRepository;  // Simule le repository pour les tests

    @InjectMocks
    private ChambreService chambreService;  // Injecte le mock ChambreRepository dans le service à tester

    private Chambre chambre;  // Instance de chambre pour les tests
    private Bloc bloc;  // Instance de bloc pour les tests


    @BeforeEach
    public void setUp() {
        // Initialisation des objets pour chaque test
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
        // Simulation du comportement du repository
        when(chambreRepository.findAll()).thenReturn(Arrays.asList(chambre));

        // Appel de la méthode à tester
        List<Chambre> chambres = chambreService.retrieveAllChambres();

        // Vérifications
        assertNotNull(chambres);  // Vérifie que la liste n'est pas nulle
        assertEquals(1, chambres.size());  // Vérifie qu'il y a une seule chambre dans la liste
        verify(chambreRepository).findAll();  // Vérifie que la méthode findAll a bien été appelée
    }

    /**
     * Teste l'ajout d'une chambre.
     */
    @Test
    public void testAddChambre() {
        // Simulation du comportement du repository
        when(chambreRepository.save(chambre)).thenReturn(chambre);

        // Appel de la méthode à tester
        Chambre savedChambre = chambreService.addChambre(chambre);

        // Vérifications
        assertNotNull(savedChambre);  // Vérifie que la chambre sauvegardée n'est pas nulle
        assertEquals(chambre.getNumeroChambre(), savedChambre.getNumeroChambre());  // Vérifie le numéro de la chambre
        verify(chambreRepository).save(chambre);  // Vérifie que la méthode save a bien été appelée
    }

    /**
     * Teste la mise à jour d'une chambre.
     */
    @Test
    public void testUpdateChambre() {
        // Simulation du comportement du repository
        when(chambreRepository.save(chambre)).thenReturn(chambre);

        // Appel de la méthode à tester
        Chambre updatedChambre = chambreService.updateChambre(chambre);

        // Vérifications
        assertNotNull(updatedChambre);  // Vérifie que la chambre mise à jour n'est pas nulle
        assertEquals(chambre.getTypeChambre(), updatedChambre.getTypeChambre());  // Vérifie le type de la chambre
        verify(chambreRepository).save(chambre);  // Vérifie que la méthode save a bien été appelée
    }

    /**
     * Teste la récupération d'une chambre par son ID.
     */
    @Test
    public void testRetrieveChambre() {
        // Simulation du comportement du repository
        when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre));

        // Appel de la méthode à tester
        Chambre retrievedChambre = chambreService.retrieveChambre(1L);

        // Vérifications
        assertNotNull(retrievedChambre);  // Vérifie que la chambre récupérée n'est pas nulle
        assertEquals(1L, retrievedChambre.getIdChambre());  // Vérifie que l'ID correspond à celui de la chambre
        verify(chambreRepository).findById(1L);  // Vérifie que la méthode findById a bien été appelée
    }

    /**
     * Teste l'affectation de chambres à un bloc.
     */


    /**
     * Teste la mise à jour du type d'une chambre.
     */
    @Test
    public void testUpdateChambreType() {
        chambre.setTypeChambre(TypeChambre.DOUBLE);
        // Simulation du comportement du repository
        when(chambreRepository.save(chambre)).thenReturn(chambre);

        // Appel de la méthode à tester
        Chambre updatedChambre = chambreService.updateChambre(chambre);

        // Vérifications
        assertEquals(TypeChambre.DOUBLE, updatedChambre.getTypeChambre());  // Vérifie que le type de la chambre a bien été mis à jour
        verify(chambreRepository).save(chambre);  // Vérifie que la méthode save a bien été appelée
    }


}
