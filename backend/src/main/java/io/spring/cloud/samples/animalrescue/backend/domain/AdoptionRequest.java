package io.spring.cloud.samples.animalrescue.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdoptionRequest {
	@Id
	private Long id;

	private String adopterName;

	private String email;

	private String notes;

	private Long animal;
}
