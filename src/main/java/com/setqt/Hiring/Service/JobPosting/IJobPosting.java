package com.setqt.Hiring.Service.JobPosting;

import com.setqt.Hiring.DTO.APIResponse.SearchResponse;
import com.setqt.Hiring.Model.JobPosting;
import com.setqt.Hiring.Service.Generic.IGenericService;

import java.util.List;

public interface IJobPosting extends IGenericService<JobPosting> {
//	public List<JobPosting> findJobPostingWithDescription(String title,String add) ;
    public List<SearchResponse> search(String title, String add);
}
