/**
 * 
 */
package com.cag.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cag.model.entity.DocumentsColl;
import com.cag.repository.DocumentsCollRep;
import com.cag.security.JwtUserDetailsService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author aspak.avesh.sharif
 *
 */
@Service
@Slf4j
public class DocumentsCollServiceImpl implements DocumentsCollService {

	@Autowired
	DocumentsCollRep documentsCollrep;

	@Override
	public Optional<DocumentsColl> fetchDocumentsColl(String id) {

		return documentsCollrep.findById(id);
	}

	@Override
	public DocumentsColl updateDocumentsColl(DocumentsColl documentsColl) {
		// TODO Auto-generated method stub
		return documentsCollrep.save(documentsColl);
	}

	@Override
	public List<DocumentsColl> updateDocumentsColl(List<DocumentsColl> documentsCollList) {
		// TODO Auto-generated method stub
		return documentsCollrep.saveAll(documentsCollList);
	}

}
