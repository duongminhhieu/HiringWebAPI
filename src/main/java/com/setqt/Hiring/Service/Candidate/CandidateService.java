package com.setqt.Hiring.Service.Candidate;


import com.setqt.Hiring.Model.Candidate;
import com.setqt.Hiring.Model.Company;
import com.setqt.Hiring.Repository.CandidateRepository;
import com.setqt.Hiring.Repository.CompanyRepository;
import com.setqt.Hiring.Service.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CandidateService extends GeneralService<Candidate> implements ICandidate {
    private final CandidateRepository candidateRepository;
    @Autowired
    public CandidateService(CandidateRepository candidateRepository) {
        super(candidateRepository);
        this.candidateRepository = candidateRepository;
    }
    @Override
    public Candidate save(Candidate entity) throws Exception {
        return null;
    }

    @Override
    public void delete(Long id) throws Exception {

    }
}
