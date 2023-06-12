package com.setqt.Hiring.Service.SavedJobPosting;

import java.util.List;

import org.springframework.stereotype.Service;

import com.setqt.Hiring.Model.SavedJobPosting;
import com.setqt.Hiring.Repository.SavedJobPostingRepository;
import com.setqt.Hiring.Service.Generic.GenericService;

@Service
public class SavedJobPostingService extends GenericService<SavedJobPosting> implements ISavedJobPosting {
    public SavedJobPostingService(SavedJobPostingRepository savedJobPostingRepository) {
        super(savedJobPostingRepository);
    }
    public List<SavedJobPosting> getByCandidateID(Long id){
    	return ((SavedJobPostingRepository) genericRepository).findByCandidateId(id);
    }
}
