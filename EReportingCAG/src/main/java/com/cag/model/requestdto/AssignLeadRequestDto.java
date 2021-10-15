/**
 * 
 */
package com.cag.model.requestdto;

import lombok.Data;
import lombok.Getter;

/**
 * @author aspak.avesh.sharif
 *
 */
@Data
@Getter
public class AssignLeadRequestDto {

	private String caseId;
	private String agentName;
	private String reason;

}
