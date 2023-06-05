package com.setqt.Hiring.Service.CV;

import com.setqt.Hiring.Service.Generic.GenericService;
import com.setqt.Hiring.Model.CV;
import com.setqt.Hiring.Repository.CvRepository;
import org.springframework.stereotype.Service;

@Service
public class CVService extends GenericService<CV> implements ICV {
    public CVService(CvRepository cvRepository) {
        super(cvRepository);
    }
}
