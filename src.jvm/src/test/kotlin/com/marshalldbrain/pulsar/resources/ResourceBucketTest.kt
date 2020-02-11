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
	
	context("Bank") {
		
		test("collection") {
			
			val collection = listOf(
				Collection(
					setOf(
						Resource(ResourceType("1"), 10)
					),
					setOf(
						Resource(ResourceType("1"), 5)
					)
				)
			)
			
			val expected1 = setOf(
				Resource(ResourceType("1"), 5)
			)
			
			val expected2 = setOf(
				Resource(ResourceType("1"), 10)
			)
			
			val bucket = ResourceBucket(collection)
			
			bucket.bank.shouldBeEmpty()
			bucket.collectResources()
			bucket.bank.shouldContainExactly(expected1)
			bucket.collectResources()
			bucket.bank.shouldContainExactly(expected2)
			
		}
		
		test("same instance") {
			
			val bucket = ResourceBucket(emptyList())
			
			bucket.bank.shouldBeSameInstanceAs(bucket.bank)
			
		}
		
	}
	
})

private class Collection(
	override val production: Set<Resource>,
	override val upkeep: Set<Resource>
) : ResourceCollection