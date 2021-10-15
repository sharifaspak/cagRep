/**
 * 
 */
package com.cag.service;

import java.util.List;
import java.util.Optional;

import com.cag.model.entity.NomineeFamDts;

/**
 * @author aspak.avesh.sharif
 *
 */
public interface NomineeFamDetService {

	Optional<NomineeFamDts> fetchNomineeFamDts(String id);

	NomineeFamDts updateNomineeFamDts(NomineeFamDts nomineeFamDts);

	List<NomineeFamDts> updateNomineeFamDts(List<NomineeFamDts> nomineeFamDtsList);

}
