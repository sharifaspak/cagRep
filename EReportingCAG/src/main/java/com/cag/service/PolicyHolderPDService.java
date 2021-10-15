/**
 * 
 */
package com.cag.service;

import java.util.List;
import java.util.Optional;

import com.cag.model.entity.PolicyHolderPD;

/**
 * @author aspak.avesh.sharif
 *
 */
public interface PolicyHolderPDService {

	Optional<PolicyHolderPD> fetchPolicyHolderPD(String id);

	PolicyHolderPD updatePolicyHolderPD(PolicyHolderPD policyHolderPD);

	List<PolicyHolderPD> updatePolicyHolderPD(List<PolicyHolderPD> policyHolderPDList);

}
