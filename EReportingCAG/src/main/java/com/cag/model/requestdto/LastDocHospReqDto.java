/**
 * 
 */
package com.cag.model.requestdto;

import java.util.List;

import com.cag.model.entity.LastDoctor;

import lombok.Data;

/**
 * @author aspak.avesh.sharif
 *
 */
@Data
public class LastDocHospReqDto {

	private String caseId;
	private boolean lastDocCheck;
	private List<LastDoctor> lastDocList;

}
