package de.lendmove.lendmoveapi.repo;

import de.lendmove.lendmoveapi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
 //   @Query("From Account a WHERE a.status like %:searchText%  OR a.firstname like  %:searchText% OR a.email like %:searchText% ORDER BY a.email DESC")
 //   Page<User> findAll(Pageable pageable, @Param("searchText") String searchText);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String username);


    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByPhonenumber (String phonenumber);
}
