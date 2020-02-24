package com.marshalldbrain.pulsar.colony.construction

import com.marshalldbrain.pulsar.resources.Resource

interface Constructible {
	
	val id: String
	val time: Int
	val cost: List<Resource>

}