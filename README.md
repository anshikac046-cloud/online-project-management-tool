CodeCrew – Online Project Management Tool

📌 About the Project

The Online Project Management Tool is a Java-based desktop application developed by Team CodeCrew to simplify project planning, task allocation, and progress tracking. It provides a centralized platform where administrators, project managers, and team members can manage project-related activities efficiently.

The application uses a graphical user interface (GUI) to make project management easier and more organized.

🎯 Objectives

- To simplify project and task management.
- To provide role-based access for different users.
- To improve team collaboration and task organization.
- To monitor project progress efficiently.
- To store and manage project information using a database.

✨ Key Features

- User Login: Provides a login interface for accessing the application.
- Admin Dashboard: Allows administrators to access user and project management features.
- User Management: Supports adding and managing users.
- Project Management: Provides an interface to manage projects.
- Task Management: Helps organize and manage project tasks.
- Project Progress: Provides a dedicated interface for monitoring project progress.
- Reports: Includes a reports interface for viewing project-related information.
- Database Connectivity: Uses JDBC to connect the Java application to MySQL.

👥 User Roles

1. Administrator

Manages users and oversees project and task management features.

2. Project Manager

Manages projects, handles tasks, and monitors project progress.

3. Team Member

Participates in project activities and works on assigned tasks, according to the available functionality.

🛠️ Technologies Used

- Programming Language: Java
- GUI Framework: Java Swing
- Database: MySQL
- Database Connectivity: JDBC
- Build Tool: Maven
- Development Environment: Visual Studio Code
- Version Control: Git and GitHub

📂 Project Structure

online-project-management-tool/
├── src/
│   └── main/
│       └── java/
├── database.sql
├── pom.xml
├── .gitignore
└── README.md

⚙️ Installation and Setup

Prerequisites

- Java Development Kit (JDK) compatible with the project
- MySQL Server
- Visual Studio Code or another Java IDE
- Maven

Step 1: Clone the Repository

git clone https://github.com/anshikac046-cloud/online-project-management-tool.git
cd online-project-management-tool

Step 2: Set Up the Database

1. Start MySQL Server.
2. Open MySQL Workbench or another MySQL client.
3. Execute the SQL statements in "database.sql".
4. Verify that the required database and tables have been created.

Step 3: Configure Database Connectivity

Configure the database URL, username, and password for your local MySQL installation. Keep credentials private and never upload passwords to GitHub.

Step 4: Build the Project

Run the following command in the project directory:

mvn clean package

Step 5: Run the Application

Open the project in your Java IDE, locate the application's main class, and run it.

👩‍💻 Team CodeCrew

- Anshika Chaudhary
- Riya Singh
- Mohammad Yusuf
- Anmol Singh Rajput

🚀 Future Scope

- Add real-time notifications.
- Improve project analytics and reporting.
- Enhance task assignment and progress tracking.
- Introduce additional collaboration features.
- Improve security and user experience.

📌 Project Status

Under Development

🔗 GitHub Repository

https://github.com/anshikac046-cloud/online-project-management-tool

---

Developed by Team CodeCrew
