package com.marshalldbrain.pulsar.resources

interface ResourceTeller {
	
	fun deposit(resources: Set<Resource>)
	fun withdraw(resources: Set<Resource>)
	
}