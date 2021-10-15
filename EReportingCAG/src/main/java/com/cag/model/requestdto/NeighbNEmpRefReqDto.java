/**
 * 
 */
package com.cag.model.requestdto;

import java.util.List;

import com.cag.model.entity.Colleague;
import com.cag.model.entity.Neighbour;

import lombok.Data;

/**
 * @author aspak.avesh.sharif
 *
 */
@Data
public class NeighbNEmpRefReqDto {

	String caseId;
	private boolean neighbourCheck;
	private List<Neighbour> neighbourList;
	private String hrCheck;
	private List<Colleague> colleagueList;
	private String colleagueHRSummary;

}
