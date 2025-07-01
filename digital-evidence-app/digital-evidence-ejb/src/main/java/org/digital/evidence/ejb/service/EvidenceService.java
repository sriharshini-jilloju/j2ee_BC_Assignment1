package org.digital.evidence.ejb.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.digital.evidence.ejb.entity.Evidence;
import org.digital.evidence.ejb.repository.EvidenceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional 
public class EvidenceService {

	@Autowired
	private EvidenceRepo evidenceRepo;
	


	public void saveEvidence(Evidence evidence) {
		evidenceRepo.save(evidence);
	}

	public List<Evidence> getAllEvidence() {
		return evidenceRepo.findAll();
	}
	
	public List<Evidence> filterEvidenceByCustodyStatus(String status){
		return evidenceRepo.findByCustodyStatus(status);
	}

	public Evidence getEvidenceById(Long id) {
		Optional<Evidence> evidence = evidenceRepo.findById(id);
		return evidence.isPresent() ? evidence.get() : null;
	}
	


}
