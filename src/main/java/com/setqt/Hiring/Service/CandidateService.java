package com.setqt.Hiring.Service;


import com.setqt.Hiring.Model.Candidate;
import com.setqt.Hiring.Model.Company;
import com.setqt.Hiring.Repository.CandidateRepository;
import com.setqt.Hiring.Repository.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
public class CandidateService {
    private final CandidateRepository candidateRepository;


    public CandidateService(CandidateRepository repo) {
        this.candidateRepository = repo;
    }

    public Candidate findById(Long id) {
        return this.candidateRepository.findById(id).orElse(null);
    }

}
