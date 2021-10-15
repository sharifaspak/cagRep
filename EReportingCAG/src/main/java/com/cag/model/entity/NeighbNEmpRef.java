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
public class NeighbNEmpRef extends Audit {

	private boolean neighbourCheck;
	private List<Neighbour> neighbourList;
	private String hrCheck;
	private List<Colleague> colleagueList;
	private String colleagueHRSummary;

}
