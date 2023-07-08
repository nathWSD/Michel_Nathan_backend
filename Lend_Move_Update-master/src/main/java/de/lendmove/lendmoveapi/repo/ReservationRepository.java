package de.lendmove.lendmoveapi.repo;

import de.lendmove.lendmoveapi.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>
{
    Page<Reservation> findAll(Pageable pageable);

    List<Reservation> findAll();

    Optional<Reservation> findById(Long id);

    List<Reservation> findByUsername(String username);

    

}
