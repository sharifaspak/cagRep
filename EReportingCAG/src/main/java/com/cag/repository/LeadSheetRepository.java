/**
 * 
 */
package com.cag.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.cag.model.entity.LeadSheet;

/**
 * @author aspak.avesh.sharif
 *
 */
@Repository
public interface LeadSheetRepository extends PagingAndSortingRepository<LeadSheet, String> {

	List<LeadSheet> findByFieldAgentNameAndLeadStatusAndLeadRecievedDateBetween(String agentName, String leadStatus,
			Date from, Date to, Pageable pageable);

	List<LeadSheet> findByLeadStatusAndLeadRecievedDateBetween(String leadStatus, Date from, Date to,
			Pageable pageable);

	List<LeadSheet> findByBackendAgentNameAndLeadStatusAndLeadRecievedDateBetween(String agentName, String leadStatus,
			Date from, Date to, Pageable pageable);

	List<LeadSheet> findByLeadOwnerAndLeadStatusAndLeadRecievedDateBetween(String agentName, String leadStatus,
			Date from, Date to, Pageable pageable);
	@Query("{'policyHolderName' : {$regex:?0,$options:'i'}}")
	List<LeadSheet> findByPolicyHolderNameIgnoreCase(String policyHolderName, Pageable paging);

}
