package io.spring.cloud.samples.animalrescue.backend.domain;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.stream.Collectors;

@Service
public class AnimalService {
	private final AnimalRepository animalRepository;
	private final AdoptionRequestRepository adoptionRequestRepository;

	public AnimalService(AnimalRepository animalRepository, AdoptionRequestRepository adoptionRequestRepository) {
		this.animalRepository = animalRepository;
		this.adoptionRequestRepository = adoptionRequestRepository;
	}

	public Flux<Animal> getAllAnimalsAndAdoptions() {
		// This code is prioritized to be more readable and maintainable
		// and causes the "N+1 selects problem". Take care of referring to the code.
		Flux<Animal> animalFlux = animalRepository.findAll()
			.delayUntil(animal -> adoptionRequestRepository.findByAnimal(animal.getId())
				.collect(Collectors.toSet())
				.doOnNext(animal::setAdoptionRequests));
		return animalFlux;
	}
}
