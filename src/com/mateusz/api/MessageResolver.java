package com.mateusz.api;

import java.util.ArrayList;
import java.util.List;

import com.mateusz.api.algorithm.Algorithm;

public abstract class MessageResolver {

	private List<Algorithm> algorithms = new ArrayList<Algorithm>();

	public abstract void resolve(String message);
	
	public abstract void resolve(Message message);
	
	public final void performAlgorithms(Message message){
		for(Algorithm alg : algorithms){
			alg.perform(message);
		}
	}
	
	public final void performAlgorithms(String message){
		for(Algorithm alg : algorithms){
			alg.perform(message);
		}
	}

	public void addAlgorithm(Algorithm algorithm) {
		algorithms.add(algorithm);
	}
	
}
