## User Service

* User service handles new user registration, user authentication and JWT validation. 

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

## 2.`[POST]` api/auth/login
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

## 3.`[POST]` api/auth/validate
To validate the JWT token 

Request Header(s):  
Authorization : Bearer [JWT token]

Sample response:
* Resposne would be true if JWT token validation is successful otherwise it would be false.  
