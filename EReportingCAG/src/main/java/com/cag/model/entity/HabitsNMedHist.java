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
public class HabitsNMedHist extends Audit {

	private String pastMedicalHistoryCheck;
	private List<PastMedicalHistory> pastMedHistList;
	private String pastHabitHistoryCheck;
	private List<Habit> habitList;
	private String otherInsuranceCheck;
	private List<OtherInsurance> otherInsuranceList;

}
