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

import com.cag.model.entity.DocumentsColl;
import com.cag.model.requestdto.DocumentsCollReqDto;
import com.cag.model.responsedto.DocumentsCollResDto;
import com.cag.service.DocumentsCollService;
import com.cag.utility.ApplicationConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * @author aspak.avesh.sharif
 *
 */
@RestController
@RequestMapping(value = "/documentsCollected/")
@Slf4j
public class DocumentsCollController {

	@Autowired
	ModelMapper modelmapper;
	@Autowired
	DocumentsCollService documentsCollService;

	@GetMapping(value = "/fetchDocumentsColl/{caseId}")
	public DocumentsCollResDto fetchDocumentsColl(@PathVariable String caseId) {
		DocumentsCollResDto responseDto = new DocumentsCollResDto();
		try {

			responseDto.setDocumentsColl(documentsCollService.fetchDocumentsColl(caseId).get());
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

	@PostMapping(value = "/saveDocumentsColl", consumes = "application/json")
	public DocumentsCollResDto saveDocumentsColl(@RequestBody DocumentsCollReqDto requestDto) {
		DocumentsCollResDto responseDto = new DocumentsCollResDto();
		DocumentsColl documentsColl = new DocumentsColl();
		documentsColl = modelmapper.map(requestDto, DocumentsColl.class);
		// policyHolderPD.setUpdatedBy(policyHolderPDRequestDto.getAgentName());
		documentsColl.setUpdatedDate(new Date());
		responseDto.setDocumentsColl(documentsCollService.updateDocumentsColl(documentsColl));
		responseDto.setStatuscode(ApplicationConstants.SUCCESS_CODE);
		responseDto.setStatusMessage(ApplicationConstants.SUCCESS);
		return responseDto;

	}



}
