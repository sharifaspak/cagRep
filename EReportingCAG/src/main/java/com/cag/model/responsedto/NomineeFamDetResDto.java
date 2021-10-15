/**
 * 
 */
package com.cag.model.responsedto;

import org.springframework.stereotype.Component;

import com.cag.model.entity.NomineeFamDts;

import lombok.Data;

/**
 * @author aspak.avesh.sharif
 *
 */
@Data
@Component
public class NomineeFamDetResDto extends CommonResponseDto {

	private NomineeFamDts nomineeFamDts;

}
