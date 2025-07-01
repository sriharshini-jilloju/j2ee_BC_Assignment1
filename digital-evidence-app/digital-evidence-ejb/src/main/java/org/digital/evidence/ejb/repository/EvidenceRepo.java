package org.digital.evidence.ejb.repository;

import java.util.List;

import org.digital.evidence.ejb.entity.Evidence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvidenceRepo extends JpaRepository<Evidence, Long>{
	 List<Evidence> findByCustodyStatus(String status);
}
