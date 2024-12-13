# Loans Application
## Installation
You need Docker to run application or an IDE with runner. In order to run the application with Docker in your local environment, run the commands below

```
git clone git@github.com:gokhanustuner/loans.git
cd loans
sh ./imagebuild.sh
```

After running `imagebuild.sh`, you will have a running application on `http://localhost:8080`. A sample user will be created on Customer table, while the application is initializing.

If you click the Postman Collection link, you will reach to a public collection consists of four endpoints<br>
[Loans Postman Collection](https://web.postman.co/workspace/My-Public-Workspace~27e7d6f6-fc4c-4d85-9930-bebe882c069f/collection/134786-3622fbf9-e213-4c63-9442-91315439074c)

## Use Cases
The system need Basic Authentication with the credentials below:

```
username: admin
password: password
```

NOTE: Only admin user exists in the system.

### Create Loan
The use case which has to be run first, to enable the other use cases run. When you move to the <ins>Loans Postman Collection</ins>, you must be running this use case first. 

In Postman, the <ins>customerId</ins> necessary to run this use case has been set in the Environment.

An example request and response are below

```
POST /api/customers/{{customerId}}/loans

Content-Type: application/json
Authorization: Basic YWRtaW46cGFzc3dvcmQ=
{
    "amount": "45000",
    "interestRate": 0.36,
    "numberOfInstallments": 12
}
```

```
Status: 200
Content-Type: application/json
{
    "id": "6f99a6ee-56e3-4b01-82d0-44a7c453649c",
    "amount": 61199.9999999999955000,
    "numberOfInstallments": 12,
    "isPaid": false
}
```

### List Loans
After creating the first Loan for our pre-initialized Customer, you can list the Loans it has

```
GET /api/customers/{{customerId}}/loans?page=1
```

```
Status: 200
Content-Type: application/json
[
    {
        "id": "cec4b965-4bb0-4630-a77a-4d2d5cce4da4",
        "amount": 61200.00,
        "numberOfInstallments": 12,
        "isPaid": false
    }
]
```
In every page, 10 Loans of Customer are listed from earlier to later.

### List Installments
To list the Installments specific to a Loan, call the endpoint below

```
GET /api/customers/{{customerId}}/loans/{{loanId}}/installments
```

```
Status: 200
Content-Type: application/json
[
    {
        "id": "f48a7a35-a3b8-44b4-af42-860700d005fd",
        "amount": 5100.00,
        "paidAmount": 0.00,
        "dueDate": "2025-01-01",
        "isPaid": false,
        "paymentDate": null
    },
    {
        "id": "d340fc54-4a77-44d1-9f05-655b997badaa",
        "amount": 5100.00,
        "paidAmount": 0.00,
        "dueDate": "2025-02-01",
        "isPaid": false,
        "paymentDate": null
    },
    {
        "id": "b8b8763a-e933-47e8-b38f-c04507e2d9ef",
        "amount": 5100.00,
        "paidAmount": 0.00,
        "dueDate": "2025-03-01",
        "isPaid": false,
        "paymentDate": null
    },
    ...
]
```

There is not pagination for Installments listing, as they have specific counts per Loan.

### Pay Loan
To pay the Installments of a Loan in the extent of the amount you post, make the calls below

```
POST /api/payment/{{loanId}}
Content-Type: application/json
Authorization: Basic YWRtaW46cGFzc3dvcmQ=

{
    "amount": "6000.00"
}
```

```
Status: 200
Content-Type: application/json
{
    "numberOfInstallmentsPaid": 1,
    "paidAmount": 4702.20000,
    "isPaid": false
}
```

The installments will be paid according to their discounted or penalized amount. The amount that you send for the payment of installments will be decreased accordingly. 

## Design Considerations on the Codebase
DDD was considered as the main approach for the development of codebase. There are entities with rich and encapsulated behavior, value objects representing the values that are spoken as domain terms, and separated layers 
as domain, application, and infrastructure.
