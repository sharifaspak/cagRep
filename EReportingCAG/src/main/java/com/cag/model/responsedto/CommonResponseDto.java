/**
 * 
 */
package com.cag.model.responsedto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author aspak.avesh.sharif
 *
 */
@Getter
@Data
public abstract class CommonResponseDto {

	
	String trxId;
	String statuscode;
	String statusMessage;
	String errorMessage;
	
}
