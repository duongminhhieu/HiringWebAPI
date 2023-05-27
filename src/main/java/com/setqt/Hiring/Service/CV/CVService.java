package com.setqt.Hiring.Service.CV;

import com.setqt.Hiring.Model.CV;
import com.setqt.Hiring.Repository.CvRepository;
import com.setqt.Hiring.Service.Generic.GenericService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CVService extends GenericService<CV> implements ICV {
    public CVService(CvRepository cvRepository) {
        super(cvRepository);
    }
}
