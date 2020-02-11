package com.marshalldbrain.pulsar.resources

data class Resource (
	val type: ResourceType,
	var amount: Int = 0
) {
	
	operator fun plusAssign(resource: Resource) {
		if (type == resource.type) {
			amount += resource.amount
		}
	}
	
	operator fun minusAssign(resource: Resource) {
		if (type == resource.type) {
			amount -= resource.amount
		}
	}
	
}