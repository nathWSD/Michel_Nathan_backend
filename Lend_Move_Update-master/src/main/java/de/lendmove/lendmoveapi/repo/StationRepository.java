package de.lendmove.lendmoveapi.repo;

import de.lendmove.lendmoveapi.entity.Station;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationRepository extends JpaRepository<Station, Long>
{
    Page<Station> findAll(Pageable pageable);

    Optional<Station> findByName(String name);
}
