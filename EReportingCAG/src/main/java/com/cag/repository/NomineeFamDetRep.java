/**
 * 
 */
package com.cag.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cag.model.entity.NomineeFamDts;

/**
 * @author aspak.avesh.sharif
 *
 */
@Repository
public interface NomineeFamDetRep extends MongoRepository<NomineeFamDts, String> {

}
