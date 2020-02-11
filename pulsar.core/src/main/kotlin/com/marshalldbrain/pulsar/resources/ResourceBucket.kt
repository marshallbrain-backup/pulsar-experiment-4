package com.marshalldbrain.pulsar.resources

class ResourceBucket (vararg sources: List<ResourceCollection>) {
	
	private val sourceList = sources.asList()
	
	val totalProduction: Set<Resource>
		get() {
			
			val production = mutableMapOf<ResourceType, Resource>()
			
			sourceList.forEach { s -> s.forEach {
				production += it.production
				production -= it.upkeep
			}}
			
			return production.values.toSet()
			
		}
	
}