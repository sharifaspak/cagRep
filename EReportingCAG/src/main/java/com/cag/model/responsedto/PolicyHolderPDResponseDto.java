/**
 * 
 */
package com.cag.model.responsedto;

import org.springframework.stereotype.Component;

import com.cag.model.entity.PolicyHolderPD;

import lombok.Data;

/**
 * @author aspak.avesh.sharif
 *
 */
@Data
@Component
public class PolicyHolderPDResponseDto extends CommonResponseDto {

	private PolicyHolderPD policyHolderPD;

}
