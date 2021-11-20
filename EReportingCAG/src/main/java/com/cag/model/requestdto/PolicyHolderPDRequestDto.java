/**
 * 
 */
package com.cag.model.requestdto;

import java.util.Date;

import lombok.Data;

/**
 * @author aspak.avesh.sharif
 *
 */
@Data
public class PolicyHolderPDRequestDto {

	private String caseId;
	private String policyNumber;
	private String policyHolderName;
	private String policyHolderAddress;
	private String policyHolderAddressTenure;
	private Date policyHolderDob;
	private String policyHolderOccupationType;
	private String policyHolderOccupationTenure;
	private String policyHolderOccupationName;
	private String policyHolderDesignation;
	private String policyHolderAnnualIncome;
	private String policyHolderEmpId;
	private String policyHoldereducationQualification;
	private Date policyHolderDod;
	private Date policyHolderLwd;
	private String policyHolderCauseOfDeath;
	private String policyHolderPlaceOfDeath;
	private Date policyHolderDoc;
	private String policyHolderPlaceOfCreamation;
	private String policyHolderStandardOfLiving;
	private String policyHolderPremiumAmt;
	private String policyHolderMaritalStatus;
	private String policyHolderOccupationDocuments;
	private Date policyIssuanceDate;
	private String policyHolderSumAssured;
	private String  insurancePurpose;
	private String proposalFormAddress;
	private String claimFormAddress;
}
