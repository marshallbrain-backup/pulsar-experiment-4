package com.marshalldbrain.pulsar.resources

operator fun MutableMap<ResourceType, Resource>.plusAssign(resources: Set<Resource>) {
	
	resources.forEach {
	
		val value = this[it.type]
		if (value != null) {
			value += it
		} else {
			this[it.type] = it
		}
	
	}
	
}

operator fun MutableMap<ResourceType, Resource>.minusAssign(resources: Set<Resource>) {
	
	resources.forEach {
		
		val value = this[it.type]
		if (value != null) {
			value -= it
		} else {
			this[it.type] = it
		}
		
	}
	
}