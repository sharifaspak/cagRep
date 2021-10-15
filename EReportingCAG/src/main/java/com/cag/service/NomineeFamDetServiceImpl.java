/**
 * 
 */
package com.cag.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cag.model.entity.NomineeFamDts;
import com.cag.repository.NomineeFamDetRep;

/**
 * @author aspak.avesh.sharif
 *
 */
@Service
public class NomineeFamDetServiceImpl implements NomineeFamDetService {

	@Autowired
	NomineeFamDetRep nomineeFamDetRep;

	@Override
	public Optional<NomineeFamDts> fetchNomineeFamDts(String id) {

		return nomineeFamDetRep.findById(id);
	}

	@Override
	public NomineeFamDts updateNomineeFamDts(NomineeFamDts nomineeFamDts) {

		return nomineeFamDetRep.save(nomineeFamDts);
	}

	@Override
	public List<NomineeFamDts> updateNomineeFamDts(List<NomineeFamDts> nomineeFamDtsList) {
		return nomineeFamDetRep.saveAll(nomineeFamDtsList);
	}

}
