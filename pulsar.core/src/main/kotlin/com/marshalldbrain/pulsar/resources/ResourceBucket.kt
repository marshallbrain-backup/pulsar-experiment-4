package com.marshalldbrain.pulsar.resources

class ResourceBucket (vararg sources: List<ResourceCollection>) : ResourceCollection {
	
	private val sourceList = sources.asList()
	private val bucket = mutableMapOf<ResourceType, Resource>()
	
	override val income: Set<Resource>
		get() {
			
			val production = mutableMapOf<ResourceType, Resource>()
			
			sourceList.forEach { s -> s.forEach {
				production += it.income
			}}
			
			return production.values.toSet()
			
		}
	
}