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
public class DeathCertfReqDto {

	private String caseId;
	private String issuingAuthorityName;
	private String personMet;
	private String contactNumber;
	private Boolean deathCertfImage;
	private String policeStation;
	private String policeStationcontactNumber;
	private String policeStationAddress;
	private String policeStationobservation;
	private String caseGeography;
	private String anganWadiName;
	private String anaganWadiContact;
	private Boolean anaganWadiImage;
	private String anmName;
	private String anmContact;
	private Boolean anmImage;
	private String ashaName;
	private String ashaContact;
	private Boolean ashaImage;
	private String sarpanchName;
	private String sarpanchContact;
	private Boolean sarpanchImage;
	private String gramSachiveOrTalathiName;
	private String gramSachiveOrTalathiContact;
	private Boolean gramSachiveOrTalathiImage;

}
