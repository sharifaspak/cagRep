/**
 * 
 */
package com.cag.service;

import java.util.List;
import java.util.Optional;

import com.cag.model.entity.LeadDetails;

/**
 * @author aspak.avesh.sharif
 *
 */
public interface LeadDetailsService {

	Optional<LeadDetails> fetchLeadDetails(String id);

	LeadDetails updateLeadDetails(LeadDetails leadDetails);

	List<LeadDetails> updateLeadDetails(List<LeadDetails> leadDetailsList);
	
	

}
