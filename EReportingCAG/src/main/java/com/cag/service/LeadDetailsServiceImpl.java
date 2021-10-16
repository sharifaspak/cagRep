/**
 * 
 */
package com.cag.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cag.model.entity.LeadDetails;
import com.cag.repository.LeadDetailsRepository;
import com.cag.security.JwtUserDetailsService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author aspak.avesh.sharif
 *
 */
@Service
@Slf4j
public class LeadDetailsServiceImpl implements LeadDetailsService {

	@Autowired
	LeadDetailsRepository leadDetailsRepository;

	@Override
	public Optional<LeadDetails> fetchLeadDetails(String id) {

		return leadDetailsRepository.findById(id);
	}

	@Override
	public LeadDetails updateLeadDetails(LeadDetails leadDetails) {

		return leadDetailsRepository.save(leadDetails);
	}

	@Override
	public List<LeadDetails> updateLeadDetails(List<LeadDetails> leadDetailsList) {

		return leadDetailsRepository.saveAll(leadDetailsList);
	}

}
