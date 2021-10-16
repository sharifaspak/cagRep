/**
 * 
 */
package com.cag.security;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author aspak.avesh.sharif
 *
 */
@Repository
public interface AgentRepository extends MongoRepository<Agent, String> {

	List<Agent> findByAgency(String agencyName);

}
