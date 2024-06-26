package com.corbinelli.lorenzo.swimming.repository;

import java.util.List;

import com.corbinelli.lorenzo.swimming.model.Swimmer;

public interface SwimmerRepository {

	public List<Swimmer> findAll();

	public Swimmer findById(String id);

	public void save(Swimmer swimmer);

	public void delete(String id);

}
