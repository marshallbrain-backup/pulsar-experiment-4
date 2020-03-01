package com.marshalldbrain.pulsar.resources

interface ResourceUpdater {
	
	fun update(change: Map<ResourceType, Int>, modifier: Int = 1)
	
}