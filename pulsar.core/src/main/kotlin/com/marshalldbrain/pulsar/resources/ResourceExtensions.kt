package com.marshalldbrain.pulsar.resources

operator fun MutableMap<ResourceType, Resource>.plusAssign(resources: Set<Resource>) {
	
	resources.forEach {
	
		val value = this[it.type]
		if (value != null) {
			this[it.type] = value + it
		} else {
			this[it.type] = it
		}
	
	}
	
}

operator fun MutableMap<ResourceType, Resource>.minusAssign(resources: Set<Resource>) {
	
	resources.forEach {
		
		val value = this[it.type]
		if (value != null) {
			this[it.type] = value - it
		} else {
			this[it.type] = -it
		}
		
	}
	
}

operator fun Set<Resource>.times(i: Int): Set<Resource> {
	return this.map { Resource(it.type, it.amount * i) }.toSet()
}