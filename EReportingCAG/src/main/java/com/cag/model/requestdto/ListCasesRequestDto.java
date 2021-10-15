/**
 * 
 */
package com.cag.model.requestdto;

import java.util.Date;

import lombok.Data;
import lombok.Getter;

/**
 * @author aspak.avesh.sharif
 *
 */
@Data
@Getter
public class ListCasesRequestDto {
	
	private String caseId;
	private String channel;
	private String agentName;
	private String status;
	private String role;
	private Date from;
	private Date to;
	private int pageNumber;
	private int numberOfrecords;
	private String reason;
	

}
