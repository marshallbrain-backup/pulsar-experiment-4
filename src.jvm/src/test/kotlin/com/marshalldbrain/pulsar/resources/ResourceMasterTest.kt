package com.marshalldbrain.pulsar.resources

import io.kotlintest.matchers.collections.shouldBeEmpty
import io.kotlintest.matchers.collections.shouldContainExactly
import io.kotlintest.matchers.types.shouldBeSameInstanceAs
import io.kotlintest.specs.FunSpec

class ResourceMasterTest : FunSpec({
	
	test("collection") {
		
		val collection = listOf(
			Collection(
				setOf(
					Resource(ResourceType("1"), 10),
					Resource(ResourceType("1"), -5)
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
		val actual = ResourceMaster().apply {
			sourceList.add(bucket)
		}
		
		actual.bank.toSet().shouldBeEmpty()
		actual.collectResources()
		actual.bank.toSet().shouldContainExactly(expected1)
		actual.collectResources()
		actual.bank.toSet().shouldContainExactly(expected2)
		
	}
	
	test("Deposit") {
		
		val teller = ResourceMaster()
		val resource = Resource(ResourceType("test"), 10)
		
		teller.deposit(setOf(resource))
		
		teller.bank.toSet().shouldContainExactly(Resource(ResourceType("test"), 10))
		
	}
	
	test("Withdraw") {
		
		val teller = ResourceMaster()
		val resource = Resource(ResourceType("test"), 10)
		
		teller.withdraw(setOf(resource))
		
		teller.bank.toSet().shouldContainExactly(Resource(ResourceType("test"), -10))
		
	}
	
	test("Resource income collection") {
		TODO("not implemented")
	}
	
}) {

}

fun Map<ResourceType, Pair<Resource, Resource>>.toSet() = this.values.map { it.first }