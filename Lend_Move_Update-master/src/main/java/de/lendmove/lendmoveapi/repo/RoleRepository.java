package de.lendmove.lendmoveapi.repo;

import de.lendmove.lendmoveapi.entity.EnumRole;
import de.lendmove.lendmoveapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(EnumRole name);
}

