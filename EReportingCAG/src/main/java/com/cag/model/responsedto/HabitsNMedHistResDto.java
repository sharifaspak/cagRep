/**
 * 
 */
package com.cag.model.responsedto;

import org.springframework.stereotype.Component;

import com.cag.model.entity.Audit;
import com.cag.model.entity.HabitsNMedHist;

import lombok.Data;

/**
 * @author aspak.avesh.sharif
 *
 */
@Data
@Component
public class HabitsNMedHistResDto extends CommonResponseDto {
	
	private HabitsNMedHist habitsNMedHist;

}
