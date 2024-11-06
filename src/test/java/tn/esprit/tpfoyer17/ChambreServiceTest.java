package tn.esprit.tpfoyer17;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.tpfoyer17.entities.Bloc;
import tn.esprit.tpfoyer17.entities.Chambre;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;
import tn.esprit.tpfoyer17.repositories.BlocRepository;
import tn.esprit.tpfoyer17.repositories.ChambreRepository;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;
import tn.esprit.tpfoyer17.services.impementations.ChambreService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Slf4j
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
        // Nettoyez les données avant chaque test
        chambreRepository.deleteAll();
        blocRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        // Supprimez toutes les données après chaque test pour garantir un état propre
        chambreRepository.deleteAll();
        blocRepository.deleteAll();
    }

    // Builder pour Chambre
    private Chambre createChambre(TypeChambre typeChambre) {
        return Chambre.builder()
                .typeChambre(typeChambre)
                .build();
    }

    // Builder pour Bloc
    private Bloc createBloc() {
        return Bloc.builder().build();
    }

    @Test
    @Order(1)
    void testRetrieveAllChambres() {
        // Given
        Chambre chambre1 = createChambre(TypeChambre.SIMPLE);
        Chambre chambre2 = createChambre(TypeChambre.DOUBLE);
        chambreRepository.save(chambre1);
        chambreRepository.save(chambre2);

        // When
        List<Chambre> result = chambreService.retrieveAllChambres();

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    @Order(2)
    void testAddChambre() {
        // Given
        Chambre chambre = createChambre(TypeChambre.SIMPLE);

        // When
        Chambre result = chambreService.addChambre(chambre);

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getIdChambre()); // Utilisez getId()
    }

    @Test
    @Order(3)
    void testUpdateChambre() {
        // Given
        Chambre chambre = createChambre(TypeChambre.SIMPLE);
        Chambre savedChambre = chambreRepository.save(chambre);
        savedChambre.setTypeChambre(TypeChambre.DOUBLE); // Modification

        // When
        Chambre result = chambreService.updateChambre(savedChambre);

        // Then
        Assertions.assertEquals(TypeChambre.DOUBLE, result.getTypeChambre());
    }

    @Test
    @Order(4)
    void testRetrieveChambre() {
        // Given
        Chambre chambre = createChambre(TypeChambre.SIMPLE);
        Chambre savedChambre = chambreRepository.save(chambre);

        // When
        Chambre result = chambreService.retrieveChambre(savedChambre.getIdChambre()); // Utilisez getId()

        // Then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(savedChambre.getIdChambre(), result.getIdChambre()); // Utilisez getId()
    }

    @Test
    @Order(5)
    void testAffecterChambresABloc() {
        // Given
        Bloc bloc = createBloc();
        blocRepository.save(bloc);

        Chambre chambre1 = createChambre(TypeChambre.SIMPLE);
        Chambre chambre2 = createChambre(TypeChambre.DOUBLE);
        chambreRepository.save(chambre1);
        chambreRepository.save(chambre2);

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
    @Order(6)
    void testUpdateChambreType() {
        // Given
        Chambre chambre = createChambre(TypeChambre.SIMPLE);
        Chambre savedChambre = chambreRepository.save(chambre);

        // When
        savedChambre.setTypeChambre(TypeChambre.DOUBLE);
        Chambre updatedChambre = chambreService.updateChambre(savedChambre);

        // Then
        Assertions.assertEquals(TypeChambre.DOUBLE, updatedChambre.getTypeChambre());
    }

    @Test
    @Order(7)
    void testAddMultipleChambresToBloc() {
        // Given
        Bloc bloc = createBloc();
        blocRepository.save(bloc);

        Chambre chambre1 = createChambre(TypeChambre.SIMPLE);
        Chambre chambre2 = createChambre(TypeChambre.DOUBLE);
        chambreRepository.save(chambre1);
        chambreRepository.save(chambre2);

        Set<Long> chambreIds = new HashSet<>();
        chambreIds.add(chambre1.getIdChambre());
        chambreIds.add(chambre2.getIdChambre());

        // When
        chambreService.affecterChambresABloc(new ArrayList<>(chambreIds), bloc.getIdBloc());

        // Then
        Assertions.assertEquals(bloc, chambreRepository.findById(chambre1.getIdChambre()).get().getBloc());
        Assertions.assertEquals(bloc, chambreRepository.findById(chambre2.getIdChambre()).get().getBloc());
    }

    @Test
    @Order(8)
    void testRetrieveNonExistentChambre() {
        // Given
        Long nonExistentId = 999L;

        // When & Then
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            chambreService.retrieveChambre(nonExistentId);
        });
    }

    @Test
    @Order(9)
    void testFindByTypeChambre() {
        Chambre chambre1 = createChambre(TypeChambre.SIMPLE);
        Chambre chambre2 = createChambre(TypeChambre.DOUBLE);
        chambreRepository.save(chambre1);
        chambreRepository.save(chambre2);

        List<Chambre> simpleChambres = chambreService.findByTypeChambre();

        Assertions.assertNotNull(simpleChambres);
        Assertions.assertTrue(simpleChambres.stream().allMatch(c -> c.getTypeChambre() == TypeChambre.SIMPLE));
    }

    @Test
    @Order(10)
    void testGetChambresParBlocEtType() {
        Bloc bloc = createBloc();
        blocRepository.save(bloc);

        Chambre chambre = createChambre(TypeChambre.DOUBLE);
        chambre.setBloc(bloc);
        chambreRepository.save(chambre);

        List<Chambre> result = chambreService.getChambresParBlocEtType(bloc.getIdBloc(), TypeChambre.DOUBLE);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(TypeChambre.DOUBLE, result.get(0).getTypeChambre());
    }
}
