/**
 * 
 */
package com.cag.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cag.model.entity.DeathCertf;
import com.cag.repository.DeathCertfRep;

/**
 * @author aspak.avesh.sharif
 *
 */
@Service
public class DeathCertfServiceImpl implements DeathCertfService {

	@Autowired
	DeathCertfRep deathCertfRep;

	@Override
	public Optional<DeathCertf> fetchDeathCertf(String id) {

		return deathCertfRep.findById(id);
	}

	@Override
	public DeathCertf updateDeathCertf(DeathCertf deathCertf) {
		// TODO Auto-generated method stub
		return deathCertfRep.save(deathCertf);
	}

	@Override
	public List<DeathCertf> updateDeathCertf(List<DeathCertf> deathCertfList) {
		// TODO Auto-generated method stub
		return deathCertfRep.saveAll(deathCertfList);
	}

}
