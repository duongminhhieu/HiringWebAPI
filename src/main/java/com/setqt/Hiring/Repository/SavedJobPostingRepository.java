package com.setqt.Hiring.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.setqt.Hiring.Model.SavedJobPosting;

@Repository
public interface SavedJobPostingRepository extends JpaRepository<SavedJobPosting, Long> {
	  @Query("SELECT sjp FROM SavedJobPosting sjp WHERE sjp.candidate.id = :candidateId")
	    List<SavedJobPosting> findByCandidateId(@Param("candidateId") Long candidateId);

}
