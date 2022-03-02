package io.spring.cloud.samples.animalrescue.backend.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Sets;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnimalServiceTest {

	@Mock
	private AdoptionRequestRepository adoptionRequestRepository;

	@Mock
	private AnimalRepository animalRepository;

	AnimalService animalService;

	@BeforeEach
	void setUp() {
		animalService = new AnimalService(animalRepository, adoptionRequestRepository);
	}

	@Test
	void getAllAnimalsAndAdoptions() {
		Animal kitty = new Animal();
		AdoptionRequest adoptKitty = new AdoptionRequest();
		kitty.setId(1L);
		kitty.setAdoptionRequests(Sets.newSet(adoptKitty));

		Animal boots = new Animal();
		AdoptionRequest adoptBoots1 = new AdoptionRequest();
		AdoptionRequest adoptBoots2 = new AdoptionRequest();
		boots.setId(2L);
		kitty.setAdoptionRequests(Sets.newSet(adoptBoots1, adoptBoots2));

		Flux<Animal> animals = Flux.just(kitty, boots);
		when(animalRepository.findAll()).thenReturn(animals);
		when(adoptionRequestRepository.findByAnimal(kitty.getId())).thenReturn(Flux.just(adoptKitty));
		when(adoptionRequestRepository.findByAnimal(boots.getId())).thenReturn(Flux.just(adoptBoots1, adoptBoots2));

		List<Animal> allAnimals = animalService.getAllAnimalsAndAdoptions().toStream().collect(Collectors.toList());
		assertThat(allAnimals).containsExactlyElementsOf(animals.toIterable());
		assertThat(allAnimals.get(0).getAdoptionRequests()).containsExactlyElementsOf(Sets.newSet(adoptKitty));
		assertThat(allAnimals.get(1).getAdoptionRequests()).containsAll(Sets.newSet(adoptBoots1, adoptBoots2));
	}
}