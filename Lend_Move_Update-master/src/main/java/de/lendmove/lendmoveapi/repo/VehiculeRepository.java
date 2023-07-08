package de.lendmove.lendmoveapi.repo;

import de.lendmove.lendmoveapi.entity.Vehicule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Long>
{
    Optional<Vehicule> findByName(String name);

    List<Vehicule> findAll();

    Page<Vehicule> findAll(Pageable pageable);



}
