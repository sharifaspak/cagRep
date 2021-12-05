/**
 * 
 */
package com.cag.model.entity;

import lombok.Data;

/**
 * @author aspak.avesh.sharif
 *
 */
@Data
public final class PastMedicalHistory {

	private String diseasaeName;
	private String duration;
	private String treatmentDocName;
	private String treatmentPlaceName;
	private String treatmentLocation;
	private YearsNMonths duration_obj;
}
