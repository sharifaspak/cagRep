/**
 * 
 */
package com.cag.model.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.Data;

/**
 * @author aspak.avesh.sharif
 *
 */
@Data
public class LeadSheet {
	
	@Id
	private String caseId;
	private String channel;
	private String fieldAgentName;
	private String fieldAgentstatus;
	private Date fieldAgentleadClosureDate;
	private String backendAgentName;
	private String backendAgentLeadClosuredate;
	private String backendAgentStatus;
	private String policyHolderName;
	private String proposalAddress;
	private String policyNumber;
	private Date leadRecievedDate;
	private String leadOwner;
	private String partnerName;
	private String leadStatus;
	private Date updatedDate;
	private String updatedBy;
	private String folderId;
	private String agencyName;
	private String agencyContact;
	private String agencyConclusion;
	private String agencyRemark;
		
}
