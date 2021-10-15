/**
 * 
 */
package com.cag.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cag.model.entity.HabitsNMedHist;

/**
 * @author aspak.avesh.sharif
 *
 */
public interface HabitsNMedHistRep extends MongoRepository<HabitsNMedHist, String> {

}
