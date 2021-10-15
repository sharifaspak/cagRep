/**
 * 
 */
package com.cag.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cag.model.entity.PolicyHolderPD;
import com.cag.repository.PolicyHolderPDRepository;

/**
 * @author aspak.avesh.sharif
 *
 */
@Service
public class PolicyHolderPDServiceImpl implements PolicyHolderPDService {

	@Autowired
	PolicyHolderPDRepository policyHolderPDRepository;

	@Override
	public Optional<PolicyHolderPD> fetchPolicyHolderPD(String id) {

		return policyHolderPDRepository.findById(id);
	}

	@Override
	public PolicyHolderPD updatePolicyHolderPD(PolicyHolderPD policyHolderPD) {
		// TODO Auto-generated method stub
		return policyHolderPDRepository.save(policyHolderPD);
	}

	@Override
	public List<PolicyHolderPD> updatePolicyHolderPD(List<PolicyHolderPD> policyHolderPDList) {
		// TODO Auto-generated method stub
		return policyHolderPDRepository.saveAll(policyHolderPDList);
	}

}
