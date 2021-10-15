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
public final class OtherInsurance {

	private String insuranceType;
	private String insurer;
	private String policyNumber;
	private Date policyIssuanceDate;
	private String sumAssured;

}
