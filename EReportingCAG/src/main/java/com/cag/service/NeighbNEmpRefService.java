/**
 * 
 */
package com.cag.service;

import java.util.List;
import java.util.Optional;

import com.cag.model.entity.NeighbNEmpRef;

/**
 * @author aspak.avesh.sharif
 *
 */
public interface NeighbNEmpRefService {

	Optional<NeighbNEmpRef> fetchNeighbNEmpRef(String id);

	NeighbNEmpRef updateNeighbNEmpRef(NeighbNEmpRef neighbNEmpRef);

	List<NeighbNEmpRef> updateNeighbNEmpRef(List<NeighbNEmpRef> neighbNEmpRefList);

}
