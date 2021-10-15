/**
 * 
 */
package com.cag.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cag.model.entity.LeadDetails;

/**
 * @author aspak.avesh.sharif
 *
 */
@Repository
public interface LeadDetailsRepository extends MongoRepository<LeadDetails, String> {

}
