package us.gaje.efiling.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import us.gaje.efiling.model.User;

public interface UserRepository extends JpaRepository<User, String>
{

	public User findByUsername(String username);
	
	
}
