package com.blautech.userprofile_microservice.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

import jakarta.persistence.*;


@Entity
@Data
@Table(name="userprofile")
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name = "user_id", nullable = false)
    private Integer userId;
	
	@Column(name="first_name", length = 100)
	private String firstName;

	@Column(name="last_name", length = 100)
	private String lastName;

	@Column(name="shipping_address", columnDefinition = "TEXT")
	private String shippingAddress;

	@Column(name="date_of_birth")
	private Date dateOfBirth;

}
