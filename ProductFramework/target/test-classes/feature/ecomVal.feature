Feature: Validating the e-commerce website for adding and ordering product

Scenario: Login to the ecommerce website with loginAPI
Given Payload of LoginAPI
When Call the "LoginAPI" with "post" http method
Then the api call is success with status code 200
And "message" in the response body is "Login Successfully"
And Store the value of "userId" and  "token"


Scenario Outline: Add product to website with addProductAPI 
Given AddProductAPI payload with "<image path>"
When Call the "AddProductAPI" with "post" http method
Then the api call is success with status code 201
And "message" in the response body is "Product Added Successfully"
And Store the value of "productId"

Examples:
|image path											  |
|/Users/mac/Downloads/images.jpeg |
#|/Users/mac/Downloads/pngtree.jpeg |

Scenario: Order the added product with CreateProductAPI
Given CreateProdAPI payload
When Call the "CreateProductAPI" with "post" http method
Then the api call is success with status code 201
And "message" in the response body is "Order Placed Successfully" 
And Store the value of "orderId"