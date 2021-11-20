/**
 * 
 */
package com.cag.model.entity;

import java.util.List;

import lombok.Data;

/**
 * @author aspak.avesh.sharif
 *
 */
@Data
public class NomineeFamDts extends Audit {
	private String metSalutation;
	private String metPersonName;
	private String metPersonRelationship;
	private String metPersonCauseOfDeath;
	private List<NomineeDetails> nomineeDetailsList;
	private List<FamilyDetails> familyDetailsList;

}
