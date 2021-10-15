/**
 * 
 */
package com.cag.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cag.model.entity.NeighbNEmpRef;
import com.cag.repository.NeighbNEmpRefRep;

/**
 * @author aspak.avesh.sharif
 *
 */
@Service
public class NeighbNEmpRefServiceImpl implements NeighbNEmpRefService {

	@Autowired
	NeighbNEmpRefRep neighbNEmpRefRep;

	@Override
	public Optional<NeighbNEmpRef> fetchNeighbNEmpRef(String id) {

		return neighbNEmpRefRep.findById(id);
	}

	@Override
	public NeighbNEmpRef updateNeighbNEmpRef(NeighbNEmpRef neighbNEmpRef) {
		// TODO Auto-generated method stub
		return neighbNEmpRefRep.save(neighbNEmpRef);
	}

	@Override
	public List<NeighbNEmpRef> updateNeighbNEmpRef(List<NeighbNEmpRef> neighbNEmpRefList) {
		// TODO Auto-generated method stub
		return neighbNEmpRefRep.saveAll(neighbNEmpRefList);

	}

}
