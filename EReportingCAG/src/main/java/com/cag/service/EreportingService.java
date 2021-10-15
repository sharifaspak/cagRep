/**
 * 
 */
package com.cag.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cag.model.entity.LeadSheet;
import com.cag.model.requestdto.ListCasesRequestDto;

/**
 * @author aspak.avesh.sharif
 *
 */

public interface EreportingService {

	List<LeadSheet> listCases(ListCasesRequestDto listCasesRequestDto);

	List<LeadSheet> uploadCases(List<LeadSheet> casesList);
	
	List<LeadSheet> fetchLead(String id);

	List<LeadSheet> updateLead(LeadSheet leadtoUpdate);

	String generateReport(String caseId);
	

}
