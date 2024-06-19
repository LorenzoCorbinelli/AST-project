package com.corbinelli.lorenzo.swimming.view;

import java.util.List;

import com.corbinelli.lorenzo.swimming.model.Swimmer;

public interface SwimmerView {

	public void showAllSwimmers(List<Swimmer> swimmers);

	public void swimmerAdded(Swimmer swimmer);

	public void showError(String message, Swimmer swimmer);

	public void swimmerRemoved(Swimmer swimmer);

	public void showErrorSwimmerNotFound(String message, Swimmer swimmer);

}
