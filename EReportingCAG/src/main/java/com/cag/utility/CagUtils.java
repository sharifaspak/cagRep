/**
 * 
 */
package com.cag.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

/**
 * @author aspak.avesh.sharif
 *
 */
@Slf4j
public class CagUtils {

	public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {

		return (dateToConvert != null) ? dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
				: null;
	}
	public static String calculateAge(Date birthDate, Date dateOfDeath) {
		log.info("birthDate and  dateOfDeath is {} {}",birthDate,dateOfDeath);
		if ((birthDate != null) && (dateOfDeath != null)) {
			LocalDate birthDateL = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate currentDateL = dateOfDeath.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			return ""+Period.between(birthDateL, currentDateL).getYears()+" years "+Period.between(birthDateL, currentDateL).getMonths()+" months "+Period.between(birthDateL, currentDateL).getDays()+" days "; 
		} else {
			return "error in age caculation";
		}
	}
	

}
