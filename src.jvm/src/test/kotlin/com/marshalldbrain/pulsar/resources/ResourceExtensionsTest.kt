package com.marshalldbrain.pulsar.resources

import io.kotlintest.matchers.maps.shouldContainExactly
import io.kotlintest.specs.FunSpec
import kotlin.math.exp


class ResourceExtensionsTest : FunSpec({
	
	test("Map plusAssign set") {
		
		val resourceSet = setOf(
			Resource(ResourceType("test1"), 5),
			Resource(ResourceType("test2"), 5),
			Resource(ResourceType("test3"), 5)
		)
		
		val actual = setOf(
			Resource(ResourceType("test2"), 5),
			Resource(ResourceType("test3"), -10),
			Resource(ResourceType("test4"), 5)
		).associateBy { it.type }.toMutableMap()
		
		val expected = setOf(
			Resource(ResourceType("test1"), 5),
			Resource(ResourceType("test2"), 10),
			Resource(ResourceType("test3"), -5),
			Resource(ResourceType("test4"), 5)
		).associateBy { it.type }
		
		actual += resourceSet
		
		actual.shouldContainExactly(expected)
		
	}
	
	test("Map minusAssign set") {
		
		val resourceSet = setOf(
			Resource(ResourceType("test1"), 5),
			Resource(ResourceType("test2"), 5),
			Resource(ResourceType("test3"), 5)
		)
		
		val actual = setOf(
			Resource(ResourceType("test2"), 5),
			Resource(ResourceType("test3"), 10),
			Resource(ResourceType("test4"), 5)
		).associateBy { it.type }.toMutableMap()
		
		val expected = setOf(
			Resource(ResourceType("test1"), -5),
			Resource(ResourceType("test2"), 0),
			Resource(ResourceType("test3"), 5),
			Resource(ResourceType("test4"), 5)
		).associateBy { it.type }
		
		actual -= resourceSet
		
		actual.shouldContainExactly(expected)
		
	}
	
})