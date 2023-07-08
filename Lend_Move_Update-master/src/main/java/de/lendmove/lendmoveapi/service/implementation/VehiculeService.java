package de.lendmove.lendmoveapi.service.implementation;

import de.lendmove.lendmoveapi.entity.Vehicule;
import de.lendmove.lendmoveapi.repo.VehiculeRepository;
import de.lendmove.lendmoveapi.service.IPageService;
import de.lendmove.lendmoveapi.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public  class VehiculeService implements IService<Vehicule>, IPageService<Vehicule> {


    @Autowired
    VehiculeRepository vehiculeRepository;

    @Override
    public Collection<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Optional<Vehicule> findById(Long id) {
        return vehiculeRepository.findById(id);
    }

    @Override
    public Boolean saveOrUpdate(Vehicule vehicule) {
        try {
            vehiculeRepository.saveAndFlush(vehicule);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            vehiculeRepository.deleteById(id);
            if(vehiculeRepository.findById(id).isPresent())
            {
                return false;
            }else
            {
                return true;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existByname(String name)
    {
        if(vehiculeRepository.findByName(name).get()!= null)
        {
            return true;
        }else
        {
            return false;
        }
    }

    @Override
    public Vehicule findByName(String name) {
        return vehiculeRepository.findByName(name).get();
    }

    @Override
    public Page<Vehicule> findAll(Pageable pageable, String searchText) {
        return vehiculeRepository.findAll(pageable);
    }

    @Override
    public Page<Vehicule> findAll(Pageable pageable) {
        return vehiculeRepository.findAll(pageable);
    }

}