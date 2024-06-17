package com.app.vkr.service;


import com.app.vkr.entity.AON;
import com.app.vkr.entity.AppUser;
import com.app.vkr.repo.AONRepository;
import com.app.vkr.repo.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class AONService {
	private final AONRepository aonRepository;

	public AONService(AONRepository aonRepository) {
		this.aonRepository = aonRepository;
	}
	public List<AON> findAllAON(String stringFilter) {
		if (stringFilter == null || stringFilter.isEmpty()) {
			return aonRepository.findAll();
		} else {
			return aonRepository.search(stringFilter);
		}
	}
	public List<AON> findAllAONToReport() {
		return aonRepository.findAll();
	}

	public List<AON> findAllByUsernameAON() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return aonRepository.searchByName(username);
	}

	public long countAON(){
		return aonRepository.count();
	}

	public void saveAON(AON aon) {
		if (aon == null) {
			System.err.println("AON is null. Are you sure you have connected your form to the application?");
			return;
		}
		aon.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		aonRepository.save(aon);
	}

	public void deleteAON(AON aon) {
		aonRepository.delete(aon);
	}

	public List<AON> findByDate(LocalDate begin, LocalDate end){

		return aonRepository.searchByDate(Date.valueOf(begin),Date.valueOf(end));
	}
}
