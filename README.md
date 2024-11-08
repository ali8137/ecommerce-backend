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

---

## Database Design
<details>
  <summary>
    the tables of the database this application are: Customer, Address, Product, Review, Rating, Category, Inventory, Cart, CartItem, Order, OrderItem, Payment.
    the many-to-many relationship tables are: Review, Rating, "Cart", "Order".
    the "weak" entities are: Address, Payment.
  </summary>

  - relationships:
    - Customer:
      - has one-to-many relationship with Cart
      - has one-to-many relationship with Order
      - has one-to-many relationship with Payment
      - has one-to-many relationship with Address
    - Address:
      - has many-to-one relationship with Customer
    - Product:
      - has many-to-many relationship with Cart
      - has many-to-many relationship with Order
      - has many-to-many relationship with Review
      - has many-to-many relationship with Rating
      - has many-to-one relationship with Category
      - has one-to-many (or can be one-to-one) relationship with Inventory
    - Review:
      - has many-to-one relationship with Product
      - has many-to-one relationship with Customer
    - Rating:
      - has many-to-one relationship with Product
      - has many-to-one relationship with Customer
    - Category:
      - has one-to-many relationship with Product
    - Inventory:
      - has many-to-one (or can be one-to-one) relationship with Product
    - Cart:
      - has many-to-one relationship with Customer
      - has one-to-many relationship with CartItem
    - CartItem:
      - has one-to-one relationship with Product
      - has many-to-one relationship with Cart
    - Order:
      - has many-to-one relationship with Customer
      - has one-to-many relationship with OrderItem
    - OrderItem:
      - has one-to-one relationship with Product
      - has many-to-one relationship with Order
    - Payment:
      - has many-to-one relationship with Customer

  - association of the database tables with their functionsalities/features in the application:
    - Product:
      - to display store products (along with filteration)
    - Customer:
      - for authentication
    - Address:
      - for payment process
    - Review:
      - for products reviews
    - Rating:
      - for products ratings
    - Category:
      - for product filteration
    - Inventory:
      - to check the stock status of the product
    - Cart and CartItem:
      - for checkout process
    - Order and OrderItem:
      - for ordering process
    - Payment:
      - to complete customer's payment process
</details>

---

## Installation


### Prerequisites
- Java 11+
- MySQL


### Backend Setup


### Database Setup

---

## Usage

---

## API Documentation

---

## Deployment
