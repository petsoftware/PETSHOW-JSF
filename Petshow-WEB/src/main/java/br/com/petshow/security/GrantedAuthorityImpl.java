package br.com.petshow.security;

import org.springframework.security.core.GrantedAuthority;

public class GrantedAuthorityImpl implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 214447432224113614L;
	private String role = "";
	public GrantedAuthorityImpl(String role) {
		// TODO Auto-generated constructor stub
		setRole(role);
	}
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return getRole();
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

}
