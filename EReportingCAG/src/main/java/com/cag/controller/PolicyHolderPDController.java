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

import com.cag.model.entity.PolicyHolderPD;
import com.cag.model.requestdto.PolicyHolderPDRequestDto;
import com.cag.model.responsedto.PolicyHolderPDResponseDto;
import com.cag.service.PolicyHolderPDService;
import com.cag.utility.ApplicationConstants;

/**
 * @author aspak.avesh.sharif
 *
 */
@RestController
@RequestMapping(value = "/policyholderPD/")
public class PolicyHolderPDController {

	public static final Logger LOG = LoggerFactory.getLogger(PolicyHolderPDController.class);

	@Autowired
	PolicyHolderPDService policyHolderPDService;

	@Autowired
	ModelMapper modelmapper;

	@GetMapping(value = "/fetchPolicyHolderPD/{caseId}")
	public PolicyHolderPDResponseDto fetchLeadDetails(@PathVariable String caseId) {
		PolicyHolderPDResponseDto policyHolderPDResponseDto = new PolicyHolderPDResponseDto();
		try {

			policyHolderPDResponseDto.setPolicyHolderPD(policyHolderPDService.fetchPolicyHolderPD(caseId).get());
			policyHolderPDResponseDto.setStatuscode(ApplicationConstants.SUCCESS_CODE);
			policyHolderPDResponseDto.setStatusMessage(ApplicationConstants.SUCCESS);
			return policyHolderPDResponseDto;
		} catch (Exception e) {
			LOG.info("Invalid case id {} . Please retry with valid case Id", caseId);
			policyHolderPDResponseDto.setStatuscode(ApplicationConstants.INVALID_CASE_ID_CODE);
			policyHolderPDResponseDto.setStatusMessage(ApplicationConstants.INVALID_CASE_ID_MESSAGE);
			policyHolderPDResponseDto.setErrorMessage(e.getMessage());
			return policyHolderPDResponseDto;
		}
	}

	@PostMapping(value = "/savePolicyHolderPD", consumes = "application/json")
	public PolicyHolderPDResponseDto saveLeadDetails(@RequestBody PolicyHolderPDRequestDto policyHolderPDRequestDto) {
		PolicyHolderPDResponseDto policyHolderPDResponseDto = new PolicyHolderPDResponseDto();
		PolicyHolderPD policyHolderPD = new PolicyHolderPD();
		policyHolderPD = modelmapper.map(policyHolderPDRequestDto, PolicyHolderPD.class);
		// policyHolderPD.setUpdatedBy(policyHolderPDRequestDto.getAgentName());
		policyHolderPD.setUpdatedDate(new Date());
		policyHolderPDResponseDto.setPolicyHolderPD(policyHolderPDService.updatePolicyHolderPD(policyHolderPD));
		policyHolderPDResponseDto.setStatuscode(ApplicationConstants.SUCCESS_CODE);
		policyHolderPDResponseDto.setStatusMessage(ApplicationConstants.SUCCESS);
		return policyHolderPDResponseDto;

	}

}
