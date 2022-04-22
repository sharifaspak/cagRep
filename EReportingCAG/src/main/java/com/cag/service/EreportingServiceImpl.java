/**
 * 
 */
package com.cag.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cag.model.entity.DeathCertf;
import com.cag.model.entity.DocumentsColl;
import com.cag.model.entity.HabitsNMedHist;
import com.cag.model.entity.LastDocHosp;
import com.cag.model.entity.LeadDetails;
import com.cag.model.entity.LeadSheet;
import com.cag.model.entity.NeighbNEmpRef;
import com.cag.model.entity.NomineeFamDts;
import com.cag.model.entity.PolicyHolderPD;
import com.cag.model.requestdto.ListCasesRequestDto;
import com.cag.repository.DeathCertfRep;
import com.cag.repository.DocumentsCollRep;
import com.cag.repository.HabitsNMedHistRep;
import com.cag.repository.LastDocHospRep;
import com.cag.repository.LeadDetailsRepository;
import com.cag.repository.LeadSheetRepository;
import com.cag.repository.NeighbNEmpRefRep;
import com.cag.repository.NomineeFamDetRep;
import com.cag.repository.PolicyHolderPDRepository;
import com.cag.utility.ApplicationConstants;
import com.cag.utility.CagUtils;
import com.deepoove.poi.XWPFTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * @author aspak.avesh.sharif
 *
 */
@Service
@Slf4j
public class EreportingServiceImpl implements EreportingService {

	private static final Logger LOG = LoggerFactory.getLogger(EreportingServiceImpl.class);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private FileManager fileManager;

	@Value("${file.upload-dir}")
	private String rootDir;

	@Autowired
	LeadSheetRepository caseRepository;

	@Autowired
	DeathCertfRep deathCertfRep;

	@Autowired
	HabitsNMedHistRep habitsNMedHistRep;

	@Autowired
	LastDocHospRep lastDocHospRep;

	@Autowired
	LeadDetailsRepository leadDetailsRepository;

	@Autowired
	NeighbNEmpRefRep neighbNEmpRefRep;

	@Autowired
	NomineeFamDetRep nomineeFamDetRep;

	@Autowired
	PolicyHolderPDRepository policyHolderPDRepository;

	@Autowired
	DocumentsCollRep documentsCollRepository;

	@Override
	public List<LeadSheet> listCases(ListCasesRequestDto listCasesRequestDto) {
		
		Date newToDate = listCasesRequestDto.getTo();
		Calendar c = Calendar.getInstance(); 
		c.setTime(newToDate); 
		c.add(Calendar.DATE, 1);
		newToDate = c.getTime();

		Pageable paging = PageRequest.of(listCasesRequestDto.getPageNumber(), listCasesRequestDto.getNumberOfrecords(),
				Sort.by("leadRecievedDate").descending());
		LOG.info("user role in request is {}", listCasesRequestDto.getRole());
		if (!(null == listCasesRequestDto.getPolicyHolderName()
				|| listCasesRequestDto.getPolicyHolderName().isEmpty())) {
			return caseRepository.findByPolicyHolderNameIgnoreCase(listCasesRequestDto.getPolicyHolderName(), paging);
		} else {
			switch (listCasesRequestDto.getRole()) {
			case ApplicationConstants.FIELD_AGENT:
				return caseRepository.findByFieldAgentNameAndLeadStatusAndLeadRecievedDateBetween(
						listCasesRequestDto.getAgentName(), ApplicationConstants.FIELD_STATUS,
						listCasesRequestDto.getFrom(), newToDate, paging);
			case ApplicationConstants.BACKEND_AGENT:
				return caseRepository.findByBackendAgentNameAndLeadStatusAndLeadRecievedDateBetween(
						listCasesRequestDto.getAgentName(), ApplicationConstants.BACKEND_STATUS,
						listCasesRequestDto.getFrom(), newToDate, paging);
			case ApplicationConstants.LEAD_OWNER:
				return caseRepository.findByLeadOwnerAndLeadStatusAndLeadRecievedDateBetween(
						listCasesRequestDto.getAgentName(), ApplicationConstants.OWNER_STATUS,
						listCasesRequestDto.getFrom(), newToDate, paging);
			default:
				return caseRepository.findByLeadStatusAndLeadRecievedDateBetween(listCasesRequestDto.getStatus(),
						listCasesRequestDto.getFrom(),newToDate, paging);
			}
		}
	}

	@Override
	public List<LeadSheet> uploadCases(List<LeadSheet> casesList) {

		return (List<LeadSheet>) caseRepository.saveAll(casesList);

	}

	@Override
	public List<LeadSheet> fetchLead(String id) {

		List<LeadSheet> leadDetails = new ArrayList<>();
		Optional<LeadSheet> lead = caseRepository.findById(id);
		try {
			leadDetails.add(lead.get());
		} catch (Exception e) {
			return null;
		}
		return leadDetails;
	}

	@Override
	public List<LeadSheet> updateLead(LeadSheet leadtoUpdate) {
		List<LeadSheet> leadDetails = new ArrayList<>();
		LeadSheet lead = caseRepository.save(leadtoUpdate);
		leadDetails.add(lead);
		return leadDetails;

	}

	@Override
	public String generateReport(String id) {
		// TODO Auto-generated method stub

		Optional<LeadSheet> leadSheet = caseRepository.findById(id);

		Optional<DeathCertf> deathCertf = deathCertfRep.findById(id);

		Optional<HabitsNMedHist> habitsNMedHist = habitsNMedHistRep.findById(id);

		Optional<LastDocHosp> lastDocHosp = lastDocHospRep.findById(id);

		Optional<LeadDetails> leadDetails = leadDetailsRepository.findById(id);

		Optional<NeighbNEmpRef> neighbNEmpRef = neighbNEmpRefRep.findById(id);

		Optional<NomineeFamDts> nomineeFamDts = nomineeFamDetRep.findById(id);

		Optional<PolicyHolderPD> policyHolderPD = policyHolderPDRepository.findById(id);

		Optional<DocumentsColl> documentsColl = documentsCollRepository.findById(id);

		String repName = null;

		String outFileName = rootDir + "/" + leadSheet.get().getCaseId() + ".docx";

		try {

			repName = prepareReport(leadSheet, deathCertf, habitsNMedHist, lastDocHosp, leadDetails, neighbNEmpRef,
					nomineeFamDts, policyHolderPD, documentsColl, outFileName);
			FileSystemResource file = new FileSystemResource(new File(repName));
			File file1 = new File(repName);

			FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(file1.toPath()), false,
					file1.getName(), (int) file1.length(), file1.getParentFile());

			try {
				InputStream input = new FileInputStream(file1);
				OutputStream os = fileItem.getOutputStream();
				IOUtils.copy(input, os);
				// Or faster..
				// IOUtils.copy(new FileInputStream(file), fileItem.getOutputStream());
			} catch (IOException ex) {
				// do something.
			}

			MultipartFile multipartFile = new CommonsMultipartFile(fileItem);

			String fileId = fileManager.uploadFile(multipartFile, id);

			System.out.println("file uploaded successfully" + fileId);

		} catch (IOException e) { // TODO Auto-generated catch block
			System.out.println("Failed while preparing report step 0");
			e.printStackTrace();
			System.out.println("Failed while preparing report step 1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Failed while preparing report step 2");
			e.printStackTrace();
			System.out.println("Failed while preparing report step 3");
		}
		try {
			if (repName != null)
				sendEmail(repName, id);
			System.out.println("Mail commented");
		} catch (MessagingException e) { // TODO Auto-generated catch block
			System.out.println("Failed while preparing report step 4");
			e.printStackTrace();
			System.out.println("Failed while preparing report step 5");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Failed while preparing report step 6");
			e.printStackTrace();
			System.out.println("Failed while preparing report step 7");
		}
		return repName;
	}

	private String prepareReport(Optional<LeadSheet> leadSheet, Optional<DeathCertf> deathCertf,
			Optional<HabitsNMedHist> habitsNMedHist, Optional<LastDocHosp> lastDocHosp,
			Optional<LeadDetails> leadDetails, Optional<NeighbNEmpRef> neighbNEmpRef,
			Optional<NomineeFamDts> nomineeFamDts, Optional<PolicyHolderPD> policyHolderPD,
			Optional<DocumentsColl> documentsColl, String outFileName) throws IOException {

		XWPFTemplate.compile(rootDir + "/template_final_v2.docx").render(new HashMap<String, Object>() {
			{
				// XWPFTemplate.compile("/tmp/reports/templateV2.docx").render(new
				// HashMap<String, Object>(){{

				put("investigationType", leadDetails.get().getInvestigationType());
				put("policyHoldeAge", CagUtils.calculateAge(policyHolderPD.get().getPolicyHolderDob(),
						policyHolderPD.get().getPolicyHolderDod()));
				put("policyHolderName", policyHolderPD.get().getPolicyHolderName());
				put("policyHolderOccupationName", policyHolderPD.get().getPolicyHolderOccupationName());
				if (!(null == nomineeFamDts.get().getNomineeDetailsList()
						|| nomineeFamDts.get().getNomineeDetailsList().isEmpty())) {
					put("nomineeName", nomineeFamDts.get().getNomineeDetailsList().get(0).getNomineeName());
					put("nomineeDob", CagUtils.convertToLocalDateViaInstant(
							nomineeFamDts.get().getNomineeDetailsList().get(0).getNomineeDob()));
					put("nomineeOccupation", nomineeFamDts.get().getNomineeDetailsList().get(0).getNomineeOccupation());
					put("nomineeRelation", nomineeFamDts.get().getNomineeDetailsList().get(0).getNomineeRelation());
					put("nomineeAddress", nomineeFamDts.get().getNomineeDetailsList().get(0).getNomineeAddress());
					put("nomineeContact", nomineeFamDts.get().getNomineeDetailsList().get(0).getNomineeContact());
				}
				put("policyHolderDob",
						CagUtils.convertToLocalDateViaInstant(policyHolderPD.get().getPolicyHolderDob()));

				put("policyHolderDod",
						CagUtils.convertToLocalDateViaInstant(policyHolderPD.get().getPolicyHolderDod()));
				put("policyHolderPremiumAmt", policyHolderPD.get().getPolicyHolderPremiumAmt());
				put("policyHolderCauseOfDeath", policyHolderPD.get().getPolicyHolderCauseOfDeath());
				put("policyHolderSumAssured", policyHolderPD.get().getPolicyHolderSumAssured());
				put("policyIssuanceDate",
						CagUtils.convertToLocalDateViaInstant(policyHolderPD.get().getPolicyIssuanceDate()));
				put("state", leadDetails.get().getState());
				put("city", leadDetails.get().getCity());
				put("reportGenDt", CagUtils.convertToLocalDateViaInstant(new Date()));
				put("claimFormAddress", leadDetails.get().getClaimFormAddress());
				put("proposalFormAddress", leadDetails.get().getProposalFormAddress());
				put("proposalFormContact", leadDetails.get().getProposalFormContact());
				put("policyHolderMaritalStatus", policyHolderPD.get().getPolicyHolderMaritalStatus());
				put("policyHolderAnnualIncome", policyHolderPD.get().getPolicyHolderAnnualIncome());
				put("policyHoldereducationQualification", policyHolderPD.get().getPolicyHoldereducationQualification());
				if (!(null == habitsNMedHist.get().getOtherInsuranceList()
						|| habitsNMedHist.get().getOtherInsuranceList().isEmpty())) {
					put("insuranceType", habitsNMedHist.get().getOtherInsuranceList().get(0).getInsuranceType());
				}
				put("policyHolderAddress", policyHolderPD.get().getPolicyHolderAddress());
				if (!(null == nomineeFamDts.get().getFamilyDetailsList()
						|| nomineeFamDts.get().getFamilyDetailsList().isEmpty())) {

					for (int i = 0; i < nomineeFamDts.get().getFamilyDetailsList().size(); i++) {
						put("familyDetailsName" + i, nomineeFamDts.get().getFamilyDetailsList().get(i).getName());
						put("familyDetailsAge" + i, nomineeFamDts.get().getFamilyDetailsList().get(i).getAge());
						put("familyDetailsRelationShip" + i,
								nomineeFamDts.get().getFamilyDetailsList().get(i).getRelationShip());
						put("familyDetailsOccupation" + i,
								nomineeFamDts.get().getFamilyDetailsList().get(i).getOccupation());
						put("familyDetailscontactNumber" + i,
								nomineeFamDts.get().getFamilyDetailsList().get(i).getContactNumber());
						put("familyDetailsaddress" + i, nomineeFamDts.get().getFamilyDetailsList().get(i).getAddress());
					}
				}

				put("rationParivarCard", documentsColl.get().getRationParivarCard());
				put("voterIdCollected", documentsColl.get().getVoterIdCollected());
				put("panCollected", documentsColl.get().getPanCollected());
				put("aadharCollected", documentsColl.get().getAadharCollected());
				put("nomineeRelationProofCollected", documentsColl.get().getNomineeRelationProofCollected());
				put("affidavitCollected", documentsColl.get().getAffidavitCollected());

				put("metSalutation", nomineeFamDts.get().getMetSalutation());
				put("metPersonName", nomineeFamDts.get().getMetPersonName());
				put("metPersonRelationship", nomineeFamDts.get().getMetPersonRelationship());
				put("metPersonCauseOfDeath", nomineeFamDts.get().getMetPersonCauseOfDeath());
				put("metPersonObservation", nomineeFamDts.get().getMetPersonCauseOfDeath());

				put("policyHolderPlaceOfDeath", policyHolderPD.get().getPolicyHolderPlaceOfDeath());

				if (!(null == lastDocHosp.get().getLastDocList() || lastDocHosp.get().getLastDocList().isEmpty())) {
					put("lastDocObservation", lastDocHosp.get().getLastDocList().get(0).getObservation());
				}

				put("pastMedicalHistoryCheck", habitsNMedHist.get().getPastMedicalHistoryCheck());
				if (!(null == habitsNMedHist.get().getPastMedHistList()
						|| habitsNMedHist.get().getPastMedHistList().isEmpty())) {
					for (int i = 0; i < habitsNMedHist.get().getPastMedHistList().size(); i++) {
						put("diseaseName" + i, habitsNMedHist.get().getPastMedHistList().get(i).getDiseasaeName());
						put("diseaseDuration" + i,
								habitsNMedHist.get().getPastMedHistList().get(i).getDuration_obj().getYears()
										+ " years and "
										+ habitsNMedHist.get().getPastMedHistList().get(i).getDuration_obj().getMonths()
										+ " months");
						put("treatmentLocation" + i,
								habitsNMedHist.get().getPastMedHistList().get(i).getTreatmentDocName() + ","
										+ habitsNMedHist.get().getPastMedHistList().get(i).getTreatmentPlaceName() + ","
										+ habitsNMedHist.get().getPastMedHistList().get(i).getTreatmentLocation());
					}
				}
				put("pastHabitHistoryCheck", habitsNMedHist.get().getPastHabitHistoryCheck());
				if (!(null == habitsNMedHist.get().getHabitList() || habitsNMedHist.get().getHabitList().isEmpty())) {
					for (int i = 0; i < habitsNMedHist.get().getHabitList().size(); i++) {
						put("habitName" + i, habitsNMedHist.get().getHabitList().get(i).getHabitName());
						put("habitDuration" + i,
								habitsNMedHist.get().getHabitList().get(i).getHabitDuration_obj().getYears()
										+ " years and "
										+ habitsNMedHist.get().getHabitList().get(i).getHabitDuration_obj().getMonths()
										+ " months");
						put("habitFrequency" + i, habitsNMedHist.get().getHabitList().get(i).getHabitFrequency());

					}
				}
				put("policyHolderDesignation", policyHolderPD.get().getPolicyHolderDesignation());
				if (!(null == policyHolderPD.get().getPolicyHolderAddressTenure_obj())) {
				put("policyHolderOccupationTenure",
						policyHolderPD.get().getPolicyHolderOccupationTenure_obj().getYears() + " years and "
								+ policyHolderPD.get().getPolicyHolderOccupationTenure_obj().getMonths() + " months");
				}
				put("policyHolderEmpId", policyHolderPD.get().getPolicyHolderEmpId());
				put("policyHolderLwd",
						CagUtils.convertToLocalDateViaInstant(policyHolderPD.get().getPolicyHolderLwd()));
				put("policyHolderOccupationDocuments", policyHolderPD.get().getPolicyHolderOccupationDocuments());

				put("otherInsuranceCheck", habitsNMedHist.get().getOtherInsuranceCheck());

				if (!(null == habitsNMedHist.get().getOtherInsuranceList()
						|| habitsNMedHist.get().getOtherInsuranceList().isEmpty())) {

					for (int i = 0; i < habitsNMedHist.get().getOtherInsuranceList().size(); i++) {
						put("otherInsuranceType" + i,
								habitsNMedHist.get().getOtherInsuranceList().get(i).getInsuranceType());
						put("otherInsuranceCompany" + i,
								habitsNMedHist.get().getOtherInsuranceList().get(0).getInsurer());
						put("otherInsurancePolicyNum" + i,
								habitsNMedHist.get().getOtherInsuranceList().get(0).getPolicyNumber());
						put("otherInsuranceIssueDt" + i, CagUtils.convertToLocalDateViaInstant(
								habitsNMedHist.get().getOtherInsuranceList().get(0).getPolicyIssuanceDate()));
						put("otherInsuranceSumAssured" + i,
								habitsNMedHist.get().getOtherInsuranceList().get(0).getSumAssured());

					}

				}
				if (!(null == policyHolderPD.get().getPolicyHolderAddressTenure_obj())) {
					put("policyHolderAddressTenure",
							policyHolderPD.get().getPolicyHolderAddressTenure_obj().getYears() + " years and "
									+ policyHolderPD.get().getPolicyHolderAddressTenure_obj().getMonths() + " months");
				}
				put("policyHolderStandardOfLiving", policyHolderPD.get().getPolicyHolderStandardOfLiving());

				put("kycDocumentListLA", documentsColl.get().getKycDocumentListLA());

				put("pastNPresentMedRecords", documentsColl.get().getPastNPresentMedRecords());
				put("incomeProofDoc", documentsColl.get().getIncomeProofDoc());

				put("insurancePurpose", policyHolderPD.get().getInsurancePurpose());

				if (!(null == neighbNEmpRef.get().getNeighbourList()
						|| neighbNEmpRef.get().getNeighbourList().isEmpty())) {

					for (int i = 0; i < neighbNEmpRef.get().getNeighbourList().size(); i++) {
						put("neigshbourName" + i, neighbNEmpRef.get().getNeighbourList().get(i).getName());
						put("neighbourPlace" + i, neighbNEmpRef.get().getNeighbourList().get(i).getPlace());
						put("neighbourContact" + i, neighbNEmpRef.get().getNeighbourList().get(i).getContactNumber());
						put("neighbourLaExpired" + i, neighbNEmpRef.get().getNeighbourList().get(i).getLaExpired());
						put("neighbourReasonOfExpiry" + i,
								neighbNEmpRef.get().getNeighbourList().get(i).getReasonOfExpiry());
						put("neighbourPlaceOfExpiry" + i,
								neighbNEmpRef.get().getNeighbourList().get(i).getPlaceOfExpiry());
						put("neighbourDateOfExpiry" + i, CagUtils.convertToLocalDateViaInstant(
								neighbNEmpRef.get().getNeighbourList().get(i).getDateOfExpiry()));
					}

				}

				if (!(null == neighbNEmpRef.get().getColleagueList()
						|| neighbNEmpRef.get().getColleagueList().isEmpty())) {

					for (int i = 0; i < neighbNEmpRef.get().getColleagueList().size(); i++) {
						put("colleagueName" + i, neighbNEmpRef.get().getColleagueList().get(i).getName());
						put("colleagueContactNumber" + i,
								neighbNEmpRef.get().getColleagueList().get(i).getContactNumber());
						put("colleagueObservation" + i, neighbNEmpRef.get().getColleagueList().get(i).getObservation());
					}

				}

				if (!(null == lastDocHosp.get().getLastDocList() || lastDocHosp.get().getLastDocList().isEmpty())) {
					for (int i = 0; i < lastDocHosp.get().getLastDocList().size(); i++) {
						put("lastDocplaceName" + i, lastDocHosp.get().getLastDocList().get(i).getPlaceName());
						put("lastDocObservation" + i, lastDocHosp.get().getLastDocList().get(i).getObservation());
						put("lastDocaddress" + i, lastDocHosp.get().getLastDocList().get(i).getAddress());
						put("lastDocName" + i, lastDocHosp.get().getLastDocList().get(i).getName());
						put("lastDocContactNumber" + i, lastDocHosp.get().getLastDocList().get(i).getContactNumber());
						put("lastDocFromDate" + i, CagUtils
								.convertToLocalDateViaInstant(lastDocHosp.get().getLastDocList().get(i).getFromDate()));
						put("lastDocToDate" + i, CagUtils
								.convertToLocalDateViaInstant(lastDocHosp.get().getLastDocList().get(i).getToDate()));
						put("lastDocdocumentsCollected" + i,
								lastDocHosp.get().getLastDocList().get(i).getDocumentsCollected());
					}
				}

				put("policeStation", deathCertf.get().getPoliceStation());
				put("policeStationAddress", deathCertf.get().getPoliceStationAddress());
				put("policeStationcontactNumber", deathCertf.get().getPoliceStationcontactNumber());
				put("policeStationobservation", deathCertf.get().getPoliceStationobservation());

				put("colleagueHRSummary", neighbNEmpRef.get().getColleagueHRSummary());

				put("policyHolderDoc",
						CagUtils.convertToLocalDateViaInstant(policyHolderPD.get().getPolicyHolderDoc()));
				put("policyHolderPlaceOfCreamation", policyHolderPD.get().getPolicyHolderPlaceOfCreamation());

				put("issuingAuthorityName", deathCertf.get().getIssuingAuthorityName());
				put("personMet", deathCertf.get().getPersonMet());
				if ("Rural".equals(deathCertf.get().getCaseGeography())) {
					put("anganWadiName", deathCertf.get().getAnganWadiName());
					put("anaganWadiContact", deathCertf.get().getAnaganWadiContact());
					put("anmName", deathCertf.get().getAnmName());
					put("anmContact", deathCertf.get().getAnmContact());
					put("ashaName", deathCertf.get().getAshaName());
					put("ashaContact", deathCertf.get().getAshaContact());
					put("sarpanchName", deathCertf.get().getSarpanchName());
					put("sarpanchContact", deathCertf.get().getSarpanchContact());
					put("gramSachiveOrTalathiName", deathCertf.get().getGramSachiveOrTalathiName());
					put("gramSachiveOrTalathiContact", deathCertf.get().getGramSachiveOrTalathiContact());
				}
				put("newsPapCutCol", documentsColl.get().getNewsPapCutCol());
				put("newsPaperRemark", documentsColl.get().getNewsPaperRemark());

				put("leadCauseOfDeath", leadSheet.get().getLeadCauseOfDeath());
				put("agencyConclusion", leadSheet.get().getAgencyConclusion());
				put("agencyRemark", leadSheet.get().getAgencyRemark());
				put("agencyName", leadSheet.get().getAgencyName());
				put("agencyContact", leadSheet.get().getAgencyContact());

			}
		}).writeToFile(outFileName);
		// System.out.println("outFileName is "+outFileName);
		return outFileName;
	}

	public void sendEmail(String repName, String caseId) throws MessagingException {
		String from = "aspaksharif@gmail.com";
		String to = "mumbai.cag@gmail.com";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setSubject("Here's your e-report for case Id " + caseId);
		helper.setFrom(from);
		helper.setTo(to);

		helper.setText("<b>Dear team</b>,<br><i>Please find the report attached.</i>", true);

		FileSystemResource file = new FileSystemResource(new File(repName));
		helper.addAttachment(caseId + ".docx", file);

		mailSender.send(message);
	}

}
