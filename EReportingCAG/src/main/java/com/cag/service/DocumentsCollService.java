/**
 * 
 */
package com.cag.service;

import java.util.List;
import java.util.Optional;

import com.cag.model.entity.DocumentsColl;

/**
 * @author aspak.avesh.sharif
 *
 */
public interface DocumentsCollService {

	DocumentsColl updateDocumentsColl(DocumentsColl documentsColl);

	List<DocumentsColl> updateDocumentsColl(List<DocumentsColl> documentsCollList);

	Optional<DocumentsColl> fetchDocumentsColl(String id);

}
