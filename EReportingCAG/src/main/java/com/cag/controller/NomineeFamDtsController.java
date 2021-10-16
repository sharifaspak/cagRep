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

import com.cag.model.entity.NomineeFamDts;
import com.cag.model.requestdto.NomineeFamDtsReqDto;
import com.cag.model.responsedto.NomineeFamDetResDto;
import com.cag.security.JwtUserDetailsService;
import com.cag.service.NomineeFamDetService;
import com.cag.utility.ApplicationConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * @author aspak.avesh.sharif
 *
 */
@RestController
@RequestMapping(value = "/nomineeFamDetails/")
@Slf4j
public class NomineeFamDtsController {

	
	@Autowired
	ModelMapper modelmapper;

	@Autowired
	NomineeFamDetService nomineeFamDetService;

	@GetMapping(value = "/fetchNomineeFamDetails/{caseId}")
	public NomineeFamDetResDto fetchNomineeFamDetails(@PathVariable String caseId) {
		NomineeFamDetResDto responseDto = new NomineeFamDetResDto();
		try {

			responseDto.setNomineeFamDts(nomineeFamDetService.fetchNomineeFamDts(caseId).get());
			responseDto.setStatuscode(ApplicationConstants.SUCCESS_CODE);
			responseDto.setStatusMessage(ApplicationConstants.SUCCESS);
			return responseDto;
		} catch (Exception e) {
			log.info("Invalid case id {} . Please retry with valid case Id", caseId);
			responseDto.setStatuscode(ApplicationConstants.INVALID_CASE_ID_CODE);
			responseDto.setStatusMessage(ApplicationConstants.INVALID_CASE_ID_MESSAGE);
			responseDto.setErrorMessage(e.getMessage());
			return responseDto;
		}
	}

	@PostMapping(value = "/saveNomineeFamDetails", consumes = "application/json")
	public NomineeFamDetResDto saveNomineeFamDetails(@RequestBody NomineeFamDtsReqDto requestDto) {
		NomineeFamDetResDto responseDto = new NomineeFamDetResDto();
		NomineeFamDts nomineeFamDts = new NomineeFamDts();
		nomineeFamDts = modelmapper.map(requestDto, NomineeFamDts.class);
		// policyHolderPD.setUpdatedBy(policyHolderPDRequestDto.getAgentName());
		nomineeFamDts.setUpdatedDate(new Date());
		responseDto.setNomineeFamDts(nomineeFamDetService.updateNomineeFamDts(nomineeFamDts));
		responseDto.setStatuscode(ApplicationConstants.SUCCESS_CODE);
		responseDto.setStatusMessage(ApplicationConstants.SUCCESS);
		return responseDto;

	}

}
