package de.lendmove.lendmoveapi.utils;

import de.lendmove.lendmoveapi.entity.*;
import de.lendmove.lendmoveapi.repo.RoleRepository;
import de.lendmove.lendmoveapi.repo.StatusVehiculeRepository;
import de.lendmove.lendmoveapi.repo.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class InitValue
{
        @Autowired
        private RoleRepository roleRepository;

        @Autowired
        private StatusVehiculeRepository statusVehiculeRepository;

        @Autowired
        private UserRepository userRepository;

        @PostConstruct
        public void init(){
            initRole();
            initStatusVehicule();

        }

        private void initRole()
        {
            if(roleRepository.findAll().isEmpty())
            {
                roleRepository.saveAndFlush(new Role(EnumRole.ROLE_USER));
                roleRepository.saveAndFlush(new Role(EnumRole.ROLE_WORKER));
                roleRepository.saveAndFlush(new Role(EnumRole.ROLE_ADMIN));
            }
        }


        private void initStatusVehicule()
        {
             if(statusVehiculeRepository.findAll().isEmpty())
            {
                statusVehiculeRepository.saveAndFlush(new StatusVehicule(EnumStatusVehicule.IN_STATION));
                statusVehiculeRepository.saveAndFlush(new StatusVehicule(EnumStatusVehicule.CREATED));
                statusVehiculeRepository.saveAndFlush(new StatusVehicule(EnumStatusVehicule.RESERVED));
                statusVehiculeRepository.saveAndFlush(new StatusVehicule(EnumStatusVehicule.IN_USAGE));
            }
        }
}
