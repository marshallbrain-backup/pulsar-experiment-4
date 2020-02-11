package com.marshalldbrain.pulsar.resources

data class Resource (
	val type: ResourceType,
	val amount: Int = 0
) {
	
	operator fun plus(resource: Resource): Resource {
		if (type == resource.type) {
			return Resource(type, amount + resource.amount)
		}
		return this
	}
	
	operator fun minus(resource: Resource): Resource {
		if (type == resource.type) {
			return Resource(type, amount - resource.amount)
		}
		return this
	}
	
}