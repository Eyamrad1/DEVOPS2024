package tn.esprit.tpfoyer17;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.services.interfaces.IBlocService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class testBlocJUnit {

    @Autowired
    private BlocRepository blocRepository;

    @Autowired
    private IBlocService blocService;

    @BeforeEach
    void setUp() {
        blocRepository.deleteAll();  // Clear the database before each test
    }

    @Test
    void retrieveBlocs_ShouldReturnListOfBlocs() {
        Bloc bloc1 = new Bloc();
        Bloc bloc2 = new Bloc();
        blocRepository.save(bloc1);
        blocRepository.save(bloc2);

        List<Bloc> blocs = blocService.retrieveBlocs();

        assertNotNull(blocs);
        assertEquals(2, blocs.size());
    }

    @Test
    void addBloc_ShouldSaveAndReturnBloc() {
        Bloc bloc = new Bloc();
        bloc.setNomBloc("Bloc A");
        bloc.setCapaciteBloc(100);

        Bloc savedBloc = blocService.addBloc(bloc);

        assertNotNull(savedBloc);
        assertEquals("Bloc A", savedBloc.getNomBloc());
    }

    @Test
    void updateBloc_ShouldUpdateAndReturnBloc() {
        Bloc bloc = new Bloc();
        bloc.setNomBloc("Bloc A");
        bloc.setCapaciteBloc(100);
        Bloc savedBloc = blocService.addBloc(bloc);

        // Modify some attribute of bloc
        savedBloc.setNomBloc("Updated Bloc");
        Bloc updatedBloc = blocService.updateBloc(savedBloc);

        assertNotNull(updatedBloc);
        assertEquals("Updated Bloc", updatedBloc.getNomBloc());
    }

    @Test
    void retrieveBloc_ShouldReturnBlocIfExists() {
        Bloc bloc = new Bloc();
        bloc.setNomBloc("Bloc A");
        Bloc savedBloc = blocRepository.save(bloc);

        Bloc retrievedBloc = blocService.retrieveBloc(savedBloc.getIdBloc());

        assertNotNull(retrievedBloc);
        assertEquals(savedBloc.getIdBloc(), retrievedBloc.getIdBloc());
    }

    @Test
    void retrieveBloc_ShouldReturnNullIfNotFound() {
        Bloc retrievedBloc = blocService.retrieveBloc(1L);
        assertNull(retrievedBloc);
    }

    @Test
    void removeBloc_ShouldDeleteBloc() {
        Bloc bloc = new Bloc();
        bloc.setNomBloc("Bloc A");
        Bloc savedBloc = blocRepository.save(bloc);

        blocService.removeBloc(savedBloc.getIdBloc());

        assertFalse(blocRepository.findById(savedBloc.getIdBloc()).isPresent());
    }

    @Test
    void findByFoyerIdFoyer_ShouldReturnListOfBlocs() {
        // Set up Foyer object for association
        Foyer foyer = new Foyer();
        // Save Foyer in its respective repository if necessary

        Bloc bloc1 = new Bloc();
        bloc1.setNomBloc("Bloc A");
        bloc1.setFoyer(foyer);

        Bloc bloc2 = new Bloc();
        bloc2.setNomBloc("Bloc B");
        bloc2.setFoyer(foyer);

        blocRepository.save(bloc1);
        blocRepository.save(bloc2);

        List<Bloc> blocs = blocService.findByFoyerIdFoyer(foyer.getIdFoyer());

        assertNotNull(blocs);
        assertEquals(2, blocs.size());
    }

    @Test
    void findByChambresIdChambre_ShouldReturnBloc() {
        // Add a test for chambres, assuming chambre association is available in the Bloc
        // Set up necessary Chambre object if needed and associate it with Bloc
    }
}
