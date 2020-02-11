package com.marshalldbrain.pulsar.resources

interface ResourceCollection {
	
	val production: Set<Resource>
	val upkeep: Set<Resource>
	
}