package com.app.vkr.repo;

import com.app.vkr.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

	Optional<AppUser> findByUsername(String username);
	@Query("select c from AppUser c " +
			"where lower(c.username) like lower(concat('%', :searchTerm, '%'))")
	List<AppUser> search(@Param("searchTerm") String searchTerm);
}
