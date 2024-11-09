package tn.esprit.tpfoyer17.services.impementations;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.entities.Universite;
import tn.esprit.tpfoyer17.repositories.FoyerRepository;
import tn.esprit.tpfoyer17.repositories.UniversiteRepository;
import tn.esprit.tpfoyer17.services.interfaces.IUniversiteService;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UniversiteService implements IUniversiteService {
    UniversiteRepository universiteRepository;
    FoyerRepository foyerRepository;
    @Override
    public List<Universite> retrieveAllUniversities() {
        return (List<Universite>) universiteRepository.findAll();
    }

    @Override
    public Universite addUniversity(Universite u) {
        return universiteRepository.save(u);
    }

    @Override
    public Universite updateUniversity(Universite u) {
        return universiteRepository.save(u);
    }

    @Override
    public Universite retrieveUniversity(long idUniversity) {
        return universiteRepository.findById(idUniversity).orElse(null);
    }


    @Override
    public Universite desaffecterFoyerAUniversite( long idUniversite) {
        Universite universite = universiteRepository.findById(idUniversite).orElse(null);
       // Foyer foyer = foyerRepository.findById(idFoyer).orElse(null);
        universite.setFoyer(null);
        return  universiteRepository.save(universite);
    }

    @Transactional
    public Universite affecterFoyerAUniversite(long idFoyer, String nomUniversite) {
        Foyer foyer = foyerRepository.findById(idFoyer).orElse(null);
        Universite universite = universiteRepository.findByNomUniversiteLike(nomUniversite);
        universite.setFoyer(foyer);
        return universiteRepository.save(universite);

    }


    // Nouvelle méthode de suppression
    @Override
    public void deleteUniversity(long idUniversity) {
        Universite universite = universiteRepository.findById(idUniversity).orElse(null);
        if (universite != null) {
            // Si un foyer est associé, on le désaffecte avant de supprimer l'université
            if (universite.getFoyer() != null) {
                universite.setFoyer(null);
                universiteRepository.save(universite);
            }
            universiteRepository.deleteById(idUniversity);
            log.info("Université avec l'ID {} supprimée avec succès.", idUniversity);
        } else {
            log.warn("Université avec l'ID {} introuvable.", idUniversity);
        }
    }
}
