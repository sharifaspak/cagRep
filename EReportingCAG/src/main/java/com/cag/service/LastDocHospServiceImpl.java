/**
 * 
 */
package com.cag.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cag.model.entity.LastDocHosp;
import com.cag.repository.LastDocHospRep;
import com.cag.security.JwtUserDetailsService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author aspak.avesh.sharif
 *
 */
@Service
@Slf4j
public class LastDocHospServiceImpl implements LastDocHospService {

	@Autowired
	LastDocHospRep lastDocHospRep;

	@Override
	public Optional<LastDocHosp> fetchLastDocHosp(String id) {

		return lastDocHospRep.findById(id);
	}

	@Override
	public LastDocHosp updateLastDocHosp(LastDocHosp lastDocHosp) {
		// TODO Auto-generated method stub
		return lastDocHospRep.save(lastDocHosp);
	}

	@Override
	public List<LastDocHosp> updateLastDocHosp(List<LastDocHosp> lastDocHospList) {
		// TODO Auto-generated method stub
		return lastDocHospRep.saveAll(lastDocHospList);

	}

}
