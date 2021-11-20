/**
 * 
 */
package com.cag.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cag.model.entity.DeathCertf;
import com.cag.model.entity.DocumentsColl;
import com.cag.model.entity.HabitsNMedHist;
import com.cag.model.entity.LastDocHosp;
import com.cag.model.entity.LeadDetails;
import com.cag.model.entity.LeadSheet;
import com.cag.model.entity.NeighbNEmpRef;
import com.cag.model.entity.NomineeFamDts;
import com.cag.model.entity.PolicyHolderPD;
import com.cag.model.requestdto.AssignLeadRequestDto;
import com.cag.model.requestdto.ListCasesRequestDto;
import com.cag.model.responsedto.ListCasesResponseDto;
import com.cag.service.DeathCertfService;
import com.cag.service.DocumentsCollService;
import com.cag.service.EreportingService;
import com.cag.service.FileManager;
import com.cag.service.HabitsNMedHistService;
import com.cag.service.LastDocHospService;
import com.cag.service.LeadDetailsService;
import com.cag.service.NeighbNEmpRefService;
import com.cag.service.NomineeFamDetService;
import com.cag.service.PolicyHolderPDService;
import com.cag.utility.ApplicationConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * @author aspak.avesh.sharif
 *
 */
@RestController
@RequestMapping(value = "/ereporting/")
@Slf4j
public class EreportingController {

	
	@Autowired
	ModelMapper modelmapper;

	@Autowired
	EreportingService ereportingService;

	@Autowired
	LeadDetailsService leadDetailsService;

	@Autowired
	PolicyHolderPDService policyHolderPDService;

	@Autowired
	NomineeFamDetService nomineeFamDetService;

	@Autowired
	ListCasesResponseDto listCasesResponseDto;

	@Autowired
	HabitsNMedHistService habitsNMedHistService;

	@Autowired
	NeighbNEmpRefService neighbNEmpRefService;

	@Autowired
	LastDocHospService lastDocHospService;

	@Autowired
	DeathCertfService deathCertfService;

	@Autowired
	DocumentsCollService documentsCollService;

	@Value("${file.upload-dir}")
	private String rootDir;
	@Autowired
	FileManager fileManager;

	@RequestMapping(method = RequestMethod.POST, value = "/uploadCases")
	public void uploadCases(@RequestParam("file") MultipartFile files) {
		
		System.out.println("file upload started - 1");

		XSSFWorkbook workbook;
		try {
			workbook = new XSSFWorkbook(files.getInputStream());

			List<LeadSheet> casesList = new ArrayList<LeadSheet>();
			List<LeadDetails> leadDetailsList = new ArrayList<LeadDetails>();
			List<PolicyHolderPD> policyHolderPDList = new ArrayList<PolicyHolderPD>();
			List<NomineeFamDts> nomineeFamDtsList = new ArrayList<NomineeFamDts>();
			List<HabitsNMedHist> habitsNMedHistList = new ArrayList<HabitsNMedHist>();
			List<NeighbNEmpRef> neighbNEmpRefList = new ArrayList<NeighbNEmpRef>();
			List<LastDocHosp> lastDocHospList = new ArrayList<LastDocHosp>();
			List<DeathCertf> deathCertfList = new ArrayList<DeathCertf>();
			List<DocumentsColl> documentsCollList = new ArrayList<DocumentsColl>();

			System.out.println("file upload started - 2");
			// XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
			// XSSFWorkbook workbook = new XSSFWorkbook(rootDir+"/OpenCases.xlsx");
			// XSSFWorkbook workbook = new XSSFWorkbook("/tmp/reports/OpenCases.xlsx");

			XSSFSheet worksheet = workbook.getSheetAt(0);

			System.out.println("file upload started - 3");
			for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
				System.out.println("file upload started - 4");
				LeadSheet openCases = new LeadSheet();
				LeadDetails leadDetails = new LeadDetails();
				PolicyHolderPD policyHolderPD = new PolicyHolderPD();
				NomineeFamDts nomineeFamDts = new NomineeFamDts();
				HabitsNMedHist habitsNMedHist = new HabitsNMedHist();
				NeighbNEmpRef neighbNEmpRef = new NeighbNEmpRef();
				LastDocHosp lastDocHosp = new LastDocHosp();
				DeathCertf deathCertf = new DeathCertf();
				DocumentsColl documentsColl = new DocumentsColl();
				XSSFRow row = worksheet.getRow(i);

				// tempStudent.setCaseId((int) row.getCell(0).getNumericCellValue());
				openCases.setPartnerName(row.getCell(0).getStringCellValue());
				openCases.setCaseId(row.getCell(1).getStringCellValue());
				openCases.setPolicyNumber(row.getCell(2).getStringCellValue());
				openCases.setPolicyHolderName(row.getCell(3).getStringCellValue());
				openCases.setProposalAddress(row.getCell(4).getStringCellValue());
				openCases.setLeadOwner(row.getCell(5).getStringCellValue());
				openCases.setLeadRecievedDate(row.getCell(6).getLocalDateTimeCellValue().toLocalDate().minusDays(1));
				openCases.setLeadStatus(row.getCell(7).getStringCellValue());
				openCases.setFieldAgentName(row.getCell(8).getStringCellValue());
				openCases.setBackendAgentName(row.getCell(9).getStringCellValue());
				System.out.println("file upload started - 5");
				openCases.setFolderId(fileManager.getFolderId(row.getCell(1).getStringCellValue()));
				System.out.println("file upload started - 6");
				casesList.add(openCases);
				System.out.println(openCases.toString());

				// lead details

				leadDetails.setCaseId(row.getCell(1).getStringCellValue());
				leadDetails.setCaseOwner(row.getCell(5).getStringCellValue());
				leadDetails.setUpdatedBy("Admin");
				leadDetails.setUpdatedDate(new Date());
				leadDetails.setPolicyNumber(row.getCell(2).getStringCellValue());
				leadDetailsList.add(leadDetails);

				// Policy holder pd

				policyHolderPD.setCaseId(row.getCell(1).getStringCellValue());
				policyHolderPD.setCaseOwner(row.getCell(5).getStringCellValue());
				policyHolderPD.setUpdatedBy("Admin");
				policyHolderPD.setUpdatedDate(new Date());
				policyHolderPD.setPolicyNumber(row.getCell(2).getStringCellValue());
				policyHolderPDList.add(policyHolderPD);

				System.out.println(openCases.toString());

				// Nominee family details

				nomineeFamDts.setCaseId(row.getCell(1).getStringCellValue());
				nomineeFamDts.setCaseOwner(row.getCell(5).getStringCellValue());
				nomineeFamDts.setUpdatedBy("Admin");
				nomineeFamDtsList.add(nomineeFamDts);

				// Habits and Medical history

				habitsNMedHist.setCaseId(row.getCell(1).getStringCellValue());
				habitsNMedHist.setCaseOwner(row.getCell(5).getStringCellValue());
				habitsNMedHist.setUpdatedBy("Admin");
				habitsNMedHistList.add(habitsNMedHist);

				// Neighbour and Emp ref details

				neighbNEmpRef.setCaseId(row.getCell(1).getStringCellValue());
				neighbNEmpRef.setCaseOwner(row.getCell(5).getStringCellValue());
				neighbNEmpRef.setUpdatedBy("Admin");
				neighbNEmpRefList.add(neighbNEmpRef);

				// Neighbour and Emp ref details

				lastDocHosp.setCaseId(row.getCell(1).getStringCellValue());
				lastDocHosp.setCaseOwner(row.getCell(5).getStringCellValue());
				lastDocHosp.setUpdatedBy("Admin");
				lastDocHospList.add(lastDocHosp);

				// DeathCertf Details

				deathCertf.setCaseId(row.getCell(1).getStringCellValue());
				deathCertf.setCaseOwner(row.getCell(5).getStringCellValue());
				deathCertf.setUpdatedBy("Admin");
				deathCertfList.add(deathCertf);

				// Documents collected

				documentsColl.setCaseId(row.getCell(1).getStringCellValue());
				documentsColl.setCaseOwner(row.getCell(5).getStringCellValue());
				documentsColl.setUpdatedBy("Admin");
				documentsCollList.add(documentsColl);

			}
			ereportingService.uploadCases(casesList);
			leadDetailsService.updateLeadDetails(leadDetailsList);
			policyHolderPDService.updatePolicyHolderPD(policyHolderPDList);
			nomineeFamDetService.updateNomineeFamDts(nomineeFamDtsList);
			habitsNMedHistService.updateHabitsNMedHist(habitsNMedHistList);
			lastDocHospService.updateLastDocHosp(lastDocHospList);
			deathCertfService.updateDeathCertf(deathCertfList);
			documentsCollService.updateDocumentsColl(documentsCollList);
			neighbNEmpRefService.updateNeighbNEmpRef(neighbNEmpRefList);

		} catch (Exception e) {
			
			System.out.println(e.getLocalizedMessage());

			//e.printStackTrace();
		}

	}

	@PostMapping(value = "/listCases")
	public ListCasesResponseDto listCases(@RequestBody ListCasesRequestDto listCasesRequestDto) {
		log.info(listCasesRequestDto.toString());
		List<LeadSheet> caseList = ereportingService.listCases(listCasesRequestDto);
		listCasesResponseDto.setCaseList(caseList);
		listCasesResponseDto.setStatuscode(ApplicationConstants.SUCCESS_CODE);
		listCasesResponseDto.setStatusMessage(ApplicationConstants.SUCCESS);
		return listCasesResponseDto;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/fetchLead/{id}")
	public ListCasesResponseDto getReport(@PathVariable String id) {
		System.out.println("Recieved id is " + id);
		List<LeadSheet> caseList = ereportingService.fetchLead(id);
		listCasesResponseDto.setCaseList(caseList);
		listCasesResponseDto.setStatuscode(ApplicationConstants.SUCCESS_CODE);
		listCasesResponseDto.setStatusMessage(ApplicationConstants.SUCCESS);

		return listCasesResponseDto;

	}

	@PostMapping(value = "/updateLead", consumes = "application/json")
	public ListCasesResponseDto saveReport(@RequestBody LeadSheet leadupateobj) {

		System.out.println("Recieved id is " + leadupateobj.getCaseId());
		List<LeadSheet> caseList = ereportingService.updateLead(leadupateobj);
		listCasesResponseDto.setCaseList(caseList);
		listCasesResponseDto.setStatuscode(ApplicationConstants.SUCCESS_CODE);
		listCasesResponseDto.setStatusMessage(ApplicationConstants.SUCCESS);
		return listCasesResponseDto;

	}

	@PostMapping(value = "/assignLead", consumes = "application/json")
	public ListCasesResponseDto assignLead(@RequestBody AssignLeadRequestDto assignLeadRequestDto) {

		System.out.println("Recieved id is " + assignLeadRequestDto.getCaseId());
		List<LeadSheet> caseList = ereportingService.fetchLead(assignLeadRequestDto.getCaseId());
		caseList.get(0).setLeadStatus(ApplicationConstants.OWNER_STATUS);
		caseList.get(0).setFieldAgentstatus(assignLeadRequestDto.getReason());
		caseList.get(0).setUpdatedBy(assignLeadRequestDto.getAgentName());

		caseList.get(0).setUpdatedDate(new Date());
		caseList = ereportingService.updateLead(caseList.get(0));
		listCasesResponseDto.setCaseList(caseList);
		listCasesResponseDto.setStatuscode(ApplicationConstants.SUCCESS_CODE);
		listCasesResponseDto.setStatusMessage(ApplicationConstants.SUCCESS);
		return listCasesResponseDto;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/generateReport/{caseId}")
	public ResponseEntity<String> generateReport(@PathVariable String caseId) throws MessagingException, IOException {
		System.out.println("Recieved id for generating report is  " + caseId);
		try {
			String generationsStatus = ereportingService.generateReport(caseId);

			if (generationsStatus.isEmpty()) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
			return ResponseEntity.ok("Success, generationsStatus: " + generationsStatus);
		} catch (Exception e) {
			log.error("Error while generateReport " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
}
