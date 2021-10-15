/**
 * 
 */
package com.cag.model.requestdto;

import java.util.List;

import com.cag.model.entity.Habit;
import com.cag.model.entity.OtherInsurance;
import com.cag.model.entity.PastMedicalHistory;

import lombok.Data;

/**
 * @author aspak.avesh.sharif
 *
 */
@Data
public class HabitsNMedHistReqDto {

	private String caseId;
	private String pastMedicalHistoryCheck;
	private List<PastMedicalHistory> pastMedHistList;
	private String pastHabitHistoryCheck;
	private List<Habit> habitList;
	private String otherInsuranceCheck;
	private List<OtherInsurance> otherInsuranceList;

}
