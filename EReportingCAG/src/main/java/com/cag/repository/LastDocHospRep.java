/**
 * 
 */
package com.cag.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cag.model.entity.LastDocHosp;

/**
 * @author aspak.avesh.sharif
 *
 */
@Repository
public interface LastDocHospRep extends MongoRepository<LastDocHosp, String> {

}
