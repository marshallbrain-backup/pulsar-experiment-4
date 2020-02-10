package com.marshalldbrain.pulsar.colony.construction

import com.marshalldbrain.pulsar.resources.Resource

interface Constructable {
	
	val name: String
	val time: Int
	val cost: List<Resource>

}