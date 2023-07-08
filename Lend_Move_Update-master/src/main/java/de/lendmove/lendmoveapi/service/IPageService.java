package de.lendmove.lendmoveapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPageService<T> extends  IService<T>
{
    Page<T> findAll (Pageable pageable, String searchText);

    Page<T> findAll (Pageable pageable);
}