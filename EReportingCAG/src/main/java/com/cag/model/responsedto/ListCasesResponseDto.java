/**
 * 
 */
package com.cag.model.responsedto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cag.model.entity.LeadSheet;

import lombok.Data;

/**
 * @author aspak.avesh.sharif
 *
 */
@Data
@Component
public class ListCasesResponseDto extends CommonResponseDto {

	private List<LeadSheet> caseList;

}
