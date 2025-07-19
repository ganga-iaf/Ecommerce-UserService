## User Service

* User service handles new user registration, user authentication and JWT validation.
* On new user signup, User service (producer) post an event on the kafka topic. On the same topic Notification service (Consumer) listens for the event to trigger the email notification to the signed-up user email.

**API Endpoints**

## 1.`[Post]` api/user
To register a new user.
Sample request body:  
```json
{
  "firstName":"John",
  "lastName":"Doe",
  "email":"john.doe@gmail.com",
  "mobileNumber":"9999999999",
  "dateOfBirth":"1993-05-05",
  "password":"Welcome1!",
  "confirmPassword":"Welcome1!"
}
```

Sample response body:

```json
{
  "id": 123,
  "firstName": "John",
  "lastName": "Doe",
  "email": "ganga.iaf@gmail.com",
  "mobileNumber": "9999999999"
}
```

## 2. `[GET]` api/user/exists?email={email id}
To check an account exists are not with provided email before allowing the user to reset his/her account password.

Sample Response:
```json
{
  "exists": true,
  "email": "ganga.iaf@gmail.com"
}
```

## 3.`[POST]` api/user/password/reset
To reset the account password after verifying the existence of an account with provided email. 

Sample Request body:
```json

{
  "email":"ganga.iaf@gmail.com",
  "password":"Welcome1!",
  "confirmPassword":"Welcome1!"
}
```

Sample Response body:

```json
{
  "email": "ganga.iaf@gmail.com"
}
```

## 4.`[POST]` api/auth/login
To generate a JWT token after validating the username/email and password.

Sample request body:

```json
{
  "email": "john.doe@gmail.com",
  "password": "Welcome1!"
}
```

Sample response body:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyaWQiOjEyLCJlbWFpbCI6ImphbHNha2lzaG9yZTQ2NUBnbWFpbC5jb20iLCJpYXQiOjE3NTI0NzI0MDksImV4cCI6MTc1MjUwODQwOSwic3ViIjoiMTIifQ.eAgFeQat0oclB001Si6Bx8jEWngvwEGWYhBJ1SC9A5g"
}
```

## 5.`[POST]` api/auth/validate
To validate the JWT token 

Request Header(s):  
Authorization : Bearer [JWT token]

Sample response:
* Resposne would be true if JWT token validation is successful otherwise it would be false.  


### Database table(s)

* users
  * id
  * first_name
  * last_name
  * email
  * mobile
  * password
  * date_of_birth
  * is_suspended
  * created_at
  * updated_at

### Technologies
* Java17
* Spring Boot
* My SQL DB

### Maven dependencies
* lombok 
* mysql-connector-j 
* spring-boot-starter-data-jpa 
* jjwt-api 
* jjwt-impl 
* jjwt-jackson
* spring-kafka




