/**
 * 
 */
package com.cag.service;

import java.util.List;
import java.util.Optional;

import com.cag.model.entity.DeathCertf;

/**
 * @author aspak.avesh.sharif
 *
 */
public interface DeathCertfService {

	Optional<DeathCertf> fetchDeathCertf(String id);

	DeathCertf updateDeathCertf(DeathCertf deathCertf);

	List<DeathCertf> updateDeathCertf(List<DeathCertf> deathCertfList);

}
