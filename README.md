# Ecommerce-web-application

---

## Description
This is a full-stack e-commerce application built using Spring Boot for the backend and React.js for the frontend. The application allows users to browse products, add them to the shopping cart, and complete purchases.

---

## Table of Contents
- [Features](#features)
- [Dependencies](#dependencies)
- [Database Design](#Database-Design)
- [Installation](#installation)
- [Usage](#usage)
- [API Documentation](#API-Documentation)
- [Deployment](#deployment)

---

## Features
- User authentication and authorization
- Product listing with filter options
- Shopping cart management
- Order placement and checkout process
- Admin panel for managing products and orders

---

## Dependencies
💡 **TODO:** 
- [ ] add the dependencies

---

## Database Design
💡 **TODO:** 
- [ ] add database schema diagram
- [ ] update the below data to reflect the up-to-date datase design in the application

<details>
  <summary>
    the entity classes of the database this application are: User, Address, Product, Review, Rating, Category, QtyPerSize, Cart, CartItem, Order, OrderItem, Payment.

    the entity classes that represent many tables (self-referencing relationships): Category ("category" and "sub_category" tables).
    the many-to-many relationship tables are: no many-to-many relationships in this project yet.
    the supporting/"weak"-entity tables are: address, qty_per_size, image.
    the enumerated helper classes: Role, OrderStatus, ProductSize, RatingValue, PaymentMethod.
    the record classes: Image.
  </summary>

  - relationships:
    - user table:
      - has one-to-many relationship with cart table
      - has one-to-many relationship with order table
      - has one-to-many relationship with payment table
      - has one-to-many relationship with address table
      - has one-to-many relationship with review table
      - has one-to-many relationship with rating table
    - address table:
      - has many-to-one relationship with user table
    - product table:
      - has one-to-one relationship with cart_item table
      - has one-to-one relationship with order_item table
      - has one-to-many relationship with review table
      - has one-to-many relationship with rating table
      - has many-to-one relationship with category table
      - has one-to-many (or can be one-to-one, that is when using alternative design for this table) relationship with qty_per_size table
    - review table:
      - has many-to-one relationship with product table
      - has many-to-one relationship with user table
      - has one-to-many relationship with rating table
    - rating table:
      - has many-to-one relationship with product table
      - has many-to-one relationship with user table
      - has many-to-one relationship with review table
    - category table:
      - has one-to-many relationship with product table
      - has one-to-many relationship with sub_category table
    - sub_category table:
      - has many-to-one relationship with category table
    - qty_per_size table:
      - has many-to-one (or can be one-to-one, that is when using alternative design for this table) relationship with product table
    - cart table:
      - has many-to-one relationship with user table
      - has one-to-many relationship with cart_item table
    - cart_item table:
      - has one-to-one relationship with product table
      - has many-to-one relationship with cart table
    - order table:
      - has many-to-one relationship with user table
      - has one-to-many relationship with order_item table
    - order_item:
      - has one-to-one relationship with product table
      - has many-to-one relationship with order table
    - Payment:
      - has one-to-one relationship with order table

  - association of the database tables with their functionsalities/features in the application:
    - product table:
      - to display store products (along with filteration)
    - user table:
      - for authentication
      - for payment process
    - address table:
      - for payment process
    - review table:
      - for products reviews
    - rating table:
      - for products ratings
    - category:
      - for product filteration
    - qty_per_size table:
      - to check the stock status of the product
    - cart and cart_item tables:
      - for checkout process
    - order and order_item tables:
      - for ordering process
    - Payment:
      - to complete user's payment process
</details>

---

## application structure:


### folder structure
💡 **TODO:** 
- [ ] add this section

### exception handling
💡 **TODO:** 
- [ ] add this section

---

## Installation


### Prerequisites
- Java 11+
- MySQL
- Maven
- Git


### Backend Setup
- clone the repository:

```bash
git clone git@github.com:ali8137/Ecommerce-web-application-backend.git
cd Ecommerce-web-application-backend
```

- configure environment variables:

```YAML
spring:
  datasource:
    username: ${MYSQL_DB_USERNAME}
    password: ${MYSQL_DB_PASSWORD}
stripe:
  api:
    key: ${STRIPE_SECRET_KEY}
```

and the environment variables: STRIPE_WEBHOOK_SECRET and JWT_SECRET_KEY

- install dependencies:

```bash
mvn clean install
```

or using "ctrl + shift + o" in Intellij IDEA

- run the application:

```bash
mvn spring-boot:run
```


### Database Setup
- create the database:

```bash
mysql -u root -p -e "CREATE DATABASE ecommerce;"
```

or using MySQL workbench UI

💡 **TODO:** 
- [ ] for the current application features, initialize/populate the database with the initial necassary data for the application like products, categories, ...

---

## Usage
- once the backend is running, you can access the app at http://localhost:8088

💡 **TODO:** 
- [ ] continue/fill the below section
### API Endpoints
- `GET /api/...` - ...
- `POST /api/...` - ...
- `PUT /api/...` - ...
- 

### Authentication
To access protected routes, you need to authenticate using a JWT token. 
- Log in using `POST /api/auth/register` with your email and password.
- After successful login, use the returned JWT token in the `Authorization` header of your subsequent requests.

### Example Request
request: 
**POST** `http://localhost:8088/api/login`
- **Headers**: `Content-Type: application/json`
- **Body**:
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

response:
{
  "token": "your-jwt-token-here"
}

---

## API Documentation
- API Base URL: http://localhost:8088/api

💡 **TODO:** 
- [ ] add postman tests
- [ ] add Swagger API documentation

---

## Deployment

💡 **TODO:** 
- [ ] update this section
