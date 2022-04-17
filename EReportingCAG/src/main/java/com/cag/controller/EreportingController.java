/**
 * 
 */
package com.cag.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
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
	DataFormatter df;

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
	public String uploadCases(@RequestParam("file") MultipartFile files) {

		System.out.println("file upload started - 1");
		List<String> duplicateUploads = new ArrayList<String>();
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
				// df.formatCellValue(row.getCell(3).setCellType(CellType.STRING);

				// String asItLooksInExcel =
				// df.formatCellValue(df.formatCellValue(row.getCell(3));

				if (null == ereportingService.fetchLead(df.formatCellValue(row.getCell(3)))) {
					// New as per comments start
					openCases.setPartnerName(df.formatCellValue(row.getCell(0)));

					// openCases.setCaseId(df.formatCellValue(row.getCell(3)));
					openCases.setCaseId(df.formatCellValue(row.getCell(3)));
					openCases.setPolicyHolderName(df.formatCellValue(row.getCell(2)));
					openCases.setPolicyNumber(df.formatCellValue(row.getCell(3)));
					openCases.setClaimNo(df.formatCellValue(row.getCell(4)));
					openCases.setInvestigationType(df.formatCellValue(row.getCell(5)));
					openCases.setState(df.formatCellValue(row.getCell(6)));
					openCases.setCity(df.formatCellValue(row.getCell(7)));

					openCases.setLeadRecievedDate(
							DateUtil.getLocalDateTime(Double.valueOf(row.getCell(8).getRawValue())).toLocalDate().minusDays(1));

					// System.out.println(df.formatCellValue(row.getCell(8).getDateCellValue());
					openCases.setFieldAgentName(df.formatCellValue(row.getCell(9)));
					openCases.setBackendAgentName(df.formatCellValue(row.getCell(10)));
					openCases.setLeadOwner(df.formatCellValue(row.getCell(11)));
					openCases.setClaimentName(df.formatCellValue(row.getCell(12)));
					openCases.setProposalContact(df.formatCellValue(row.getCell(13)));
					openCases.setProposalAddress(df.formatCellValue(row.getCell(14)));
					openCases.setClaimFormContact(df.formatCellValue(row.getCell(15)));
					openCases.setClaimFormAdd(df.formatCellValue(row.getCell(16)));
					openCases.setPolicyIssuanceDate(
							DateUtil.getLocalDateTime(Double.valueOf(row.getCell(17).getRawValue())).toLocalDate());
					openCases.setLeadCauseOfDeath(df.formatCellValue(row.getCell(18)));
					openCases.setLeadSumAssured(df.formatCellValue(row.getCell(19)));
					openCases.setClaimFormEmail(df.formatCellValue(row.getCell(20)));
					openCases.setProposalEmail(df.formatCellValue(row.getCell(21)));
					openCases.setLeadStatus("field");
					System.out.println("file upload started - 5");
					openCases.setFolderId(fileManager.getFolderId(df.formatCellValue(row.getCell(3))));
					System.out.println("file upload started - 6");

					casesList.add(openCases);
					System.out.println(openCases.toString());

					// lead details
					leadDetails.setInsuranceCompany(df.formatCellValue(row.getCell(0)));
					leadDetails.setCaseId(df.formatCellValue(row.getCell(3)));
					leadDetails.setLaName(df.formatCellValue(row.getCell(2)));
					leadDetails.setPolicyNumber(df.formatCellValue(row.getCell(3)));
					leadDetails.setInvestigationType(df.formatCellValue(row.getCell(5)));
					leadDetails.setState(df.formatCellValue(row.getCell(6)));
					leadDetails.setCity(df.formatCellValue(row.getCell(7)));
					leadDetails.setCaseOwner(df.formatCellValue(row.getCell(11)));
					leadDetails.setNomineeContact(df.formatCellValue(row.getCell(15)));
					leadDetails.setClaimFormContact(df.formatCellValue(row.getCell(15)));
					leadDetails.setProposalFormContact(df.formatCellValue(row.getCell(13)));
					leadDetails.setNomineeAddress(df.formatCellValue(row.getCell(16)));
					leadDetails.setProposalFormAddress(df.formatCellValue(row.getCell(14)));
					leadDetails.setClaimFormAddress(df.formatCellValue(row.getCell(16)));
					leadDetails.setUpdatedBy("Admin");
					leadDetails.setUpdatedDate(new Date());

					leadDetailsList.add(leadDetails);

					// Policy holder pd

					policyHolderPD.setCaseId(df.formatCellValue(row.getCell(3)));
					policyHolderPD.setPolicyHolderName(df.formatCellValue(row.getCell(2)));
					policyHolderPD
							.setPolicyIssuanceDate(DateUtil.getJavaDate(Double.valueOf(row.getCell(17).getRawValue())));
					policyHolderPD.setCaseOwner(df.formatCellValue(row.getCell(11)));
					policyHolderPD.setProposalFormAddress(df.formatCellValue(row.getCell(14)));
					policyHolderPD.setClaimFormAddress(df.formatCellValue(row.getCell(16)));
					policyHolderPD.setPolicyHolderSumAssured(df.formatCellValue(row.getCell(19)));
					policyHolderPD.setUpdatedBy("Admin");
					policyHolderPD.setUpdatedDate(new Date());
					policyHolderPD.setPolicyNumber(df.formatCellValue(row.getCell(3)));
					policyHolderPDList.add(policyHolderPD);

					System.out.println(openCases.toString());

					// Nominee family details

					nomineeFamDts.setCaseId(df.formatCellValue(row.getCell(3)));
					nomineeFamDts.setCaseOwner(df.formatCellValue(row.getCell(11)));
					nomineeFamDts.setUpdatedBy("Admin");
					nomineeFamDtsList.add(nomineeFamDts);

					// Habits and Medical history

					habitsNMedHist.setCaseId(df.formatCellValue(row.getCell(3)));
					habitsNMedHist.setCaseOwner(df.formatCellValue(row.getCell(11)));
					habitsNMedHist.setUpdatedBy("Admin");
					habitsNMedHistList.add(habitsNMedHist);

					// Neighbour and Emp ref details

					neighbNEmpRef.setCaseId(df.formatCellValue(row.getCell(3)));
					neighbNEmpRef.setCaseOwner(df.formatCellValue(row.getCell(11)));
					neighbNEmpRef.setUpdatedBy("Admin");
					neighbNEmpRefList.add(neighbNEmpRef);

					// Neighbour and Emp ref details

					lastDocHosp.setCaseId(df.formatCellValue(row.getCell(3)));
					lastDocHosp.setCaseOwner(df.formatCellValue(row.getCell(11)));
					lastDocHosp.setUpdatedBy("Admin");
					lastDocHospList.add(lastDocHosp);

					// DeathCertf Details

					deathCertf.setCaseId(df.formatCellValue(row.getCell(3)));
					deathCertf.setCaseOwner(df.formatCellValue(row.getCell(11)));
					deathCertf.setUpdatedBy("Admin");
					deathCertfList.add(deathCertf);

					// Documents collected

					documentsColl.setCaseId(df.formatCellValue(row.getCell(3)));
					documentsColl.setCaseOwner(df.formatCellValue(row.getCell(11)));
					documentsColl.setUpdatedBy("Admin");
					documentsCollList.add(documentsColl);
				} else {
					duplicateUploads.add(df.formatCellValue(row.getCell(3)));
				}
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

			// e.printStackTrace();
		}
		return "duplicates : " + duplicateUploads.toString();

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
