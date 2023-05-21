
package com.setqt.Hiring.Repository;

import com.setqt.Hiring.Repository.Generic.GenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.setqt.Hiring.Model.Employer;

@Repository
public interface EmployerRepository extends GenericRepository<Employer> {

}
