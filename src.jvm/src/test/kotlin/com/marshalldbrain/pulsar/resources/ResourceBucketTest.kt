package com.marshalldbrain.pulsar.resources

import io.kotlintest.matchers.collections.shouldBeEmpty
import io.kotlintest.matchers.collections.shouldContainAll
import io.kotlintest.matchers.collections.shouldContainExactly
import io.kotlintest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotlintest.matchers.types.shouldBeSameInstanceAs
import io.kotlintest.specs.FunSpec
import kotlin.math.sign

class ResourceBucketTest : FunSpec({
	
	test("totalProduction") {
		
		val collection = listOf(
			Collection(
				setOf(
					Resource(ResourceType("test1"), 10),
					Resource(ResourceType("test2"), 5),
					Resource(ResourceType("test1"), -5)
				)
			),
			Collection(
				setOf(
					Resource(ResourceType("test1"), 5),
					Resource(ResourceType("test1"), -5),
					Resource(ResourceType("test2"), -5)
				)
			)
		)
		
		val expected = setOf(
			Resource(ResourceType("test1"), 5),
			Resource(ResourceType("test2"), 0)
		)
		
		val bucket = ResourceBucket(collection)
		
		bucket.income.shouldContainExactly(expected)
	
	}
	
})

class Collection(
	override val income: Set<Resource>
) : ResourceCollection