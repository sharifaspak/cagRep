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

import com.cag.model.entity.NeighbNEmpRef;
import com.cag.model.requestdto.NeighbNEmpRefReqDto;
import com.cag.model.responsedto.NeighbNEmpRefResDto;
import com.cag.security.JwtUserDetailsService;
import com.cag.service.NeighbNEmpRefService;
import com.cag.utility.ApplicationConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * @author aspak.avesh.sharif
 *
 */
@RestController
@RequestMapping(value = "neighbNEmpRef")
@Slf4j
public class NeighbNEmpRefController {

	@Autowired
	ModelMapper modelmapper;

	@Autowired
	NeighbNEmpRefService neighbNEmpRefService;

	@GetMapping(value = "/fetchNeighbNEmpRef/{caseId}")
	public NeighbNEmpRefResDto fetchNeighbNEmpRef(@PathVariable String caseId) {
		NeighbNEmpRefResDto responseDto = new NeighbNEmpRefResDto();
		try {

			responseDto.setNeighbNEmpRef(neighbNEmpRefService.fetchNeighbNEmpRef(caseId).get());
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

	@PostMapping(value = "/saveNeighbNEmpRef", consumes = "application/json")
	public NeighbNEmpRefResDto saveNeighbNEmpRef(@RequestBody NeighbNEmpRefReqDto requestDto) {
		NeighbNEmpRefResDto responseDto = new NeighbNEmpRefResDto();
		NeighbNEmpRef neighbNEmpRef = new NeighbNEmpRef();
		neighbNEmpRef = modelmapper.map(requestDto, NeighbNEmpRef.class);
		// policyHolderPD.setUpdatedBy(policyHolderPDRequestDto.getAgentName());
		neighbNEmpRef.setUpdatedDate(new Date());
		responseDto.setNeighbNEmpRef(neighbNEmpRefService.updateNeighbNEmpRef(neighbNEmpRef));
		responseDto.setStatuscode(ApplicationConstants.SUCCESS_CODE);
		responseDto.setStatusMessage(ApplicationConstants.SUCCESS);
		return responseDto;

	}
}
