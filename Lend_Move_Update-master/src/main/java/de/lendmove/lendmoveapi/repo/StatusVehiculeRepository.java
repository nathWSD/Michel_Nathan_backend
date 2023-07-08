package de.lendmove.lendmoveapi.repo;

import de.lendmove.lendmoveapi.entity.EnumRole;
import de.lendmove.lendmoveapi.entity.EnumStatusVehicule;
import de.lendmove.lendmoveapi.entity.Role;
import de.lendmove.lendmoveapi.entity.StatusVehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusVehiculeRepository extends JpaRepository<StatusVehicule, Long>
{
    Optional<StatusVehicule> findByName(EnumStatusVehicule name);
}
