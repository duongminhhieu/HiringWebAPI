package com.setqt.Hiring.Repository;

import com.setqt.Hiring.Model.CV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvRepository extends JpaRepository<CV, Long> {

}
