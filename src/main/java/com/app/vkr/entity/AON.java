package com.app.vkr.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "AON")
public class AON {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date date;
	private String location;
	private String numberAON;
	private String product;
	private String decimalNumber;
	private String serialNumber;
	private Boolean manufacturingD;
	private Boolean structuralD;
	private Boolean culpritNotFound;
	private String description;
	private String decision;
	private String username;


	public AON() {
	}
}

