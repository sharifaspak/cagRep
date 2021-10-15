/**
 * 
 */
package com.cag.model.entity;

import java.util.Date;

import lombok.Data;

/**
 * @author aspak.avesh.sharif
 *
 */
@Data
public final class LastDoctor {

	private String name;
	private String placeName;
	private String address;
	private String contactNumber;
	private Date fromDate;
	private Date toDate;
	private String observation;
	private String documentsCollected;

}
