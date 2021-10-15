/**
 * 
 */
package com.cag.service;

import java.util.List;
import java.util.Optional;

import com.cag.model.entity.LastDocHosp;

/**
 * @author aspak.avesh.sharif
 *
 */
public interface LastDocHospService {

	List<LastDocHosp> updateLastDocHosp(List<LastDocHosp> lastDocHospList);

	Optional<LastDocHosp> fetchLastDocHosp(String id);

	LastDocHosp updateLastDocHosp(LastDocHosp lastDocHosp);

}
