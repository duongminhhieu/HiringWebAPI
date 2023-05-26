package com.setqt.Hiring.Service.Report;

import com.setqt.Hiring.Model.Report;
import com.setqt.Hiring.Service.Generic.GenericService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ReportService extends GenericService<Report> implements IReport {
    public ReportService(JpaRepository<Report, Long> genericRepository) {
        super(genericRepository);
    }
}
