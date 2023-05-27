package com.setqt.Hiring.Repository;

import com.setqt.Hiring.Model.SavedJobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedJobPostingRepository extends JpaRepository<SavedJobPosting, Long> {
}
