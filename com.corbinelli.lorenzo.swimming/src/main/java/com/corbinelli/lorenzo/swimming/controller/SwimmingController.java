package com.corbinelli.lorenzo.swimming.controller;

import com.corbinelli.lorenzo.swimming.model.Swimmer;

public class SwimmingController {

	private SwimmerView swimmerView;
	private SwimmerRepository swimmerRepository;

	public SwimmingController(SwimmerView swimmerView, SwimmerRepository swimmerRepository) {
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
		swimmerRepository.delete(swimmer.getId());
		swimmerView.swimmerRemoved(swimmer);
	}

}
