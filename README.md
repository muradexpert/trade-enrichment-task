# Trade Enrichment Project

This project is a simple Spring Boot application that processes trade data from CSV files. It includes services for enriching trade data by mapping products and their respective details. The application also has corresponding unit tests to ensure correctness.

## Table of Contents

- [Requirements](#requirements)
- [Setup](#setup)
- [Running the Application](#running-the-application)
- [Testing](#testing)
- [Project Structure](#project-structure)
- [Additional Notes](#additional-notes)

## Requirements

Before you begin, ensure you have met the following requirements:

- **Java 17** or later
- **Maven 3.6.0** or later
- **Git** (if you are cloning the repository)

## Setup

1. **Clone the repository** (if you haven't already):

    ```bash
    git clone https://github.com/muradexpert/trade-enrichment-task.git
    cd tradeenrichment
    ```

2. **Build the project**:

    You can build the project using Maven. Navigate to the root directory of the project and run:

    ```bash
    mvn clean install
    ```

    This command will compile the source code, run the tests, and package the application into a JAR file.

3. **Load Product Data**:

    The application expects a `product.csv` file to be present in the `src/main/resources/` directory. Ensure that the CSV file is structured as follows:

    ```csv
    product_id,product_name
    1,Product A
    2,Product B
    3,Product C
    ```

## Running the Application

After the build completes successfully, you can run the application using the following command:

```bash
mvn spring-boot:run


```
Alternatively, you can run the JAR file generated in the target/ directory:

```bash
java -jar target/tradeenrichment-0.0.1-SNAPSHOT.jar

