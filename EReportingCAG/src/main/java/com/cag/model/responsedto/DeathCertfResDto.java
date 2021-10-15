/**
 * 
 */
package com.cag.model.responsedto;

import org.springframework.stereotype.Component;

import com.cag.model.entity.DeathCertf;

import lombok.Data;

/**
 * @author aspak.avesh.sharif
 *
 */
@Data
@Component
public class DeathCertfResDto extends CommonResponseDto {

	private DeathCertf deathCertf;

}
