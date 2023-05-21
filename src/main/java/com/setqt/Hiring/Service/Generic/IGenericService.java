package com.setqt.Hiring.Service.Generic;

import java.util.List;

public interface IGenericService <T>{
    List<T> findAll()  throws Exception;
    T save(T entity) throws Exception;
    void delete(Long id) throws Exception;
}
