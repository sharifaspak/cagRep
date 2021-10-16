package com.cag.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private AgentRepository agentRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Agent agent = agentRepository.findById(username).get();
		if (agent == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority(agent.getRole()));
		return new org.springframework.security.core.userdetails.User(agent.getUsername(), agent.getPassword(),
				grantedAuthorities);
	}

	public Agent save(Agent agent) {
		Agent newAgent = new Agent();
		newAgent.setUsername(agent.getUsername());
		newAgent.setRole(agent.getRole());
		newAgent.setAgency(agent.getAgency());
		newAgent.setPassword(bcryptEncoder.encode(agent.getPassword()));
		return agentRepository.save(newAgent);
	}
}