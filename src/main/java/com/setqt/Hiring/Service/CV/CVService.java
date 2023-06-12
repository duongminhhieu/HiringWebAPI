package com.setqt.Hiring.Service.CV;

import java.util.List;

import org.springframework.stereotype.Service;

import com.setqt.Hiring.Model.CV;
import com.setqt.Hiring.Repository.CvRepository;
import com.setqt.Hiring.Service.Generic.GenericService;

@Service
public class CVService extends GenericService<CV> implements ICV {
    public CVService(CvRepository cvRepository) {
        super(cvRepository);
    }
    
    public List<CV> getCVByUsername(String name) {
    	return  ((CvRepository) genericRepository).getCvByUsername(name);
    }
    
}
