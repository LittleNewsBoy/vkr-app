package com.app.vkr.repo;

import com.app.vkr.entity.AON;
import com.app.vkr.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AONRepository extends JpaRepository<AON,Long> {

	@Query("select c from AON c " +
			"where lower(c.product) like lower(concat('%', :searchTerm, '%'))")
	List<AON> search(@Param("searchTerm") String searchTerm);

	@Query("select c from AON c " +
			"where lower(c.username) like lower(concat('%', :searchTerm, '%'))")
	List<AON> searchByName(@Param("searchTerm") String searchTerm);
}
