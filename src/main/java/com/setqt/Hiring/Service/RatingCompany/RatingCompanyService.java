package com.setqt.Hiring.Service.RatingCompany;

import com.setqt.Hiring.Model.RatingCompany;
import com.setqt.Hiring.Service.Generic.GenericService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class RatingCompanyService extends GenericService<RatingCompany> implements IRatingCompany {
    public RatingCompanyService(JpaRepository<RatingCompany, Long> genericRepository) {
        super(genericRepository);
    }
}
