package com.marshalldbrain.pulsar.colony.construction

enum class ConstructionType {
	
	BUILD {
		override fun name(target: Constructible): String {
			return "Building ${target.id}"
		}
	};
	
	abstract fun name(target: Constructible): String
	
}