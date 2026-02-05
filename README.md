# 🐛 Bug Tracker / Issue Tracker (Jira-like)

A full-stack Jira-style Issue Tracking System built using **Spring Boot** and **React** with **JWT-based authentication** and **Kanban workflow**.

---

## 🚀 Features

- ✅ User Authentication & Authorization (JWT)
- ✅ Role-Based Access (Admin, Manager, Developer)
- ✅ Project Management
- ✅ Issue / Bug Tracking
- ✅ Kanban Board (To Do → In Progress → Done)
- ✅ Drag & Drop Workflow
- ✅ Issue Assignment
- ✅ Comments & Collaboration
- ✅ Filters & Search
- ✅ Secure REST APIs
- ✅ Swagger API Documentation

---

## 🧰 Tech Stack

### Backend
- **Java 17**
- **Spring Boot 3.5.10**
- **Spring Security + JWT (HS256)**
- **Hibernate / JPA**
- **PostgreSQL 16**
- **Maven**

### Frontend
- **React (Vite 7.3.1)**
- **Tailwind CSS 3.4.17**
- **Axios**
- **React Router**
- **HTML5 Drag & Drop API**

---

## 🏗️ Architecture

```
┌──────────────────┐
│   React UI       │ (Vite, Tailwind, Components)
│  (localhost:5173)│
└────────┬─────────┘
         │
         │ Axios + JWT Bearer Token
         ▼
┌──────────────────┐
│ Spring Boot REST │ (Controllers, Services)
│  (localhost:8080)│
└────────┬─────────┘
         │
         │ JPA / Hibernate
         ▼
┌──────────────────┐
│  PostgreSQL DB   │ (Issues, Users, Comments)
│   (Port: 5432)   │
└──────────────────┘
```

---

## 🔐 Authentication Flow

1. **User Login** → POST `/auth/login` with email & password
2. **JWT Generated** → Server creates HS256 signed token (24-hour expiry)
3. **Token Storage** → Client stores in `localStorage`
4. **Token Injection** → Axios interceptor auto-adds `Authorization: Bearer <token>`
5. **Backend Validation** → `JwtAuthenticationFilter` validates on every request
6. **Protected Routes** → React ProtectedRoute guards UI, redirects to login if invalid

---

## 📦 Core Modules

### Auth Module
- LoginRequest / AuthResponse DTOs
- JWT token generation & validation
- Custom UserDetailsService
- SecurityConfig with CORS

### Project Module
- Create & manage projects
- Project ownership

### Issue Module
- Create, read, update issues
- Status: TODO → IN_PROGRESS → DONE
- Priority: LOW, MEDIUM, HIGH
- Drag & drop status updates

### Comment System
- Issue-level threading
- Real-time collaboration
- Secured endpoints

### Kanban Workflow
- 3-column layout (UI)
- Drag & drop with instant persistence
- Filters by status, priority, and search

---

## ▶️ How to Run

### Prerequisites
- Java 17+
- PostgreSQL 16+
- Node.js 18+
- Maven 3.8+

### Backend Setup
```bash
cd bugtracker
mvn clean install
mvn spring-boot:run
```
Backend runs on: **http://localhost:8080**

### Frontend Setup
```bash
cd bugtracker-ui
npm install
npm run dev
```
Frontend runs on: **http://localhost:5173**

### Seeding
On first startup, the backend automatically seeds:
- 1 default user: `koushik@gmail.com` / `123456`
- 1 default project
- 3 default issues (TODO, IN_PROGRESS, DONE)

---

## 📄 API Endpoints

### Authentication
- `POST /auth/login` - Login & get JWT
- `POST /auth/register` - Register new user

### Projects
- `GET /projects` - List all projects
- `POST /projects` - Create project
- `GET /projects/{id}` - Get project details

### Issues
- `GET /issues/project/{projectId}` - Get project issues
- `POST /issues/project/{projectId}` - Create issue
- `GET /issues/{id}` - Get issue details
- `PUT /issues/{id}` - Update issue
- `PUT /issues/{id}/status?status=...` - Change issue status
- `DELETE /issues/{id}` - Delete issue

### Comments
- `GET /issues/{issueId}/comments` - Get issue comments
- `POST /issues/{issueId}/comments` - Add comment

### Swagger Documentation
- **http://localhost:8080/swagger-ui.html**

---

## 🔑 Default Credentials

```
Email: koushik@gmail.com
Password: 123456
```

---

## 🎯 Project Structure

```
bugtracker/
├── src/main/java/com/bugtracker/
│   ├── BugtrackerApplication.java
│   ├── config/
│   │   └── AppConfig.java (Seed data)
│   ├── controller/ (REST endpoints)
│   │   ├── AuthController.java
│   │   ├── ProjectController.java
│   │   ├── IssueController.java
│   │   └── CommentController.java
│   ├── model/ (JPA entities)
│   │   ├── User.java
│   │   ├── Project.java
│   │   ├── Issue.java
│   │   ├── Comment.java
│   │   ├── Role.java
│   │   ├── IssueStatus.java
│   │   └── IssuePriority.java
│   ├── repository/ (Data access)
│   │   ├── UserRepository.java
│   │   ├── ProjectRepository.java
│   │   ├── IssueRepository.java
│   │   └── CommentRepository.java
│   ├── service/ (Business logic)
│   │   ├── AuthService.java
│   │   ├── ProjectService.java
│   │   ├── IssueService.java
│   │   └── CommentService.java
│   └── security/ (JWT & auth)
│       ├── JwtUtil.java
│       ├── JwtAuthenticationFilter.java
│       ├── CustomUserDetailsService.java
│       ├── SecurityConfig.java
│       └── dto/
│           ├── LoginRequest.java
│           └── AuthResponse.java
├── pom.xml
└── README.md

bugtracker-ui/
├── src/
│   ├── api/
│   │   └── axios.js (HTTP client with JWT interceptor)
│   ├── auth/
│   │   └── ProtectedRoute.jsx (Route guard)
│   ├── components/
│   │   ├── Comments.jsx
│   │   └── CreateIssueModal.jsx
│   ├── pages/
│   │   ├── Login.jsx
│   │   └── Kanban.jsx (Main Kanban board with filters)
│   ├── utils/
│   │   └── auth.js
│   ├── App.jsx (Router)
│   ├── main.jsx
│   ├── index.css
│   ├── tailwind.config.js
│   ├── postcss.config.js
│   └── vite.config.js
├── package.json
└── index.html
```

---

## 🎬 Demo Flow (For Interviews / Viva)

**When asked "Explain your project", follow this flow:**

1. **Login Page** → Show authentication
   - Email: koushik@gmail.com
   - Password: 123456
   - Click "Login"
   - JWT stored in localStorage

2. **Dashboard** → Explain protection
   - "This route is protected; without valid JWT, users are redirected to login"

3. **Kanban Board** → Show project structure
   - "The board fetches issues from the `/issues/project/{projectId}` endpoint"
   - "I have 3 columns: TODO, IN_PROGRESS, and DONE"

4. **Create Issue** → Show form validation
   - Click "+ Create Issue"
   - Fill in: Title, Description, Priority, Status
   - Click "Create"
   - "The issue is created via POST and appears on the board"

5. **Drag & Drop** → Show real-time sync
   - Drag an issue from TODO to IN_PROGRESS
   - "The status updates instantly in the UI (optimistic update)"
   - "A PUT request is sent to `/issues/{issueId}/status`"
   - "If the API fails, the UI rolls back automatically"

6. **Comments** → Show collaboration
   - Click an issue card to expand
   - Scroll to comments section
   - Type a comment and press Enter
   - "Comments are fetched from `/issues/{issueId}/comments`"
   - "Each comment is associated with a user and timestamp"

7. **Filters & Search** → Show advanced features
   - Filter by Status: "In Progress"
   - Filter by Priority: "High"
   - Search by title
   - "All filters work together, updated instantly on the client side"

8. **Logout** → Complete the flow
   - Click "Logout"
   - JWT is removed
   - Redirected to login

**Key talking points during demo:**

> "The Kanban board updates status in real time and persists changes to the database."

> "JWT authentication ensures only authorized users can access issues and comments."

> "The frontend uses Axios interceptor to automatically inject the token in every API call."

> "Role-based authorization on the backend prevents unauthorized operations."

> "Optimistic UI updates provide instant feedback, while errors trigger automatic rollback."

---

## 💡 Key Implementation Highlights

### 1. JWT Security
- Generated on login using HS256 algorithm
- 24-hour expiration
- Auto-injected in all requests via Axios interceptor
- Validated by custom JwtAuthenticationFilter

### 2. Kanban Workflow
- Native HTML5 drag-and-drop API
- Optimistic UI updates (instant visual feedback)
- Automatic rollback on API failure
- Filters integrated for real-time searching

### 3. Real-time Collaboration
- Comments tied to issues
- User-based access control
- Instant comment display
- Secure endpoints with role validation

### 4. Production-Ready Practices
- Exception handling in all services
- CORS properly configured
- CSRF disabled for stateless JWT auth
- Request/response DTOs for clean APIs

---

## 🌟 Resume Bullets

### Short (1-liner)
> Built a Jira-like issue tracking system using Spring Boot and React with JWT authentication and Kanban workflow.

### Medium (Recommended)
> Developed a full-stack Jira-style bug tracker using Spring Boot and React, implementing JWT security, role-based access, Kanban drag-and-drop workflow, and real-time issue collaboration features.

### Strong (Top-tier)
> Designed and implemented a full-stack Jira-like Issue Tracking System using Java Spring Boot 3 and React with Vite, featuring JWT-based authentication, role-based authorization, Kanban workflow with native drag-and-drop, issue assignment, threaded comments, advanced filters, and secure REST APIs backed by PostgreSQL, deployed with production-grade error handling and CORS configuration.

---

## 🎓 Interview Q&A Prep

**Q: How do you handle JWT expiration?**
A: "The token has a 24-hour expiry. In a production system, I'd implement refresh tokens and a background task to auto-refresh before expiry."

**Q: What happens if someone modifies the JWT token?**
A: "The JwtUtil.extractEmail() validates the signature using the server's secret. Any tampering invalidates the token, and the request is rejected."

**Q: How did you implement drag-and-drop?**
A: "I used the native HTML5 Drag and Drop API with dataTransfer. The frontend optimistically updates the UI, then sends a PUT request to update the database."

**Q: What if the API fails during drag-and-drop?**
A: "The frontend catches the error and calls fetchIssues() to rollback the UI to the server state."

**Q: How do you prevent unauthorized access?**
A: "Using Spring Security @PreAuthorize annotations and custom UserDetailsService. Each request is validated by JwtAuthenticationFilter."

**Q: Why use JWT instead of sessions?**
A: "JWT is stateless, scalable across multiple servers, and ideal for REST APIs. No server-side session storage needed."

---

## 📋 Checklist - Project Complete ✅

- ✅ Backend: Spring Boot REST API with JWT security
- ✅ Frontend: React SPA with protected routes
- ✅ Database: PostgreSQL with Hibernate JPA
- ✅ Authentication: JWT with 24-hour expiry
- ✅ Authorization: Role-based access control
- ✅ Kanban Board: 3-column layout with drag-and-drop
- ✅ Issue Management: Full CRUD operations
- ✅ Comments: Issue-level collaboration
- ✅ Filters & Search: Real-time filtering
- ✅ Error Handling: Graceful failures with rollback
- ✅ CORS: Configured for frontend integration
- ✅ Swagger Docs: Auto-generated API documentation
- ✅ Seed Data: Default user & sample data on startup
- ✅ Resume-Ready: Professional README and documentation

---

## 🚀 Next Steps (Optional)

If you want to enhance this project further:

- **Deployment:** Docker containers, Render.com (backend), Vercel (frontend)
- **Real-time Updates:** WebSocket for instant push notifications
- **Advanced Features:** Issue relationships, burndown charts, sprint planning
- **Analytics:** Issue resolution time, team velocity metrics
- **Mobile:** React Native version for iOS/Android

---

## 📞 Support

For questions or issues during setup, refer to:
- Backend logs: `mvn spring-boot:run` (check for errors)
- Frontend console: F12 → Console tab (check API calls)
- Swagger Docs: http://localhost:8080/swagger-ui.html

---

## 👨‍💻 Author

**Koushik Gowda**

---

## 📄 License

This project is open source and available for educational purposes.

---

**🎉 Congratulations! You now have an enterprise-level Jira-like Issue Tracking System ready for submission, interviews, and portfolio!**
