package de.lendmove.lendmoveapi.controller;

import de.lendmove.lendmoveapi.entity.*;
import de.lendmove.lendmoveapi.payload.VehiculeRequest;
import de.lendmove.lendmoveapi.payload.response.MessageResponse;
import de.lendmove.lendmoveapi.repo.StatusVehiculeRepository;
import de.lendmove.lendmoveapi.service.implementation.VehiculeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth/vehicule")
public class VehiculeController
{
    @Autowired
    VehiculeService vehiculeService;

    @Autowired
    StatusVehiculeRepository statusVehiculeRepository;




    @GetMapping("/all")  //
  //  @PreAuthorize(" hasRole('USER') or hasRole('WORKER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllCar(
                           @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10000") int size,
                           @RequestParam(defaultValue = "name") String sortBy, @RequestParam(defaultValue = "asc") String sortDir)
    {
        try {
            List<Vehicule> vehicules = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size,
                    sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());
            Page<Vehicule> pageVehicules = null;

                pageVehicules = vehiculeService.findAll(paging);


            vehicules = pageVehicules.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("vehicules", vehicules);
            response.put("currentPage", pageVehicules.getNumber());
            response.put("totalItems", pageVehicules.getTotalElements());
            response.put("totalPages", pageVehicules.getTotalPages());
            response.put("size", pageVehicules.getSize());
            response.put("sort", pageVehicules.getSort());




            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCarById(@PathVariable(value = "id") String id) {
        System.out.println(id + "------ car id----------");
        Map<String, Object> response = new HashMap<>();
        try {
            if (id != null && vehiculeService.findById(Long.valueOf(id)).isPresent()) {
                Vehicule vehicule = vehiculeService.findById(Long.valueOf(id)).get();
                response.put("found", true);
                response.put("vehicule", vehicule);
            } else {
                response.put("found", false);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", "Error finding car by ID");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    @PostMapping("/addVehicule")
    @PreAuthorize("hasRole('WORKER') or hasRole('ADMIN')")
    public ResponseEntity<?> addVehicule(@Valid @RequestBody VehiculeRequest vehiculeRequest) {
        Map<String, Object> response = new HashMap<>();
        Vehicule newVehicule = new Vehicule();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();


        newVehicule.setName(vehiculeRequest.getName());
        newVehicule.setDescription(vehiculeRequest.getDescription());
        newVehicule.setCreatedBy(auth.getName());
        newVehicule.setActualStation(vehiculeRequest.getActualStation());
        newVehicule.setImage(vehiculeRequest.getImage());
        newVehicule.setCreatedAt(new Date().toString());

        newVehicule.setCategory(vehiculeRequest.getCategory());
        newVehicule.setCity(vehiculeRequest.getCity());

        newVehicule.setLinkUrl(vehiculeRequest.getLinkUrl());



        Set<String> strStatus = vehiculeRequest.getStatus();
        Set<StatusVehicule> statusVehicules = new HashSet<>();

        if (strStatus!=null && strStatus.isEmpty()) {
            StatusVehicule statusVehicule = statusVehiculeRepository.findByName(EnumStatusVehicule.CREATED)
                    .orElseThrow(() -> new RuntimeException("Error: Status vehicule is not found."));
            statusVehicules.add(statusVehicule);
        } else {
            strStatus.forEach(status -> {
                switch (status) {
                    case "in_station":
                        StatusVehicule inStation = statusVehiculeRepository.findByName(EnumStatusVehicule.IN_STATION)
                                .orElseThrow(() -> new RuntimeException("Error: In_station is not found."));
                        statusVehicules.add(inStation);

                        break;
                    case "reserved":
                        StatusVehicule reserved = statusVehiculeRepository.findByName(EnumStatusVehicule.RESERVED)
                                .orElseThrow(() -> new RuntimeException("Error: Reserved is not found."));
                        statusVehicules.add(reserved);

                        break;
                    case "in_usage":
                        StatusVehicule inUsage = statusVehiculeRepository.findByName(EnumStatusVehicule.IN_USAGE)
                                .orElseThrow(() -> new RuntimeException("Error: in_usage is not found."));
                        statusVehicules.add(inUsage);

                        break;
                    default:
                        StatusVehicule stationVehicule = statusVehiculeRepository.findByName(EnumStatusVehicule.CREATED)
                                .orElseThrow(() -> new RuntimeException("Error: In_station is not found."));
                        statusVehicules.add(stationVehicule);
                }
            });
        }

        newVehicule.setStatus(statusVehicules);
        if(vehiculeService.saveOrUpdate(newVehicule))
        {
            return ResponseEntity.ok(new MessageResponse("Vehicule registered successfully!"));
        }else
        {
            response.put("error", "Error by added a vehicule");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/calculatePrice")
    public ResponseEntity<?> determinePrice(@PathVariable String startTime , String EndTime ,
                                            String tarif, String reservationDateEnd, String reservationDateStart)
    {
        // take values and if the tarif option is traif1 do a particular calculation and for tarif 2 and 3 too
        return null;
    }






}
