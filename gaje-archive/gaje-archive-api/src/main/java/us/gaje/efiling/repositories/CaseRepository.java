package us.gaje.efiling.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import us.gaje.efiling.model.CaseRecord;

public interface CaseRepository extends JpaRepository<CaseRecord, String>{
	
	

}
