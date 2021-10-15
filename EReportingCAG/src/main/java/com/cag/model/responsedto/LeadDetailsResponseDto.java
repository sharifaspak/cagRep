/**
 * 
 */
package com.cag.model.responsedto;

import org.springframework.stereotype.Component;

import com.cag.model.entity.LeadDetails;

import lombok.Data;

/**
 * @author aspak.avesh.sharif
 *
 */
@Data
@Component
public class LeadDetailsResponseDto extends CommonResponseDto {
	
	private LeadDetails leadDetails;

}
