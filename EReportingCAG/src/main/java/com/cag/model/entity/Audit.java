/**
 * 
 */
package com.cag.model.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.Data;

/**
 * @author aspak.avesh.sharif
 *
 */
@Data
public abstract class Audit {
	@Id
	private String caseId;
	private String updatedBy;
	private Date updatedDate;
	private String caseStatus;
	private String caseOwner;
}
