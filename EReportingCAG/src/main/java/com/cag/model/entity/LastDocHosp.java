/**
 * 
 */
package com.cag.model.entity;

import java.util.List;

import lombok.Data;

/**
 * @author aspak.avesh.sharif
 *
 */
@Data
public class LastDocHosp extends Audit {

	private boolean lastDocCheck;
	private List<LastDoctor> lastDocList;

}
