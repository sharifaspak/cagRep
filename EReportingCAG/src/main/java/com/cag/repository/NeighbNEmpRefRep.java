/**
 * 
 */
package com.cag.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cag.model.entity.NeighbNEmpRef;

/**
 * @author aspak.avesh.sharif
 *
 */
@Repository
public interface NeighbNEmpRefRep extends MongoRepository<NeighbNEmpRef, String> {

}
