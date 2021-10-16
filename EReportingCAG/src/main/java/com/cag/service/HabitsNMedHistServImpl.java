/**
 * 
 */
package com.cag.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cag.model.entity.HabitsNMedHist;
import com.cag.repository.HabitsNMedHistRep;
import com.cag.security.JwtUserDetailsService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author aspak.avesh.sharif
 *
 */
@Service
@Slf4j
public class HabitsNMedHistServImpl implements HabitsNMedHistService {

	@Autowired
	HabitsNMedHistRep habitsNMedHistRep;

	@Override
	public Optional<HabitsNMedHist> fetchHabitsNMedHist(String id) {

		return habitsNMedHistRep.findById(id);
	}

	@Override
	public HabitsNMedHist updateHabitsNMedHist(HabitsNMedHist habitsNMedHist) {
		// TODO Auto-generated method stub
		return habitsNMedHistRep.save(habitsNMedHist);
	}

	@Override
	public List<HabitsNMedHist> updateHabitsNMedHist(List<HabitsNMedHist> habitsNMedHistList) {
		// TODO Auto-generated method stub
		return habitsNMedHistRep.saveAll(habitsNMedHistList);
	}

}
