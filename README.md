# Castille Assesment

## Task 1 Description
Develop an authenticated Order Taking API stack that is exposed to 3rd party endpoints (example an
ERP system) selling products on behalf of Melita.
An order specifies the customer details (including personal details, installation address, preferred
installation date + time slot details), the required products (e.g. Internet, TV, Telephony, Mobile) and
the required package per product (e.g. Internet 250Mbps or 1Gbps, TV with 90 Channels or 140
Channels, Telephony with Free On net Calls or Unlimited Calls, Mobile Prepaid or Mobile Postpaid). The Order Taking API must validate these details and accept the order. On accepting the
order it must publish a messaging event to a RabbitMQ topic for Melita's Ordering Fulfilment system
and Care systems to pick it up:-

* Deliverables.

1) An authenticated public REST based Order Taking API stack.
2) Micro services to be implemented using Spring Boot
3) Publish events to a RabbitMQ
4) Code needs to be version controlled in a Git Repo
5) Expect very good code design and robust error handling, design and integration patterns


## Software Specifications
    * java 1.8
    * Maven
    * Springboot V2.5
    * H2 Database
    * RabbitMQ
    * Spring Security

    
## Important Links
   For api documentation I am using swagger-Open Document Api which can be accessed from below Link
      
   Swagger : http://localhost:8080/swagger-ui/index.html

   Some of the API links are described below:



    some of the links are:
 ## Product

1.```POST /product**```-Create New Product	 
```json        
    {
      "description": "Internet/Tv/Telephone/Mobile",
      "product_packages": [
        {
          "description": "string"
        }
      ]
    }
        
```

2.```GET /api/product/{id}``` -This is to fetch product details with unique id
	    
3.```GET /api/product?page=0&pageSize=20``` -This is to fetch the list of products**

4.```PUT /api/product/{id}``` -This is to update products
```json
{
  "description": "Internet/Tv/Telephone/Mobile",
  "product_packages": [
    {
      "description": "string"
    }
  ]
}
```

## Customer

1.```POST /customer**```-Registers a new customer
```json        
    {
      "first_name": "string",
      "last_name": "string",
      "gender": "FEMALE",
      "email": "string",
      "phone_number": "string"
    }
        
```

2.```GET /api/customer/{id}``` -This is to a customer with unique id

3.```GET /api/customer?gender=FEMALE&email=%40&name=ken&phoneNumber=07234566&page=0&pageSize=20``` -This fetches list of registered customers , it has optional filters**

4.```PUT /api/customer/{id}``` -This is to update customer
```json
{
  "first_name": "string",
  "last_name": "string",
  "gender": "FEMALE",
  "email": "string",
  "phone_number": "string"
}
```
7.```DELETE /api/customer/{id}``` -This is to delete a customer

## Product-Package

1.```POST /product-package**```-Create new Product-package
```json        
    {
      "product_id": 0,
      "description": "string"
    }
        
```

2.```GET /api/product-package/{id}``` -This is to fetch product package details with unique id

3.```GET /api/product-package?description=Unlimited&productName=Internet&page=0&pageSize=20``` -This fetches list of product packages registered for a client to choose from, it has optional filters**

4.```PUT /api/product-package/{id}``` -This is to update product package
```json
{
  "product_id": 0,
  "description": "string"
}
```
7.```DELETE /api/product-package/{id}``` -This is to delete product package


## Order

1.```POST /customer**```-Registers a new order
```json        
    {
      "customer_id": 1,
      "product_id": 1,
      "product_package_id": 5,
      "installation_time": "2022-09-01T19:38:30.904Z",
      "installation_address": "Kisumu, Kenya"
    }
        
```

2.```GET /api/order/{id}``` -This is to fetch an order based on order id

3.```GET /api/order?status=PENDING&customerId=2&productName=TV&packageName=FREE&page=0&pageSize=20``` -This fetches list of orders, it has optional filters. This endpoint can be used to submit orders to order fulfillment system**

4.```PUT /api/order/{id}``` -This is to update an order
```json
{
  "first_name": "string",
  "last_name": "string",
  "gender": "FEMALE",
  "email": "string",
  "phone_number": "string"
}
```
7.```DELETE /api/order/{id}``` -This is to delete an order
8.```POST /api/order/{orderId}/marker-checker/{status}``` -This is to update an order status(PENDING/APPROVED/DISBURSED) depending on certain criteria by the agent


   
## Storage
**Task 1**
1. For Storage I am using H2- database which is an in-memory database and saving to file for persistence

H2 DB UI: http://localhost:8080/h2-console   
```
jdbcUrl:jdbc:h2:file:/data/orderdb
username :sa
password :password
```
**Task 2**
1. MYSQLDB- for persistence

Find sample in the project resource. 
credentials are located in config-datastore on github, link shared on email

## Assumptions
1. **Product Object is overloaded with packages, during fetching of a Product the response comes with its associated packages. This can be changed by commenting the below line in product model entity**
```
productDto.setProductPackages(this.packages.stream().map(ProductPackage::toProductPackageDto).collect(Collectors.toList()));
```
2. **Creation of system users was beyond scope of the project hence configuring password and usernames to authenticate endpoints.**

 
3. **Orders are made by customers but product and packages are set by agents hence order creation endpoint has security for user while the rest are administrator protected**

    
## Build
Download or clone the project from the link below:

Task 1
https://github.com/ikatanyi/Order-API.git

Task 2
https://github.com/ikatanyi/Order-Approval-Api-Task-2.git

config-server
https://github.com/ikatanyi/spring-config-server.git

    mvn clean build

## Run
    mvn spring-boot:run
 *Run packaged jar*
```
    java -jar dir-path\apocalypse\target\apocalypse-0.0.1-SNAPSHOT.jar
```
## OTHER
  1. *The project contains config-repo where settings are hosted check link below*

https://github.com/ikatanyi/config-repo.git
## Licence

    Castille
