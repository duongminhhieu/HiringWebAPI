
package com.setqt.Hiring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.setqt.Hiring.Model.Employer;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {

}
