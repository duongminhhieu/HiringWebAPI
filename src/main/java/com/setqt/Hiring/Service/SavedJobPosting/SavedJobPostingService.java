package com.setqt.Hiring.Service.SavedJobPosting;

import com.setqt.Hiring.Model.SavedJobPosting;
import com.setqt.Hiring.Repository.SavedJobPostingRepository;
import com.setqt.Hiring.Service.Generic.GenericService;
import org.springframework.stereotype.Service;

@Service
public class SavedJobPostingService extends GenericService<SavedJobPosting> implements ISavedJobPosting {
    public SavedJobPostingService(SavedJobPostingRepository savedJobPostingRepository) {
        super(savedJobPostingRepository);
    }
}
