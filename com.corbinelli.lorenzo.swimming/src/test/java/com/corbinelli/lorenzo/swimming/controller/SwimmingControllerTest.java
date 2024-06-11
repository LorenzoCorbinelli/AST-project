package com.corbinelli.lorenzo.swimming.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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

}
