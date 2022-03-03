package io.spring.cloud.samples.animalrescue.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Animal {

	@Id
	private Long id;

	private String name;

	private LocalDate rescueDate;

	private String avatarUrl;

	private String description;

	private Set<AdoptionRequest> adoptionRequests;
}
