/**
 * 
 */
package com.cag.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cag.model.entity.Ereporting;

/**
 * @author aspak.avesh.sharif
 *
 */
@Repository
public interface EreportingRepository extends MongoRepository<Ereporting, String> {

}
