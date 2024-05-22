package com.app.vkr.repo;

import com.app.vkr.entity.Todo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepo extends JpaRepository<Todo, Long> {
	@Transactional
	void deleteByDone(boolean done);
}
