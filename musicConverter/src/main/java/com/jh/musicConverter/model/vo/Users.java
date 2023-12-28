package com.jh.musicConverter.model.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(name="seq_userId", sequenceName="seq_userId", allocationSize = 1)
public class Users {
	
	@Id
	@GeneratedValue(generator = "seq_userId", strategy = GenerationType.SEQUENCE)
	private int userId;
	
	private String name;

}
