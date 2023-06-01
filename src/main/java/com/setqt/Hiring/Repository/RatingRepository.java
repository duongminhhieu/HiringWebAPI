package com.setqt.Hiring.Repository;

import com.setqt.Hiring.Model.RatingCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RatingRepository extends JpaRepository<RatingCompany, Long> {
}
