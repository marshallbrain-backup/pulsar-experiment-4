package com.marshalldbrain.pulsar.resources

import io.kotlintest.matchers.collections.shouldContainAll
import io.kotlintest.matchers.collections.shouldContainExactly
import io.kotlintest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotlintest.specs.FunSpec
import kotlin.math.sign

class ResourceBucketTest : FunSpec({
	
	test("totalProduction") {
		
		val collection = listOf(
			Collection(
				setOf(
					Resource(ResourceType("test1"), 10),
					Resource(ResourceType("test2"), 5)
				),
				setOf(
					Resource(ResourceType("test1"), 5)
				)
			),
			Collection(
				setOf(
					Resource(ResourceType("test1"), 5)
				),
				setOf(
					Resource(ResourceType("test1"), 5),
					Resource(ResourceType("test2"), 5)
				)
			)
		)
		
		val expected = setOf(
			Resource(ResourceType("test1"), 5),
			Resource(ResourceType("test2"), 0)
		)
		
		val bucket = ResourceBucket(collection)
		
		bucket.totalProduction.shouldContainExactly(expected)
	
	}
	
})

private class Collection(
	override val production: Set<Resource>,
	override val upkeep: Set<Resource>
) : ResourceCollection