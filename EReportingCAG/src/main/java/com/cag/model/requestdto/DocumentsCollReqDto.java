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
public class DocumentsCollReqDto {

	private String caseId;
	private String rationParivarCard;
	private String voterIdCollected;
	private String panCollected;
	private String aadharCollected;
	private String nomineeRelationProofCollected;
	private String affidavitCollected;
	private String kycDocumentListLA;
	private String pastNPresentMedRecords;
	private String incomeProofDoc;
	private String newsPapCutCol;
	private String newsPaperRemark;
}
