package com.marshalldbrain.pulsar.colony.construction

import com.marshalldbrain.pulsar.resources.ResourceType

interface Constructible {
	
	val id: String
	val time: Int
	val cost: Map<ResourceType, Int>

}