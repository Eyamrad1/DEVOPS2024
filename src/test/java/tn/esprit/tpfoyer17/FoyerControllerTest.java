package tn.esprit.tpfoyer17;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.tpfoyer17.controllers.FoyerController;
import tn.esprit.tpfoyer17.entities.Foyer;
import tn.esprit.tpfoyer17.services.interfaces.IFoyerService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;


import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class FoyerControllerTest {
 private MockMvc mockMvc;

 @Mock
 private IFoyerService iFoyerService;

 @InjectMocks
 private FoyerController foyerController;

 private Foyer foyer;


 @BeforeEach
 public void setUp() {
  mockMvc = MockMvcBuilders.standaloneSetup(foyerController).build();
  foyer = new Foyer();
  foyer.setIdFoyer(1L);
  foyer.setNomFoyer("Test Foyer");
 }


 @Test
 void testRetrieveAllFoyers() throws Exception {
  when(iFoyerService.retrieveAllFoyers()).thenReturn(List.of(foyer));

  mockMvc.perform(get("/api/foyers/retrieveAllFoyers"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0].nomFoyer").value("Test Foyer"));
 }

 @Test
 void testAddFoyer() throws Exception {
  when(iFoyerService.addFoyer(any(Foyer.class))).thenReturn(foyer);

  mockMvc.perform(post("/api/foyers/addFoyer")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(asJsonString(foyer)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.nomFoyer").value("Test Foyer"));
 }

 @Test
 void testUpdateFoyer() throws Exception {
  when(iFoyerService.updateFoyer(any(Foyer.class))).thenReturn(foyer);

  mockMvc.perform(put("/api/foyers/updateFoyer")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(asJsonString(foyer)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.nomFoyer").value("Test Foyer"));
 }

 @Test
 void testRetrieveFoyer() throws Exception {
  when(iFoyerService.retrieveFoyer(1L)).thenReturn(foyer);

  mockMvc.perform(get("/api/foyers/retrieveFoyer/{idFoyer}", 1L))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.nomFoyer").value("Test Foyer"));
 }

 @Test
 void testRemoveFoyer() throws Exception {
  doNothing().when(iFoyerService).removeFoyer(1L);

  // Update the URL to match the one in the controller
  mockMvc.perform(delete("/api/foyers/removeFoyer/{idFoyer}", 1L))
          .andExpect(status().isOk());

  verify(iFoyerService, times(1)).removeFoyer(1L);
 }



 @Test
 void testAjouterFoyerEtAffecterAUniversite() throws Exception {
  when(iFoyerService.ajouterFoyerEtAffecterAUniversite(any(Foyer.class), eq(1L))).thenReturn(foyer);

  mockMvc.perform(put("/api/foyers/ajouterFoyerEtAffecterAUniversite/{idUniversite}", 1L)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(asJsonString(foyer)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.nomFoyer").value("Test Foyer"));
 }

 // Helper method to convert object to JSON string
 private static String asJsonString(Object obj) {
  try {
   return new ObjectMapper().writeValueAsString(obj);
  } catch (Exception e) {
   throw new RuntimeException(e);
  }
 }



}

