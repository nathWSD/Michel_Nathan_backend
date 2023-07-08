package de.lendmove.lendmoveapi.controller;


import de.lendmove.lendmoveapi.entity.Station;
import de.lendmove.lendmoveapi.repo.StationRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/station")
public class StationController
{
    @Autowired
    StationRepository stationRepository;

    @GetMapping
    public ResponseEntity<?> getAll()
    {
        Map<String, Object> response = new HashMap<>();

        List<Station> stations = new ArrayList<>();
        try {
            stations = stationRepository.findAll();
            response.put("stations", stations);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e)
        {
            response.put("error", "Error by getting all stations");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> addStation(@RequestParam Station station)
    {
        Map<String, Object> response = new HashMap<>();
        try
        {
            if( station!=null && stationRepository.findByName(station.getName()).get()==null)
            {
                stationRepository.save(station);
                response.put("message", "Station added");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }else
            {
                response.put("error", "Station alreay exist or another error.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        }catch (Exception e)
        {
            response.put("error", "error by added a station");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
