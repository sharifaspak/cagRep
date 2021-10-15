/**
 * 
 */
package com.cag.model.requestdto;

import lombok.Data;

/**
 * @author aspak.avesh.sharif
 *
 */
@Data
public class LeadDetailsRequestDto {

	private String caseId;
	private String policyNumber;
	private String nomineeAddress;
	private String nomineeContact;
	private String insuranceCompany;
	private String laName;
	private String investigationType;
	private String state;
	private String city;
	private String agentName; 
}
