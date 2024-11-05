package tn.esprit.tpfoyer17;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.entities.Reservation;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.repositories.ChambreRepository;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;
import tn.esprit.tpfoyer17.services.impementations.ChambreService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@DataJpaTest
public class ChambreServiceTest {

    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private BlocRepository blocRepository;

    @Autowired
    private UniversiteRepository universiteRepository;

    private ChambreService chambreService;

    @BeforeEach
    void setUp() {
        chambreService = new ChambreService(chambreRepository, blocRepository, universiteRepository);
        chambreRepository.deleteAll(); // Nettoyez les données avant chaque test
        blocRepository.deleteAll();
    }
    @AfterEach
    void tearDown() {
        // Supprimez toutes les données après chaque test pour garantir un état propre
        chambreRepository.deleteAll();
        blocRepository.deleteAll();
    }
    @Test
    void testRetrieveAllChambres() {
        // Given
        Chambre chambre1 = new Chambre();
        Chambre chambre2 = new Chambre();
        chambreRepository.save(chambre1);
        chambreRepository.save(chambre2);

        // When
        List<Chambre> result = chambreService.retrieveAllChambres();

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void testAddChambre() {
        // Given
        Chambre chambre = new Chambre();

        // When
        Chambre result = chambreService.addChambre(chambre);

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getIdChambre()); // Utilisez getId()
    }

    @Test
    void testUpdateChambre() {
        // Given
        Chambre chambre = new Chambre();
        Chambre savedChambre = chambreRepository.save(chambre);
        savedChambre.setTypeChambre(TypeChambre.DOUBLE); // Modification

        // When
        Chambre result = chambreService.updateChambre(savedChambre);

        // Then
        Assertions.assertEquals(TypeChambre.DOUBLE, result.getTypeChambre());
    }

    @Test
    void testRetrieveChambre() {
        // Given
        Chambre chambre = new Chambre();
        Chambre savedChambre = chambreRepository.save(chambre);

        // When
        Chambre result = chambreService.retrieveChambre(savedChambre.getIdChambre()); // Utilisez getId()

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(savedChambre.getIdChambre(), result.getIdChambre()); // Utilisez getId()
    }

    @Test
    void testAffecterChambresABloc() {
        // Given
        Bloc bloc = new Bloc();
        blocRepository.save(bloc);
        Chambre chambre1 = new Chambre();
        Chambre chambre2 = new Chambre();
        chambreRepository.save(chambre1);
        chambreRepository.save(chambre2);

        // Assurez-vous que les chambres ont des états initiaux corrects
        List<Long> numChambres = List.of(chambre1.getIdChambre(), chambre2.getIdChambre()); // Utilisez getId()

        // When
        Bloc result = chambreService.affecterChambresABloc(numChambres, bloc.getIdBloc()); // Utilisez getId()

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(bloc.getIdBloc(), result.getIdBloc()); // Utilisez getId()
        Assertions.assertEquals(bloc, chambre1.getBloc());
        Assertions.assertEquals(bloc, chambre2.getBloc());
    }
   @Test
    void testUpdateChambreType() {
        // Given
        Chambre chambre = new Chambre();
        chambre.setTypeChambre(TypeChambre.SIMPLE);
        Chambre savedChambre = chambreRepository.save(chambre);

        // When
        savedChambre.setTypeChambre(TypeChambre.DOUBLE);
        Chambre updatedChambre = chambreService.updateChambre(savedChambre);

        // Then
        Assertions.assertEquals(TypeChambre.DOUBLE, updatedChambre.getTypeChambre());
    }
}
