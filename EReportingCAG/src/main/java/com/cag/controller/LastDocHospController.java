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

import com.cag.model.entity.LastDocHosp;
import com.cag.model.requestdto.LastDocHospReqDto;
import com.cag.model.responsedto.LastDocHospResDto;
import com.cag.service.LastDocHospService;
import com.cag.utility.ApplicationConstants;

/**
 * @author aspak.avesh.sharif
 *
 */
@RestController
@RequestMapping(value = "/lastDocHosp/")
public class LastDocHospController {

	private static final Logger LOG = LoggerFactory.getLogger(LastDocHospController.class);

	@Autowired
	ModelMapper modelmapper;

	@Autowired
	LastDocHospService lastDocHospService;

	@GetMapping(value = "/fetchLastDocHosp/{caseId}")
	public LastDocHospResDto fetchLastDocHosp(@PathVariable String caseId) {
		LastDocHospResDto responseDto = new LastDocHospResDto();
		try {

			responseDto.setLastDocHosp(lastDocHospService.fetchLastDocHosp(caseId).get());
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

	@PostMapping(value = "/saveLastDocHosp", consumes = "application/json")
	public LastDocHospResDto saveLastDocHosp(@RequestBody LastDocHospReqDto requestDto) {
		LastDocHospResDto responseDto = new LastDocHospResDto();
		LastDocHosp lastDocHosp = new LastDocHosp();
		lastDocHosp = modelmapper.map(requestDto, LastDocHosp.class);
		// policyHolderPD.setUpdatedBy(policyHolderPDRequestDto.getAgentName());
		lastDocHosp.setUpdatedDate(new Date());
		responseDto.setLastDocHosp(lastDocHospService.updateLastDocHosp(lastDocHosp));
		responseDto.setStatuscode(ApplicationConstants.SUCCESS_CODE);
		responseDto.setStatusMessage(ApplicationConstants.SUCCESS);
		return responseDto;

	}

}
