package com.example;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by modi on 07/04/16.
 */
public class Scraper {

	public static final String PRODUCTS_ACTIVE = "products active";
	public static final String PRODUCT_NAME = "product-name";
	public static final String PRODUCT_PRICE = "product-price";
	public static final String CLASS = "class";

	AtomicBoolean isDone = new AtomicBoolean(false);

	@Value("${urls.to.scrap}")
	private String urltoScrap;

	@Value("${num.of.products}")
	int numOfProducts;

	@Autowired
	ObjectMapper mapper;

	public void scrap() {
		before();
		Document doc;

		try {
			// need http protocol
			doc = Jsoup.connect(urltoScrap).get();
			// get page title
			String title = doc.title();
			System.out.println("title : " + title);
			Elements unordered = doc.getElementsByAttributeValue(CLASS, PRODUCTS_ACTIVE);
			Collection<Product> products = new LinkedList<Product>();
			for (Element ul : unordered) {
				Elements listItems = ul.select("li");
				int alreadyHandledProductsCount=0;
				for (Element listItem : listItems) {
					// get the value from href attribute
					if (!listItem.getElementsByClass(PRODUCT_NAME).isEmpty()) {
						Elements productName = listItem.getElementsByClass(PRODUCT_NAME);
						Element productNameElement = productName.get(0);
						Elements productPrices = listItem.getElementsByClass(PRODUCT_PRICE);
						Element productPrice = productPrices.get(0);
						String productNameTxt = productNameElement.text();
						String productPriceTxt = productPrice.text();
						products.add(new Product(productNameTxt,productPriceTxt));
						alreadyHandledProductsCount++;
						if(alreadyHandledProductsCount==numOfProducts){
							break;
						}
					}
				}
			}
			String jsonStr=mapper.writeValueAsString(products);
			System.out.println(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			isDone.set(true);
		}
	}

	private void before() {
		Runnable command = new Runnable() {
			@Override
			public void run() {
				while (!isDone.get()) {
					System.out.println("-");
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("done...");
			}
		};
		new Thread(command).start();
	}
}
