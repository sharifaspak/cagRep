/**
 * 
 */
package com.cag.model.entity;

import lombok.Data;

/**
 * @author aspak.avesh.sharif
 *
 */
@Data
public final class Habit {

	private String habitName;
	private String habitDuration;
	private String habitFrequency;
	private YearsNMonths habitDuration_obj;
}
