/**
 * 
 */
package com.cag.model.requestdto;

import java.util.List;

import com.cag.model.entity.FamilyDetails;
import com.cag.model.entity.NomineeDetails;

import lombok.Data;

/**
 * @author aspak.avesh.sharif
 *
 */
@Data
public class NomineeFamDtsReqDto {

	private String caseId;
	private String metSalutation;
	private String metPersonName;
	private String metPersonRelationship;
	private String metPersonCauseOfDeath;
	private String metPersonObservation;
	private List<NomineeDetails> nomineeDetailsList;
	private List<FamilyDetails> familyDetailsList;

}
