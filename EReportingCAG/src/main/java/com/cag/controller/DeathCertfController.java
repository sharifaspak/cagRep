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

import com.cag.model.entity.DeathCertf;
import com.cag.model.requestdto.DeathCertfReqDto;
import com.cag.model.responsedto.DeathCertfResDto;
import com.cag.service.DeathCertfService;
import com.cag.utility.ApplicationConstants;

/**
 * @author aspak.avesh.sharif
 *
 */
@RestController
@RequestMapping(value = "/deathCertf/")
public class DeathCertfController {

	private static final Logger LOG = LoggerFactory.getLogger(DeathCertfController.class);

	@Autowired
	ModelMapper modelmapper;

	@Autowired
	DeathCertfService deathCertfService;

	@GetMapping(value = "/fetchDeathCertf/{caseId}")
	public DeathCertfResDto fetchDeathCertf(@PathVariable String caseId) {
		DeathCertfResDto responseDto = new DeathCertfResDto();
		try {

			responseDto.setDeathCertf(deathCertfService.fetchDeathCertf(caseId).get());
			responseDto.setStatuscode(ApplicationConstants.SUCCESS_CODE);
			responseDto.setStatusMessage(ApplicationConstants.SUCCESS);
			return responseDto;
		} catch (Exception e) {
			LOG.info("Invalid case id {} . Please retry with valid case Id", caseId);
			responseDto.setStatuscode(ApplicationConstants.INVALID_CASE_ID_CODE);
			responseDto.setStatusMessage(ApplicationConstants.INVALID_CASE_ID_MESSAGE);
			responseDto.setErrorMessage(e.getMessage());
			return responseDto;
		}
	}

	@PostMapping(value = "/saveDeathCertf", consumes = "application/json")
	public DeathCertfResDto saveDeathCertf(@RequestBody DeathCertfReqDto requestDto) {
		DeathCertfResDto responseDto = new DeathCertfResDto();
		DeathCertf deathCertf = new DeathCertf();
		deathCertf = modelmapper.map(requestDto, DeathCertf.class);
		// policyHolderPD.setUpdatedBy(policyHolderPDRequestDto.getAgentName());
		deathCertf.setUpdatedDate(new Date());
		responseDto.setDeathCertf(deathCertfService.updateDeathCertf(deathCertf));
		responseDto.setStatuscode(ApplicationConstants.SUCCESS_CODE);
		responseDto.setStatusMessage(ApplicationConstants.SUCCESS);
		return responseDto;

	}

}
