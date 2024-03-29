# Expense Tracker
##### Table of Contents  
- [Introduction](#introduction)
- [Running the Server](#running-the-server)
- [Running the Client](#running-the-client)
## Introduction
This app allows you to track your daily expenses. You can create multiple wallets along with as many categories and subcategories as you want. Once you create your categories and your wallets, you can insert your expenses and view all of them in a dedicated section.

## :whale: Running the Server

Move into resources folder within your terminal and run

```docker compose up mysql-db```

Now, you can build the server application with

```mvn clean install```

In order to run the server use

```java -jar target/expense-tracker-0.0.1-SNAPSHOT.jar ```


## :rocket: Running the Client

First run

```npm install```

And then

```npm run build```
