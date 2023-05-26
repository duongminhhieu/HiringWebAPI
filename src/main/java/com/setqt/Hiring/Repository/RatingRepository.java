package com.setqt.Hiring.Repository;

import com.setqt.Hiring.Model.RatingCompany;
import com.setqt.Hiring.Model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RatingRepository extends JpaRepository<RatingCompany, Long> {
}
