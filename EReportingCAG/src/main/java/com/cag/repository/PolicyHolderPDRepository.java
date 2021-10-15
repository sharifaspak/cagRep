/**
 * 
 */
package com.cag.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cag.model.entity.PolicyHolderPD;

/**
 * @author aspak.avesh.sharif
 *
 */
@Repository
public interface PolicyHolderPDRepository extends MongoRepository<PolicyHolderPD, String> {

}
