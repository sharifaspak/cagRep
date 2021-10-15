/**
 * 
 */
package com.cag.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cag.model.entity.DocumentsColl;

/**
 * @author aspak.avesh.sharif
 *
 */
@Repository
public interface DocumentsCollRep extends MongoRepository<DocumentsColl, String> {

}
