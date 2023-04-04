package client;

public class LoginResponse {
	private String token;
	
	public LoginResponse() {
		
	}
	public LoginResponse(String s) {
		setToken(s);
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
