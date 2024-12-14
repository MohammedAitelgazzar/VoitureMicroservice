package com.app.controllers;


import com.app.clients.ClientService;
import com.app.clients.VoitureService;
import com.app.entities.Client;
import com.app.entities.Voiture;
import com.app.repositories.VoitureRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VoitureController {

    @Autowired
    VoitureRepository voitureRepository;

    @Autowired
    VoitureService voitureService;

    @Autowired
    ClientService clientService;

    @GetMapping(value = "/voitures", produces = {"application/json"})
    public ResponseEntity<Object> findAll() {
        try {
            List<Voiture> voitures = voitureRepository.findAll();
            voitures.forEach(voiture -> {
                try {
                    voiture.setClient(clientService.clientById(voiture.getIdclient()));
                } catch (Exception e) {
                }
            });
            return ResponseEntity.ok(voitures);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching voitures: " + e.getMessage());
        }
    }

    @GetMapping("/voitures/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        try {
            Voiture voiture = voitureRepository.findById(id)
                    .orElseThrow(() -> new Exception("Voiture introuvable"));
            voiture.setClient(clientService.clientById(voiture.getIdclient()));
            return ResponseEntity.ok(voiture);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Voiture not found with ID: " + id);
        }
    }

    @GetMapping("/voitures/client/{id}")
    public ResponseEntity<List<Voiture>> findByClient(@PathVariable Long id) {
        try {
            Client client = clientService.clientById(id);
            if (client != null) {
                List<Voiture> voitures = voitureRepository.findByIdclient(id);
                voitures.forEach(voiture -> voiture.setClient(client));
                return ResponseEntity.ok(voitures);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/voitures/{clientId}")
    public ResponseEntity<Object> save(@PathVariable Long clientId, @RequestBody Voiture voiture) {
        try {
            Client client = clientService.clientById(clientId);

            if (client != null) {
                voiture.setClient(client);

                voiture.setIdclient(clientId);
                voiture.setClient(client);
                Voiture savedVoiture = voitureService.enregistrerVoiture(voiture);

                return ResponseEntity.ok(savedVoiture);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Client not found with ID: " + clientId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving voiture: " + e.getMessage());
        }
    }
}





//
//import com.app.VoitureServiceApplication;
//import com.app.clients.ClientService;
//import com.app.entities.Client;
//import com.app.entities.Voiture;
//import com.app.repositories.VoitureRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//@RestController
//public class VoitureController {
//
//@Autowired
//    VoitureRepository voitureRepository;
//
//
//   // VoitureService voitureService;
//
//@Autowired
//    ClientService clientService;
//
//    @GetMapping(value = "/voitures", produces = {"application/json"})
//    public ResponseEntity<Object> findAll() {
//        try {
//            System.out.println("Début de la récupération des voitures...");
//            List<Voiture> voitures = voitureRepository.findAll();
//            System.out.println("Nombre de voitures trouvées : " + voitures.size());
//            for (Voiture v : voitures) {
//                System.out.println("Voiture : " + v.getMarque() + " " + v.getModele());
//            }
//            return ResponseEntity.ok(voitures);
//        } catch (Exception e) {
//            System.err.println("Erreur lors de la récupération des voitures : " + e.getMessage());
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error fetching voitures: " + e.getMessage());
//        }
//    }
//    @GetMapping("/voitures/{id}")
//    public ResponseEntity<Object> findById(@PathVariable Long id) {
//        try {
//            Voiture voiture = voitureRepository.findById(id)
//                    .orElseThrow(() -> new Exception("Voiture Introuvable"));
//
//            // Fetch the client details using the clientService
//            voiture.setClient(clientService.clientById(voiture.getClientId()));
//
//            return ResponseEntity.ok(voiture);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("Voiture not found with ID: " + id);
//        }
//    }
//    @GetMapping("/voitures/client/{id}")
//    public ResponseEntity<List<Voiture>> findByClient(@PathVariable Long id) {
//        try {
//            Client client = clientService.clientById(id);
//            if (client != null) {
//                List<Voiture> voitures = voitureRepository.findByClientId(id);
//                return ResponseEntity.ok(voitures);
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//    @PostMapping("/voitures/{clientId}")
//    public ResponseEntity<Object> save(@PathVariable Long clientId, @RequestBody Voiture voiture) {
//        try {
//            // Check if client exists using the ClientService
//            Client client = clientService.clientById(clientId);
//
//            if (client != null) {
//                // Set the client object in the Voiture object
//                voiture.setClient(client);
//
//                // Save the Voiture with the associated Client
//                voiture.setClientId(clientId);
//                Voiture savedVoiture = voitureRepository.save(voiture);
//                return ResponseEntity.ok(savedVoiture);
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body("Client not found with ID: " + clientId);
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error saving voiture: " + e.getMessage());
//        }
//    }
//    @PutMapping("/voitures/{id}")
//    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Voiture updatedVoiture) {
//        try {
//            Voiture existingVoiture = voitureRepository.findById(id)
//                    .orElseThrow(() -> new Exception("Voiture not found with id: " + id));
//
//            // Update the existing voiture with new values
//            if (updatedVoiture.getMarque() != null) {
//                existingVoiture.setMarque(updatedVoiture.getMarque());
//            }
//            if (updatedVoiture.getModele() != null) {
//                existingVoiture.setModele(updatedVoiture.getModele());
//            }
//            if (updatedVoiture.getMatricule() != null) {
//                existingVoiture.setMatricule(updatedVoiture.getMatricule());
//            }
//
//            // Save the updated Voiture
//            Voiture savedVoiture = voitureRepository.save(existingVoiture);
//            return ResponseEntity.ok(savedVoiture);
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error updating voiture: " + e.getMessage());
//        }
//    }
//
//}