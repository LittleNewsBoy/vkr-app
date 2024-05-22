package com.app.vkr.repo;

import com.app.vkr.entity.AON;
import com.app.vkr.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface AONRepository extends JpaRepository<AON,Long> {

	@Query("select c from AON c " +
			"where lower(c.product) like lower(concat('%', :searchTerm, '%'))")
	List<AON> search(@Param("searchTerm") String searchTerm);

	@Query("select c from AON c " +
			"where lower(c.username) like lower(concat('%', :searchTerm, '%'))")
	List<AON> searchByName(@Param("searchTerm") String searchTerm);

	//@Query("from AON where date <= begin and date >= end")
	@Query(nativeQuery = true, value = "select * from AON c where c.date >= :begin and c.date <= :end")
	List<AON> searchByDate(Date begin, Date end);
}
