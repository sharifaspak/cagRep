/**
 * 
 */
package com.cag.controller;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cag.model.entity.HabitsNMedHist;
import com.cag.model.requestdto.HabitsNMedHistReqDto;
import com.cag.model.responsedto.HabitsNMedHistResDto;
import com.cag.service.HabitsNMedHistService;
import com.cag.utility.ApplicationConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * @author aspak.avesh.sharif
 *
 */

@RestController
@RequestMapping(value = "/habitsNMedHistory/")
@Slf4j
public class HabitsNMedHisController {

	@Autowired
	HabitsNMedHistService habitsNMedHistService;

	@Autowired
	ModelMapper modelmapper;

	@GetMapping(value = "/fetchHabitsNMedHist/{caseId}")
	public HabitsNMedHistResDto fetchHabitsNMedHist(@PathVariable String caseId) {
		HabitsNMedHistResDto responseDto = new HabitsNMedHistResDto();
		try {

			responseDto.setHabitsNMedHist(habitsNMedHistService.fetchHabitsNMedHist(caseId).get());
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

	@PostMapping(value = "/saveHabitsNMedHist", consumes = "application/json")
	public HabitsNMedHistResDto saveHabitsNMedHist(@RequestBody HabitsNMedHistReqDto requestDto) {
		HabitsNMedHistResDto responseDto = new HabitsNMedHistResDto();
		HabitsNMedHist habitsNMedHist = new HabitsNMedHist();
		habitsNMedHist = modelmapper.map(requestDto, HabitsNMedHist.class);
		// policyHolderPD.setUpdatedBy(policyHolderPDRequestDto.getAgentName());
		habitsNMedHist.setUpdatedDate(new Date());
		responseDto.setHabitsNMedHist(habitsNMedHistService.updateHabitsNMedHist(habitsNMedHist));
		responseDto.setStatuscode(ApplicationConstants.SUCCESS_CODE);
		responseDto.setStatusMessage(ApplicationConstants.SUCCESS);
		return responseDto;

	}

}
