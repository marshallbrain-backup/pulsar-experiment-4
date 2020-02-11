package com.marshalldbrain.pulsar.resources

class ResourceBucket (vararg sources: List<ResourceCollection>) {
	
	private val sourceList = sources.asList()
	private val bucket = mutableMapOf<ResourceType, Resource>()
	
	val totalProduction: Set<Resource>
		get() {
			
			val production = mutableMapOf<ResourceType, Resource>()
			
			sourceList.forEach { s -> s.forEach {
				production += it.production
				production -= it.upkeep
			}}
			
			return production.values.toSet()
			
		}
	
	val bank: Set<Resource>
		get() = bucket.values.toSet()
	
	fun collectResources() {
		bucket += totalProduction
	}
	
}