package us.gaje.efiling.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import us.gaje.efiling.model.Court;

public interface CourtRepository extends JpaRepository<Court, String>{

	public Court findByExternalId(String externalId);
	
}
