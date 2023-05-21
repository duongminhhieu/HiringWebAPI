package com.setqt.Hiring.Service.Company;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.setqt.Hiring.Service.Generic.GenericService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.setqt.Hiring.Model.Company;
import com.setqt.Hiring.Repository.CompanyRepository;

import jakarta.persistence.EntityNotFoundException;


@Service
public class CompanyService extends GenericService<Company> implements ICompany {


	public CompanyService(JpaRepository<Company, Long> genericRepository) {
		super(genericRepository);
	}
//
//	public Company findById(Long id) {
//		return this.repository.findById(id).orElse(null);
//	}
//
//	public void updateOne(long id, Company company) {
//	        if (repository.findById(id).isEmpty()) throw new EntityNotFoundException();
//	        repository.updateById(id,company) ;
//	        }
//
//	public List<Company> findAll() {
//		return repository.findAll().stream().sorted(Comparator.comparing(Company::getId)).toList();
//	}
//
//	public Company create(Company company) {
//		return repository.save(company);
//	}
//
//	public void deleteById(long id) {
//		repository.deleteById(id);
//	}
//
//	public void deleteAll() {
//		repository.deleteAll();
//	}

}
