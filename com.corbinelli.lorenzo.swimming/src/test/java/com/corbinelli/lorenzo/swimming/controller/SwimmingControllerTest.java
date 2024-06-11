package com.corbinelli.lorenzo.swimming.controller;

import static org.mockito.Mockito.ignoreStubs;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.corbinelli.lorenzo.swimming.model.Swimmer;

public class SwimmingControllerTest {
	
	@Mock
	private SwimmerRepository swimmerRepository;
	
	@Mock
	private SwimmerView swimmerView;
	
	@InjectMocks
	private SwimmingController swimmingController;

	private AutoCloseable closeable;

	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
	}
	
	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}
	
	@Test
	public void testAllSwimmers() {
		List<Swimmer> swimmers = Arrays.asList(new Swimmer());
		when(swimmerRepository.findAll()).thenReturn(swimmers);
		swimmingController.allSwimmers();
		verify(swimmerView).showAllSwimmers(swimmers);
	}
	
	@Test
	public void testNewSwimmerWhenItDoesNotAlreadyExist() {
		Swimmer swimmer = new Swimmer("1", "test", "testGender", "testStroke");
		when(swimmerRepository.findById("1")).thenReturn(null);
		swimmingController.newSwimmer(swimmer);
		InOrder inOrder = inOrder(swimmerRepository, swimmerView);
		inOrder.verify(swimmerRepository).save(swimmer);
		inOrder.verify(swimmerView).swimmerAdded(swimmer);
	}
	
	@Test
	public void testNewSwimmerWhenItAlreadyExists() {
		Swimmer existingSwimmer = new Swimmer("1", "existing", "testGender", "testStroke");
		Swimmer swimmerToAdd = new Swimmer("1", "test", "testGender", "testStroke");
		when(swimmerRepository.findById("1")).thenReturn(existingSwimmer);
		swimmingController.newSwimmer(swimmerToAdd);
		verify(swimmerView).showError("Already existing swimmer with id 1", existingSwimmer);
		verifyNoMoreInteractions(ignoreStubs(swimmerRepository));
	}

}
