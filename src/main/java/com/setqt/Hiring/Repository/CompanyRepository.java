package com.setqt.Hiring.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.setqt.Hiring.Model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

	@Query(value = "UPDATE Company com"
			+ "SET com.name =?2.getName()"
			+"WHERE Company.id = ?1", nativeQuery = true)
	void updateById(Long id,Company com);

	
	@Query("SELECT c FROM Company c ORDER BY c.rate DESC")
    List<Company> findTop6ByRating();
	
	

//	void getCompanyFromUserID(Long id,Company com);
	
	
	
}
