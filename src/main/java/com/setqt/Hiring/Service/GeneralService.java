package com.setqt.Hiring.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.setqt.Hiring.Model.Company;
import com.setqt.Hiring.Repository.CompanyRepository;

import jakarta.persistence.EntityNotFoundException;


public class GeneralService<T> {
	private final JpaRepository<T,Long> repository;

	public GeneralService(JpaRepository<T, Long> repo) {
		this.repository = repo;
	}

	public Optional<?> findById(long id) {
		return Optional.ofNullable(repository.findById(id).orElse(null));
	}


//	public void updateOne(long id, T model) {
//	        if (repository.findById(id).isEmpty()) throw new EntityNotFoundException();
//	        repository.updateById(id,model) ;
//	        }

	public List<T> findAll() {
		return repository.findAll().stream().toList();
	}

	public T create(T model) {
		return repository.save(model);
	}

	public void deleteById(long id) {
		repository.deleteById(id);
	}

	public void deleteAll() {
		repository.deleteAll();
	}

	
}

