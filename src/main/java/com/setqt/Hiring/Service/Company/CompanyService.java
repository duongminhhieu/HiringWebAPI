package com.setqt.Hiring.Service.Company;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.setqt.Hiring.Service.Generic.GenericService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.setqt.Hiring.Model.Company;
import com.setqt.Hiring.Repository.CompanyRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class CompanyService extends GenericService<Company> implements ICompany {
	
	@Autowired
	public CompanyService(JpaRepository<Company, Long> genericRepository) {
		super(genericRepository);
	}
	
	public List<Company> findTop6ByRating() {
		return ((CompanyRepository) genericRepository).findTop6ByRating();
		
	}
}
