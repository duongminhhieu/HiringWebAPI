package com.setqt.Hiring.Model;

import java.io.Serializable;


public class CandidatePK implements Serializable{
	private Long user;

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public CandidatePK(Long user) {
		super();
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CandidatePK other = (CandidatePK) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
}
//
//public class CandidatePK implements Serializable{
//	private User user;
//	
//	public User getUser() {
//		return user;
//	}
//	
//	public void setUser(User user) {
//		this.user = user;
//	}
//	
//	public CandidatePK(User user) {
//		super();
//		this.user = user;
//	}
//	
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((user == null) ? 0 : user.hashCode());
//		return result;
//	}
//	
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		CandidatePK other = (CandidatePK) obj;
//		if (user == null) {
//			if (other.user != null)
//				return false;
//		} else if (!user.equals(other.user))
//			return false;
//		return true;
//	}
//	
//}
