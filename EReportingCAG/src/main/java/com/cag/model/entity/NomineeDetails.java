/**
 * 
 */
package com.cag.model.entity;

import java.util.Date;

import lombok.Data;

/**
 * @author aspak.avesh.sharif
 *
 */
@Data
public class NomineeDetails {
	
	private String nomineeName;
	private Date nomineeDob;
	private String nomineeContact;
	private String nomineeOccupation;
	private String nomineeAddress;
	private String residingTenure;
	private String residenceType;
	private String nomineeRelation;
	

}
