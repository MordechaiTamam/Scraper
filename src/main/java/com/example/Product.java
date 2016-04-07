package com.example;

/**
 * Created by modi on 07/04/16.
 */
public class Product {
	String name;
	String price;

	public Product(String name, String price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public String getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "Product{" +
				"name='" + name + '\'' +
				", price='" + price + '\'' +
				'}';
	}
}
