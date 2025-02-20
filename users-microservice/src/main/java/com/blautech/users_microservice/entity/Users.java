package com.blautech.users_microservice.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Data
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Users {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="first_name", length = 100)
	private String firstName;

	@Column(name="last_name", length = 100)
	private String lastName;

	@Column(name="email", length = 100)
	private String email;

	@Column(name="password", length = 255)
	private String password;

	@Column(name="shipping_address", columnDefinition = "TEXT")
	private String shippingAddress;

	@Column(name="date_of_birth")
	private Date dateOfBirth;
}
