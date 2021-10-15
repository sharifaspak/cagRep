/**
 * 
 */
package com.cag.service;

import java.util.List;
import java.util.Optional;

import com.cag.model.entity.HabitsNMedHist;

/**
 * @author aspak.avesh.sharif
 *
 */
public interface HabitsNMedHistService {

	Optional<HabitsNMedHist> fetchHabitsNMedHist(String id);

	HabitsNMedHist updateHabitsNMedHist(HabitsNMedHist habitsNMedHist);

	List<HabitsNMedHist> updateHabitsNMedHist(List<HabitsNMedHist> habitsNMedHistList);

}
