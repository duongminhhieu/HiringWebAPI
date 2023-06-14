package com.setqt.Hiring.Service.CV;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.setqt.Hiring.DTO.APIResponse.AnalysisData;
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
    public Optional<AnalysisData> analysData(String name) {
    	AnalysisData data=  ((CvRepository) genericRepository).getAnalysData(name);
    	Optional<AnalysisData> result = null;
    	return result.of(data);
    }
    
    
}
