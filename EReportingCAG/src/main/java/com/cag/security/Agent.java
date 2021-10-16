/**
 * 
 */
package com.cag.security;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * @author aspak.avesh.sharif
 *
 */
@Data
public class Agent {
	@Id
	private String username;
	private String password;
	private String agency;
	private String role;

}
