package com.cag.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AgentRepository agentRepository;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		System.out.println(authenticationRequest.getUsername());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token,userDetails.getAuthorities().toString()));
	}

	@RequestMapping(value = "/registerAgent", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody Agent user) throws Exception {
		return ResponseEntity.ok(userDetailsService.save(user));
	}
	
	@RequestMapping(value = "/listAgentsByAgency/{agencyName}", method = RequestMethod.GET)
	public ResponseEntity<?> listAgents(@PathVariable String agencyName) throws Exception {
		List<String> agentsList = agentRepository.findByAgency(agencyName).stream().map(a -> a.getUsername())
				.collect(Collectors.toList());
		return ResponseEntity.ok(agentsList);

	}


	@RequestMapping(value = "/retrieveAgent/{agentUsername}", method = RequestMethod.GET)
	public ResponseEntity<?> retrieveAgent(@PathVariable String agentUsername) throws Exception {
		return ResponseEntity.ok(agentRepository.findById(agentUsername));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}