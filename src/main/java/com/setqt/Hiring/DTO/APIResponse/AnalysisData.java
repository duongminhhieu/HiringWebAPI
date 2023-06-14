package com.setqt.Hiring.DTO.APIResponse;



public class AnalysisData {
	private Long view;
	private Long submited;
	private Long vacancy;
	private Long passed;

	public AnalysisData() {
		
	}
	public AnalysisData(Long view, Long vacancy,  Long submited,Long passed) {
		super();
		this.view = view;
		this.submited = submited;
		this.vacancy = vacancy;
		this.passed = passed;
	}
//	public AnalysisData(String view, String vacancy,String passed,  String submited) {
//		super();
//		this.view = 3;
//		this.submited = 3;
//		this.vacancy = 3;
//		this.passed = 3;
//	}

	public Long getView() {
		return view;
	}

	public void setView(Long view) {
		this.view = view;
	}

	public Long getSubmited() {
		return submited;
	}

	public void setSubmited(Long submited) {
		this.submited = submited;
	}

	public Long getvacancy() {
		return vacancy;
	}

	public void setvacancy(Long vacancy) {
		this.vacancy = vacancy;
	}

	public Long getPassed() {
		return passed;
	}

	public void setPassed(Long passed) {
		this.passed = passed;
	}

}
