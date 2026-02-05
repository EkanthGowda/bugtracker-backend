# Bug Tracker API - Postman Testing Guide

## 📋 Quick Reference for All Endpoints

### Base URL
```
http://localhost:8080
```

---

## 🔐 AUTHENTICATION ENDPOINTS

### 1. Register User
```
POST /auth/register
Content-Type: application/json

{
  "name": "John Developer",
  "email": "john@example.com",
  "password": "123456",
  "role": "DEVELOPER"
}
```

### 2. Login (Get JWT Token)
```
POST /auth/login
Content-Type: application/json

{
  "email": "koushik@gmail.com",
  "password": "123456"
}
```
**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrb3VzaGlrQGdtYWlsLmNvbSIsImlhdCI6MTc3MDM3NzcyMSwiZXhwIjoxNzcwMTI0MDIxfQ.MDdpj0nMcpENujjGT88j6SIZ4dvjCJ_6OxV7D8-y4T0"
}
```

### 3. Get Current User
```
GET /auth/me
Authorization: Bearer {token}
```

---

## 📁 PROJECT ENDPOINTS

### 4. Create Project (MANAGER/ADMIN only)
```
POST /projects
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "E-Commerce Platform",
  "description": "Building a scalable e-commerce system"
}
```

### 5. Get All Projects
```
GET /projects
Authorization: Bearer {token}
```

### 6. Add Member to Project
```
POST /projects/{projectId}/add-member/{userId}
Authorization: Bearer {token}
```

---

## 🎫 ISSUE ENDPOINTS

### 7. Create Issue (MANAGER/ADMIN only)
```
POST /issues/project/{projectId}
Authorization: Bearer {token}
Content-Type: application/json

{
  "title": "Fix login page responsive design",
  "description": "Login page breaks on mobile devices",
  "priority": "HIGH"
}
```

### 8. Get Issues by Project
```
GET /issues/project/{projectId}
Authorization: Bearer {token}
```

### 9. Get My Issues
```
GET /issues/my
Authorization: Bearer {token}
```

### 10. Update Issue Status
```
PUT /issues/{issueId}/status?status={status}
Authorization: Bearer {token}
```

**Allowed Status Values:**
- `TODO`
- `IN_PROGRESS`
- `DONE`

---

## 💬 COMMENT ENDPOINTS ⭐ NEW IN STEP 6

### 11. Add Comment to Issue
```
POST /issues/{issueId}/comments
Authorization: Bearer {token}
Content-Type: application/json

{
  "content": "This issue is being worked on. Looks good!"
}
```

### 12. Get Comments for Issue
```
GET /issues/{issueId}/comments
Authorization: Bearer {token}
```

---

## 🧪 FULL WORKFLOW TEST

### Step 1: Login
```
POST /auth/login
{
  "email": "koushik@gmail.com",
  "password": "123456"
}
```
**Save the token from response**

### Step 2: Create Project
```
POST /projects
Authorization: Bearer {token}
{
  "name": "Test Project",
  "description": "Testing"
}
```
**Note the project ID (usually 1)**

### Step 3: Create Issue
```
POST /issues/project/1
Authorization: Bearer {token}
{
  "title": "Test Issue",
  "description": "Test description",
  "priority": "HIGH"
}
```
**Note the issue ID (usually 1)**

### Step 4: Add Comment
```
POST /issues/1/comments
Authorization: Bearer {token}
{
  "content": "This is a test comment"
}
```

### Step 5: View Comments
```
GET /issues/1/comments
Authorization: Bearer {token}
```

### Step 6: Update Issue Status
```
PUT /issues/1/status?status=IN_PROGRESS
Authorization: Bearer {token}
```

---

## 🔑 Authorization Rules

| Endpoint | ADMIN | MANAGER | DEVELOPER |
|----------|-------|---------|-----------|
| POST /projects | ✅ | ✅ | ❌ |
| GET /projects | ✅ | ✅ | ✅ |
| POST /issues/project/{id} | ✅ | ✅ | ❌ |
| GET /issues/project/{id} | ✅ | ✅ | ✅ |
| GET /issues/my | ✅ | ✅ | ✅ |
| PUT /issues/{id}/status | ✅ | ✅ | ✅ |
| POST /issues/{id}/comments | ✅ | ✅ | ✅ |
| GET /issues/{id}/comments | ✅ | ✅ | ✅ |

---

## 💡 Postman Tips

### 1. Auto-save JWT Token
In the **Tests** tab after login request:
```javascript
var jsonData = pm.response.json();
pm.environment.set("token", jsonData.token);
```

### 2. Use Environment Variables
Set up environment variable `token` and reference it as `{{token}}`

### 3. Validate Response Status
```javascript
pm.test("Status code is 200", function () {
  pm.response.to.have.status(200);
});
```

### 4. Check Response Body
```javascript
pm.test("Token exists", function () {
  pm.response.json().token;
});
```

---

## 🚀 API Documentation (Interactive)
```
http://localhost:8080/swagger-ui.html
```

Access Swagger UI for interactive API exploration!
