package com.corbinelli.lorenzo.swimming.controller;

import java.util.List;

import com.corbinelli.lorenzo.swimming.model.Swimmer;

public interface SwimmerRepository {

	public List<Swimmer> findAll();

}
