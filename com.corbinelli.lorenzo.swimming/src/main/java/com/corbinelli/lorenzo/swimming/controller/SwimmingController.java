package com.corbinelli.lorenzo.swimming.controller;

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

}
