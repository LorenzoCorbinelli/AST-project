package com.corbinelli.lorenzo.swimming.controller;

import com.corbinelli.lorenzo.swimming.model.Swimmer;
import com.corbinelli.lorenzo.swimming.repository.SwimmerRepository;
import com.corbinelli.lorenzo.swimming.view.SwimmerView;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class SwimmingController {

	private SwimmerView swimmerView;
	private SwimmerRepository swimmerRepository;

	@Inject
	public SwimmingController(@Assisted SwimmerView swimmerView, SwimmerRepository swimmerRepository) {
		this.swimmerView = swimmerView;
		this.swimmerRepository = swimmerRepository;
	}
	
	public void allSwimmers() {
		swimmerView.showAllSwimmers(swimmerRepository.findAll());
	}

	public void newSwimmer(Swimmer swimmer) {
		Swimmer existingSwimmer = swimmerRepository.findById(swimmer.getId());
		if(existingSwimmer != null) {
			swimmerView.showError("Already existing swimmer with id " + swimmer.getId(), existingSwimmer);
			return;
		}
		swimmerRepository.save(swimmer);
		swimmerView.swimmerAdded(swimmer);
	}

	public void deleteSwimmer(Swimmer swimmer) {
		if(swimmerRepository.findById(swimmer.getId()) == null) {
			swimmerView.showErrorSwimmerNotFound("No existing swimmer with id " + swimmer.getId(), swimmer);
			return;
		}
		swimmerRepository.delete(swimmer.getId());
		swimmerView.swimmerRemoved(swimmer);
	}

}
