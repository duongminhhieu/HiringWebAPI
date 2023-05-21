package com.setqt.Hiring.Service.Generic;

import com.setqt.Hiring.Repository.Generic.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GenericService<T> implements IGenericService<T> {

    protected GenericRepository<T> genericRepository;

    @Autowired
    public GenericService(GenericRepository<T> genericRepository) {
        this.genericRepository = genericRepository;
    }

    @Override
    public List<T> findAll() throws Exception {
        try {
            return genericRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public T save(T entity) throws Exception {
        try {
            return genericRepository.save(entity);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        try {
            genericRepository.deleteById(id);
        } catch (Exception e) {
            throw e;
        }
    }
}
