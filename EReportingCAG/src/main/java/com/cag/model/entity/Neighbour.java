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
public final class Neighbour {

	private String name;
	private String place;
	private String contactNumber;
	private Boolean laExpired;
	private String reasonOfExpiry;
	private String placeOfExpiry;
	private Date dateOfExpiry;

}
