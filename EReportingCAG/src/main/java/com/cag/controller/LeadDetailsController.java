/**
 * 
 */
package com.cag.controller;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cag.model.entity.LeadDetails;
import com.cag.model.requestdto.LeadDetailsRequestDto;
import com.cag.model.responsedto.LeadDetailsResponseDto;
import com.cag.security.JwtUserDetailsService;
import com.cag.service.LeadDetailsService;
import com.cag.utility.ApplicationConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * @author aspak.avesh.sharif
 *
 */

@RestController
@RequestMapping(value = "/leadDetails/")
@Slf4j
public class LeadDetailsController {

	@Autowired
	ModelMapper modelmapper;

	@Autowired
	LeadDetailsService leadDetailsService;

	@Autowired
	LeadDetailsResponseDto leadDetailsResponseDto;

	@GetMapping(value = "/fetchLeadDetails/{caseId}")
	public LeadDetailsResponseDto fetchLeadDetails(@PathVariable String caseId) {

		try {
		leadDetailsResponseDto.setLeadDetails(leadDetailsService.fetchLeadDetails(caseId).get());
		leadDetailsResponseDto.setStatuscode(ApplicationConstants.SUCCESS_CODE);
		leadDetailsResponseDto.setStatusMessage(ApplicationConstants.SUCCESS);
		return leadDetailsResponseDto;
		}
		catch(Exception e) {
			log.info("Invalid case id {} . Please retry with valid case Id",caseId);
			leadDetailsResponseDto.setStatuscode(ApplicationConstants.INVALID_CASE_ID_CODE);
			leadDetailsResponseDto.setStatusMessage(ApplicationConstants.INVALID_CASE_ID_MESSAGE);
			leadDetailsResponseDto.setErrorMessage(e.getMessage());
			return leadDetailsResponseDto;
		}
	}

	@PostMapping(value = "/saveLeadDetails", consumes = "application/json")
	public LeadDetailsResponseDto saveLeadDetails(@RequestBody LeadDetailsRequestDto leadDetailsRequestDto) {
		LeadDetails leadDetails = new LeadDetails();
		leadDetails = modelmapper.map(leadDetailsRequestDto, LeadDetails.class);
		leadDetails.setUpdatedBy(leadDetailsRequestDto.getAgentName());
		leadDetails.setUpdatedDate(new Date());
		leadDetailsResponseDto.setLeadDetails(leadDetailsService.updateLeadDetails(leadDetails));
		leadDetailsResponseDto.setStatuscode(ApplicationConstants.SUCCESS_CODE);
		leadDetailsResponseDto.setStatusMessage(ApplicationConstants.SUCCESS);
		return leadDetailsResponseDto;

	}

}
