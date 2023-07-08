package de.lendmove.lendmoveapi.service.implementation;

import de.lendmove.lendmoveapi.entity.Reservation;
import de.lendmove.lendmoveapi.entity.Vehicule;
import de.lendmove.lendmoveapi.repo.ReservationRepository;
import de.lendmove.lendmoveapi.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService implements IService<Reservation> {

    @Autowired
    ReservationRepository reservationRepository;


    @Override
    public Collection<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Page<Reservation> findAll(Pageable pageable)
    {
        return reservationRepository.findAll(pageable);
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return reservationRepository.findById(id);
    }

    @Override
    public Boolean saveOrUpdate(Reservation reservation) {
        try {
            reservationRepository.saveAndFlush(reservation);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try{
            reservationRepository.deleteById(id);
            if(reservationRepository.findById(id).isPresent())
            {
                reservationRepository.deleteById(id);
                return true;
            }else
            {
                return true;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }


    public List<Reservation> findAllReservationByUsername(String username)
    {
        List<Reservation> allReservation = reservationRepository.findAll();
        List<Reservation> userReservation = new ArrayList<>();
        if(!allReservation.isEmpty())
        {
            for(Reservation value : allReservation)
            {
                if(value.getUsername().equals(username))
                {
                    userReservation.add(value);
                }
            }
        }
        return  userReservation;
    }

    public List<Reservation> findAllActivUserReservation(String username)
    {
        List<Reservation> userReservation = findAllReservationByUsername(username);
        List<Reservation> userActivReservation = new ArrayList<>();

        if(!userReservation.isEmpty())
        {
            for(Reservation value: userReservation)
            {
                if(value.getStatus().equals("ACTIV"))
                {
                    userActivReservation.add(value);
                }
            }
        }
        return userActivReservation;
    }


    public List<Reservation> findAllActivUserReservation(String username, boolean avaible)
    {
        List<Reservation> userReservation = findAllReservationByUsername(username);
        List<Reservation> userActivReservation = new ArrayList<>();
        if(!userReservation.isEmpty())
        {
            for(Reservation value: userReservation)
            {
                if(value.isAvailable())
                {
                    userActivReservation.add(value);
                }
            }
        }
        return userActivReservation;
    }

    @Override
    public Reservation findByName(String name) {
        return null;
    }
}
