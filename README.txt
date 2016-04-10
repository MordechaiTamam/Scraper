This is a spring-boot and maven based application.

 The DemoApplication.class has a main method which you can run from your IDE.
 If you choose to run the program from the command line, just execute it with 'java -jar <PATH TO JAR>

 Available configurations:
 if you have the source code, you can look at the application.properties file, there you can find
 two properties you can configure:
 1) urls.to.scrap
 2) num.of.products

 If you like to run from command line with different values than the default ones,you can execute something like
 this:
 java -jar <PATH TO JAR> --num.of.products=<YOUR NUM> --urls.to.scrap=<YOUR URL>
