package com.marshalldbrain.pulsar.resources

interface ResourcePing {
	
	fun pingChange(change: Map<ResourceType, Int>, modifier: Int = 1)
	
}