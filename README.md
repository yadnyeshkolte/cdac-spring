# 🌱 Spring Framework Complete Notes
## CDAC Spring Learning Guide & Interview Preparation

---

# Table of Contents
1. [Introduction to Spring Framework](#1-introduction-to-spring-framework)
2. [Spring Core Concepts](#2-spring-core-concepts)
3. [Dependency Injection (DI)](#3-dependency-injection-di)
4. [Spring Bean Lifecycle](#4-spring-bean-lifecycle)
5. [Spring MVC Architecture](#5-spring-mvc-architecture)
6. [Spring Boot](#6-spring-boot)
7. [Spring Data JPA](#7-spring-data-jpa)
8. [Spring JDBC](#8-spring-jdbc)
9. [REST API Development](#9-rest-api-development)
10. [Spring Security Basics](#10-spring-security-basics)
11. [Session Management](#11-session-management)
12. [Spring Annotations Reference](#12-spring-annotations-reference)
13. [Common Interview Questions](#13-common-interview-questions)
14. [Troubleshooting Guide](#14-troubleshooting-guide)
15. [Best Practices](#15-best-practices)

---

# 1. Introduction to Spring Framework

## 1.1 What is Spring? 
Spring is a comprehensive, lightweight, open-source framework for building Java enterprise applications. It provides infrastructure support for developing Java applications, allowing developers to focus on business logic. 

## 1.2 Key Features of Spring
- **Lightweight**: Minimal overhead in terms of size and transparency
- **Inversion of Control (IoC)**: Objects define dependencies via constructor arguments, factory method arguments, or properties
- **Aspect-Oriented Programming (AOP)**: Separates cross-cutting concerns from business logic
- **Container**: Manages object lifecycle and configuration
- **MVC Framework**: Well-designed web framework for building web applications
- **Transaction Management**: Consistent programming model for different transaction APIs
- **JDBC Exception Handling**: Simplifies error handling in database operations

## 1.3 Spring Framework Modules

```
┌─────────────────────────────────────────────────────────────┐
│                    SPRING FRAMEWORK                         │
├─────────────────────────────────────────────────────────────┤
│  Data Access/Integration  │  Web           │  AOP          │
│  ├── JDBC                 │  ├── Web       │  ├── AOP      │
│  ├── ORM                  │  ├── Servlet   │  └── Aspects  │
│  ├── OXM                  │  ├── WebSocket │               │
│  ├── JMS                  │  └── Portlet   │               │
│  └── Transactions         │                │               │
├─────────────────────────────────────────────────────────────┤
│                     Core Container                          │
│  ├── Beans    ├── Core    ├── Context    └── SpEL         │
├─────────────────────────────────────────────────────────────┤
│  Test                                                       │
└─────────────────────────────────────────────────────────────┘
```

## 1.4 Spring vs Spring Boot

| Feature | Spring Framework | Spring Boot |
|---------|------------------|-------------|
| Configuration | Manual XML/Java Config | Auto-configuration |
| Server | External server required | Embedded server |
| Dependencies | Manual dependency management | Starter dependencies |
| Setup Time | More time required | Quick setup |
| Production Ready | Needs additional setup | Built-in features |

---

# 2. Spring Core Concepts

## 2.1 Inversion of Control (IoC)
IoC is a design principle where the control of object creation and lifecycle is transferred from the application code to a container or framework. 

### Traditional Approach (Without IoC):
```java
public class StudentService {
    // Tight coupling - StudentService creates its own dependency
    private StudentRepository repository = new StudentRepository();
    
    public void saveStudent(Student student) {
        repository. save(student);
    }
}
```

### IoC Approach (With Spring):
```java
@Service
public class StudentService {
    // Loose coupling - Spring injects the dependency
    @Autowired
    private StudentRepository repository;
    
    public void saveStudent(Student student) {
        repository.save(student);
    }
}
```

## 2.2 Spring Container
The Spring container is responsible for:
- Creating objects
- Wiring objects together
- Configuring objects
- Managing object lifecycle

### Types of Spring Containers:
1. **BeanFactory**: Basic container with lazy initialization
2. **ApplicationContext**: Advanced container with eager initialization

```java
// BeanFactory Example
BeanFactory factory = new XmlBeanFactory(new FileSystemResource("beans. xml"));
Student student = (Student) factory.getBean("student");

// ApplicationContext Example (Recommended)
ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
Student student = context.getBean("student", Student.class);

// Annotation-based Configuration
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
```

## 2.3 Spring Bean
A bean is an object that is instantiated, assembled, and managed by the Spring IoC container.

### Bean Definition Methods: 

**1. XML Configuration:**
```xml
<beans xmlns="http://www.springframework.org/schema/beans">
    <bean id="student" class="com. cdac.Student">
        <property name="name" value="John"/>
        <property name="course" value="Java"/>
    </bean>
</beans>
```

**2. Java Configuration:**
```java
@Configuration
public class AppConfig {
    
    @Bean
    public Student student() {
        Student student = new Student();
        student.setName("John");
        student.setCourse("Java");
        return student;
    }
    
    @Bean
    public StudentService studentService() {
        return new StudentService(student());
    }
}
```

**3. Annotation-based:**
```java
@Component
public class Student {
    private String name;
    private String course;
    // getters and setters
}
```

## 2.4 Bean Scopes

| Scope | Description | Usage |
|-------|-------------|-------|
| **singleton** | Single instance per Spring container (default) | Stateless beans |
| **prototype** | New instance every time requested | Stateful beans |
| **request** | New instance per HTTP request | Web applications |
| **session** | New instance per HTTP session | User sessions |
| **application** | Single instance per ServletContext | Application-wide |
| **websocket** | New instance per WebSocket session | WebSocket apps |

```java
@Component
@Scope("prototype")
public class PrototypeBean {
    // New instance created for each request
}

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionScopedBean {
    // One instance per user session
}
```

---

# 3. Dependency Injection (DI)

## 3.1 What is Dependency Injection?
DI is a design pattern that implements IoC.  Dependencies are "injected" into objects rather than created by them.

## 3.2 Types of Dependency Injection

### 3.2.1 Constructor Injection (Recommended)
```java
@Service
public class StudentService {
    
    private final StudentRepository repository;
    private final EmailService emailService;
    
    // Constructor Injection - Recommended approach
    @Autowired
    public StudentService(StudentRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }
    
    public void registerStudent(Student student) {
        repository. save(student);
        emailService.sendWelcomeEmail(student. getEmail());
    }
}
```

**Advantages of Constructor Injection:**
- Ensures required dependencies are not null
- Supports immutability (final fields)
- Makes testing easier
- Clear declaration of dependencies

### 3.2.2 Setter Injection
```java
@Service
public class StudentService {
    
    private StudentRepository repository;
    
    // Setter Injection
    @Autowired
    public void setRepository(StudentRepository repository) {
        this.repository = repository;
    }
    
    public void saveStudent(Student student) {
        repository.save(student);
    }
}
```

**When to use Setter Injection:**
- Optional dependencies
- Circular dependencies (though best avoided)
- Re-configurable dependencies

### 3.2.3 Field Injection (Not Recommended for Production)
```java
@Service
public class StudentService {
    
    // Field Injection - Convenient but not recommended
    @Autowired
    private StudentRepository repository;
    
    @Autowired
    private EmailService emailService;
}
```

**Why Field Injection is Discouraged:**
- Cannot make fields final
- Harder to test
- Hides dependencies
- No way to enforce required dependencies

## 3.3 @Autowired Annotation

```java
@Component
public class CourseService {
    
    // Autowired by type
    @Autowired
    private CourseRepository courseRepository;
    
    // Autowired with qualifier when multiple beans exist
    @Autowired
    @Qualifier("mysqlRepository")
    private DataRepository dataRepository;
    
    // Optional dependency
    @Autowired(required = false)
    private CacheService cacheService;
}
```

## 3.4 @Qualifier Annotation
Used when multiple beans of the same type exist: 

```java
@Repository("mysqlRepo")
public class MySQLStudentRepository implements StudentRepository {
    // MySQL implementation
}

@Repository("mongoRepo")
public class MongoStudentRepository implements StudentRepository {
    // MongoDB implementation
}

@Service
public class StudentService {
    
    @Autowired
    @Qualifier("mysqlRepo")
    private StudentRepository repository;
}
```

## 3.5 @Primary Annotation
Marks a bean as the default when multiple candidates exist:

```java
@Repository
@Primary
public class MySQLStudentRepository implements StudentRepository {
    // This will be injected by default
}

@Repository
public class MongoStudentRepository implements StudentRepository {
    // This requires @Qualifier to be injected
}
```

---

# 4. Spring Bean Lifecycle

## 4.1 Bean Lifecycle Phases

```
┌─────────────────────────────────────────────────────────────┐
│                    BEAN LIFECYCLE                           │
├─────────────────────────────────────────────────────────────┤
│  1.  Instantiation                                           │
│     └── Bean object is created                              │
│                                                             │
│  2. Populate Properties                                     │
│     └── Dependencies are injected                           │
│                                                             │
│  3. BeanNameAware. setBeanName()                            │
│     └── Bean receives its ID/name                          │
│                                                             │
│  4. BeanFactoryAware.setBeanFactory()                      │
│     └── Bean receives reference to BeanFactory             │
│                                                             │
│  5. ApplicationContextAware.setApplicationContext()         │
│     └── Bean receives reference to ApplicationContext       │
│                                                             │
│  6. BeanPostProcessor.postProcessBeforeInitialization()    │
│     └── Custom processing before initialization            │
│                                                             │
│  7. @PostConstruct / InitializingBean. afterPropertiesSet() │
│     └── Custom initialization logic                        │
│                                                             │
│  8. BeanPostProcessor.postProcessAfterInitialization()     │
│     └── Custom processing after initialization             │
│                                                             │
│  9. Bean is Ready for Use                                  │
│                                                             │
│  10. @PreDestroy / DisposableBean.destroy()                │
│      └── Cleanup before bean destruction                   │
└─────────────────────────────────────────────────────────────┘
```

## 4.2 Lifecycle Callback Methods

```java
@Component
public class StudentBean implements InitializingBean, DisposableBean {
    
    private String name;
    
    // Constructor
    public StudentBean() {
        System.out.println("1. Constructor called");
    }
    
    // Setter for dependency injection
    @Autowired
    public void setName(@Value("${student.name}") String name) {
        this.name = name;
        System. out.println("2. Properties populated");
    }
    
    // PostConstruct - called after dependency injection
    @PostConstruct
    public void init() {
        System.out.println("3. @PostConstruct - Custom initialization");
    }
    
    // InitializingBean interface method
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("4. afterPropertiesSet() called");
    }
    
    // PreDestroy - called before bean destruction
    @PreDestroy
    public void cleanup() {
        System.out.println("5. @PreDestroy - Cleanup resources");
    }
    
    // DisposableBean interface method
    @Override
    public void destroy() throws Exception {
        System.out.println("6. destroy() called");
    }
}
```

## 4.3 Custom Init and Destroy Methods

```java
@Configuration
public class AppConfig {
    
    @Bean(initMethod = "customInit", destroyMethod = "customDestroy")
    public DatabaseConnection databaseConnection() {
        return new DatabaseConnection();
    }
}

public class DatabaseConnection {
    
    public void customInit() {
        System.out.println("Initializing database connection.. .");
        // Open connection, load resources, etc.
    }
    
    public void customDestroy() {
        System.out.println("Closing database connection...");
        // Close connection, release resources, etc. 
    }
}
```

---

# 5. Spring MVC Architecture

## 5.1 MVC Design Pattern

```
┌──────────────────────────────────────────────────────────────┐
│                    MVC ARCHITECTURE                          │
├──────────────────────────────────────────────────────────────┤
│                                                              │
│   ┌─────────┐    Request    ┌────────────────────┐          │
│   │ Browser │ ────────────> │ DispatcherServlet  │          │
│   │ (Client)│               │ (Front Controller) │          │
│   └─────────┘               └─────────┬──────────┘          │
│        ▲                              │                      │
│        │                              ▼                      │
│        │                    ┌─────────────────────┐         │
│        │                    │  Handler Mapping    │         │
│        │                    └─────────┬───────────┘         │
│        │                              │                      │
│        │                              ▼                      │
│        │                    ┌─────────────────────┐         │
│        │                    │    Controller       │         │
│        │                    │  (@Controller)      │         │
│        │                    └─────────┬───────────┘         │
│        │                              │                      │
│        │                              ▼                      │
│        │                    ┌─────────────────────┐         │
│        │                    │     Service         │         │
│        │                    │   (@Service)        │         │
│        │                    └─────────┬───────────┘         │
│        │                              │                      │
│        │                              ▼                      │
│        │                    ┌─────────────────────┐         │
│        │                    │    Repository       │         │
│        │                    │  (@Repository)      │         │
│        │                    └─────────┬───────────┘         │
│        │                              │                      │
│        │                              ▼                      │
│        │                    ┌─────────────────────┐         │
│        │                    │    Database         │         │
│        │                    └─────────────────────┘         │
│        │                              │                      │
│        │     Response                 │                      │
│        │  ◄───────────────────────────┘                      │
│        │                                                     │
│        │                    ┌─────────────────────┐         │
│        └────────────────────│   View Resolver     │         │
│                             │   (JSP/Thymeleaf)   │         │
│                             └─────────────────────┘         │
└──────────────────────────────────────────────────────────────┘
```

## 5.2 DispatcherServlet Configuration

### Web.xml Configuration (Traditional):
```xml
<web-app>
    <servlet>
        <servlet-name>dispatcher</servlet-name>
        <servlet-class>
            org.springframework. web.servlet. DispatcherServlet
        </servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>/WEB-INF/dispatcher-servlet.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    
    <servlet-mapping>
        <servlet-name>dispatcher</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

### dispatcher-servlet.xml:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
           http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans. xsd
           http://www.springframework.org/schema/context
           http://www.springframework.org/schema/context/spring-context.xsd
           http://www.springframework.org/schema/mvc
           http://www.springframework.org/schema/mvc/spring-mvc.xsd">
    
    <!-- Enable annotation-driven Spring MVC -->
    <mvc:annotation-driven/>
    
    <!-- Component scan for controllers -->
    <context:component-scan base-package="com. cdac"/>
    
    <!-- View Resolver Configuration -->
    <bean class="org.springframework. web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/views/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
    
</beans>
```

## 5.3 Controller Implementation

```java
package com.cdac;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    // Display all students
    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "student-list";  // Returns student-list.jsp
    }
    
    // Show registration form
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("student", new Student());
        return "register";  // Returns register.jsp
    }
    
    // Process registration form
    @PostMapping("/register")
    public String registerStudent(@ModelAttribute Student student) {
        studentService.save(student);
        return "redirect:/students";  // Redirect to list
    }
    
    // Get student by ID
    @GetMapping("/{id}")
    public String getStudent(@PathVariable("id") int id, Model model) {
        Student student = studentService. findById(id);
        model.addAttribute("student", student);
        return "student-details";
    }
    
    // Delete student
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") int id) {
        studentService. deleteById(id);
        return "redirect:/students";
    }
}
```

## 5.4 Model and ModelAndView

```java
@Controller
public class CourseController {
    
    // Using Model
    @GetMapping("/courses")
    public String getCourses(Model model) {
        List<Course> courses = courseService.findAll();
        model.addAttribute("courses", courses);
        model.addAttribute("title", "Course List");
        return "course-list";
    }
    
    // Using ModelAndView
    @GetMapping("/course/{id}")
    public ModelAndView getCourse(@PathVariable int id) {
        ModelAndView mav = new ModelAndView();
        mav. setViewName("course-details");
        mav.addObject("course", courseService.findById(id));
        return mav;
    }
    
    // Using ModelMap
    @GetMapping("/dashboard")
    public String dashboard(ModelMap modelMap) {
        modelMap.addAttribute("totalCourses", courseService.count());
        modelMap.addAttribute("activeCourses", courseService.countActive());
        return "dashboard";
    }
}
```

## 5.5 JSP View Example

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student List</title>
    <style>
        table { border-collapse: collapse; width: 100%; }
        th, td { border:  1px solid #ddd; padding:  8px; text-align: left; }
        th { background-color: #4CAF50; color: white; }
        tr:nth-child(even) { background-color: #f2f2f2; }
    </style>
</head>
<body>
    <h2>Student Registration System</h2>
    
    <a href="students/register">Add New Student</a>
    
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Course</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="student" items="${students}">
                <tr>
                    <td>${student.id}</td>
                    <td>${student.name}</td>
                    <td>${student. email}</td>
                    <td>${student.course}</td>
                    <td>
                        <a href="students/${student.id}">View</a>
                        <a href="students/edit/${student.id}">Edit</a>
                        <a href="students/delete/${student.id}">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
```

---

# 6. Spring Boot

## 6.1 What is Spring Boot? 
Spring Boot is an opinionated framework built on top of Spring that simplifies the development of production-ready applications. 

## 6.2 Key Features
- **Auto-configuration**:  Automatically configures application based on dependencies
- **Standalone**:  Creates stand-alone applications with embedded servers
- **Production-ready**: Includes health checks, metrics, and externalized configuration
- **No XML configuration**: Promotes Java-based configuration
- **Starter dependencies**: Simplifies dependency management

## 6.3 Spring Boot Project Structure

```
project-root/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── demo/
│   │   │               ├── DemoApplication.java
│   │   │               ├── controller/
│   │   │               │   └── StudentController. java
│   │   │               ├── service/
│   │   │               │   └── StudentService.java
│   │   │               ├── repository/
│   │   │               │   └── StudentRepository.java
│   │   │               └── model/
│   │   │                   └── Student.java
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   ├── static/
│   │   │   │   ├── css/
│   │   │   │   └── js/
│   │   │   └── templates/
│   │   │       └── index.html
│   │   └── webapp/
│   │       └── WEB-INF/
│   │           └── views/
│   │               └── hello. jsp
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── demo/
│                       └── DemoApplicationTests.java
├── pom.xml
└── README. md
```

## 6.4 Main Application Class

```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework. boot.autoconfigure. SpringBootApplication;

@SpringBootApplication  // Combines @Configuration, @EnableAutoConfiguration, @ComponentScan
public class DemoApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

## 6.5 application.properties Configuration

```properties
# Server Configuration
server.port=8080
server.servlet.context-path=/api

# Database Configuration (MySQL)
spring.datasource.url=jdbc:mysql://localhost:3306/studentdb
spring.datasource.username=root
spring.datasource. password=password
spring.datasource. driver-class-name=com.mysql. cj.jdbc. Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring. jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.format_sql=true

# JSP View Resolver
spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view. suffix=.jsp

# Logging Configuration
logging. level.root=INFO
logging. level.com.example=DEBUG
logging. file. name=application.log

# Actuator Endpoints
management.endpoints.web.exposure. include=health,info,metrics
```

## 6.6 pom.xml with Spring Boot Starters

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         https://maven.apache.org/xsd/maven-4.0.0.xsd">
    
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.0</version>
        <relativePath/>
    </parent>
    
    <groupId>com.example</groupId>
    <artifactId>demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>demo</name>
    <description>Spring Boot Demo Project</description>
    
    <properties>
        <java. version>17</java.version>
    </properties>
    
    <dependencies>
        <!-- Spring Boot Web Starter -->
        <dependency>
            <groupId>org. springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        
        <!-- Spring Boot Data JPA Starter -->
        <dependency>
            <groupId>org. springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        
        <!-- MySQL Driver -->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>
        
        <!-- JSP Support -->
        <dependency>
            <groupId>org.apache.tomcat.embed</groupId>
            <artifactId>tomcat-embed-jasper</artifactId>
            <scope>provided</scope>
        </dependency>
        
        <!-- JSTL -->
        <dependency>
            <groupId>jakarta.servlet. jsp. jstl</groupId>
            <artifactId>jakarta.servlet.jsp. jstl-api</artifactId>
        </dependency>
        
        <!-- Validation -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        
        <!-- Spring Boot Test Starter -->
        <dependency>
            <groupId>org. springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework. boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

---

# 7. Spring Data JPA

## 7.1 Entity Class

```java
package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "students")
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(name = "name", nullable = false)
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email")
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    
    @NotBlank(message = "Course is required")
    @Column(name = "course")
    private String course;
    
    @Column(name = "created_at")
    @Temporal(TemporalType. TIMESTAMP)
    private java.util.Date createdAt;
    
    // Constructors
    public Student() {}
    
    public Student(String name, String email, String course) {
        this. name = name;
        this.email = email;
        this.course = course;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    
    public java.util.Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(java.util. Date createdAt) { this.createdAt = createdAt; }
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = new java.util. Date();
    }
    
    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', email='" + email + "', course='" + course + "'}";
    }
}
```

## 7.2 Repository Interface

```java
package com.example.demo. repository;

import com.example.demo. model.Student;
import org.springframework. data.jpa. repository.JpaRepository;
import org. springframework.data.jpa.repository.Query;
import org. springframework.data.repository.query.Param;
import org. springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    // Derived Query Methods (Spring generates implementation automatically)
    List<Student> findByName(String name);
    
    List<Student> findByNameContaining(String keyword);
    
    List<Student> findByCourse(String course);
    
    Optional<Student> findByEmail(String email);
    
    List<Student> findByNameAndCourse(String name, String course);
    
    List<Student> findByNameOrEmail(String name, String email);
    
    List<Student> findByNameOrderByCreatedAtDesc(String name);
    
    boolean existsByEmail(String email);
    
    long countByCourse(String course);
    
    // Custom JPQL Queries
    @Query("SELECT s FROM Student s WHERE s.course = :course")
    List<Student> findStudentsByCourse(@Param("course") String course);
    
    @Query("SELECT s FROM Student s WHERE s.name LIKE %:keyword% OR s.email LIKE %:keyword%")
    List<Student> searchStudents(@Param("keyword") String keyword);
    
    // Native SQL Query
    @Query(value = "SELECT * FROM students WHERE course = ?1", nativeQuery = true)
    List<Student> findByCourseNative(String course);
    
    // Update Query
    @Query("UPDATE Student s SET s.course = :course WHERE s.id = :id")
    @org.springframework.data. jpa.repository. Modifying
    @org.springframework.transaction.annotation.Transactional
    int updateCourse(@Param("id") Long id, @Param("course") String course);
}
```

## 7.3 Service Layer

```java
package com.example. demo.service;

import com.example. demo.model.Student;
import com. example.demo.repository.StudentRepository;
import org.springframework. beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction. annotation.Transactional;
import java.util. List;
import java.util. Optional;

@Service
@Transactional
public class StudentService {
    
    private final StudentRepository studentRepository;
    
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this. studentRepository = studentRepository;
    }
    
    // Create
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }
    
    // Read - All
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    // Read - By ID
    @Transactional(readOnly = true)
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }
    
    // Read - By Email
    @Transactional(readOnly = true)
    public Optional<Student> getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }
    
    // Read - By Course
    @Transactional(readOnly = true)
    public List<Student> getStudentsByCourse(String course) {
        return studentRepository.findByCourse(course);
    }
    
    // Update
    public Student updateStudent(Long id, Student studentDetails) {
        Student student = studentRepository. findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        
        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        student.setCourse(studentDetails.getCourse());
        
        return studentRepository. save(student);
    }
    
    // Delete
    public void deleteStudent(Long id) {
        if (! studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        studentRepository. deleteById(id);
    }
    
    // Search
    @Transactional(readOnly = true)
    public List<Student> searchStudents(String keyword) {
        return studentRepository.searchStudents(keyword);
    }
    
    // Count
    @Transactional(readOnly = true)
    public long countStudents() {
        return studentRepository.count();
    }
}
```

---

# 8. Spring JDBC

## 8.1 JDBC Template Configuration

```java
@Configuration
public class DatabaseConfig {
    
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource. setUrl("jdbc: mysql://localhost:3306/studentdb");
        dataSource. setUsername("root");
        dataSource. setPassword("password");
        return dataSource;
    }
    
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
```

## 8.2 DAO with JdbcTemplate

```java
package com.cdac;

import org.springframework.beans.factory. annotation.Autowired;
import org. springframework.jdbc.core.JdbcTemplate;
import org. springframework.jdbc.core. RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction. annotation.Transactional;
import java.sql.ResultSet;
import java.sql. SQLException;
import java.util.List;

@Repository
@Transactional
public class StudentDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    // RowMapper for Student
    private RowMapper<Student> studentRowMapper = new RowMapper<Student>() {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setId(rs.getInt("id"));
            student.setName(rs.getString("name"));
            student.setEmail(rs.getString("email"));
            student.setCourse(rs.getString("course"));
            return student;
        }
    };
    
    // Lambda version of RowMapper
    private RowMapper<Student> studentMapper = (rs, rowNum) -> {
        Student student = new Student();
        student.setId(rs.getInt("id"));
        student.setName(rs. getString("name"));
        student.setEmail(rs.getString("email"));
        student.setCourse(rs.getString("course"));
        return student;
    };
    
    // Insert Student
    public int registerStudent(Student student) {
        String sql = "INSERT INTO student(name, email, course) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, student.getName(), student.getEmail(), student.getCourse());
    }
    
    // Get All Students
    public List<Student> viewAllStudents() {
        String sql = "SELECT * FROM student";
        return jdbcTemplate.query(sql, studentMapper);
    }
    
    // Get Student by ID
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM student WHERE id = ? ";
        return jdbcTemplate.queryForObject(sql, studentMapper, id);
    }
    
    // Update Student
    public int updateStudent(Student student) {
        String sql = "UPDATE student SET name = ?, email = ?, course = ? WHERE id = ?";
        return jdbcTemplate.update(sql, student. getName(), student.getEmail(), 
                                   student.getCourse(), student.getId());
    }
    
    // Delete Student
    public int deleteStudent(int id) {
        String sql = "DELETE FROM student WHERE id = ? ";
        return jdbcTemplate.update(sql, id);
    }
    
    // Count Students
    public int countStudents() {
        String sql = "SELECT COUNT(*) FROM student";
        return jdbcTemplate. queryForObject(sql, Integer.class);
    }
    
    // Find by Course
    public List<Student> findByCourse(String course) {
        String sql = "SELECT * FROM student WHERE course = ?";
        return jdbcTemplate.query(sql, studentMapper, course);
    }
}
```

## 8.3 Traditional JDBC DAO (Without JdbcTemplate)

```java
package com.cdac;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework. beans.factory.annotation. Autowired;
import org.springframework. stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class StudentDAOTraditional {
    
    @Autowired
    private DataSource dataSource;
    
    // Register student
    public void registerStudent(Student student) {
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = dataSource.getConnection();
            String sql = "INSERT INTO student(name, email, course) VALUES (?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getCourse());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps, con);
        }
    }
    
    // View all students
    public List<Student> viewAllStudents() {
        List<Student> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = dataSource.getConnection();
            ps = con.prepareStatement("SELECT * FROM student");
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s. setEmail(rs. getString("email"));
                s.setCourse(rs.getString("course"));
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, con);
        }
        return list;
    }
    
    // Helper method to close resources
    private void closeResources(ResultSet rs, PreparedStatement ps, Connection con) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e. printStackTrace();
        }
    }
}
```

---

# 9. REST API Development

## 9.1 REST Controller

```java
package com.example.demo. controller;

import com.example.demo. model.Student;
import com.example. demo.service.StudentService;
import org.springframework. beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org. springframework.http.ResponseEntity;
import org.springframework.web. bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")  // Enable CORS
public class StudentRestController {
    
    @Autowired
    private StudentService studentService;
    
    // GET - All Students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
    
    // GET - Student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
            .map(student -> new ResponseEntity<>(student, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    // POST - Create Student
    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        Student savedStudent = studentService. saveStudent(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }
    
    // PUT - Update Student
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id, 
            @Valid @RequestBody Student studentDetails) {
        try {
            Student updatedStudent = studentService.updateStudent(id, studentDetails);
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // DELETE - Delete Student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // GET - Search Students
    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchStudents(@RequestParam String keyword) {
        List<Student> students = studentService.searchStudents(keyword);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
    
    // GET - Students by Course
    @GetMapping("/course/{course}")
    public ResponseEntity<List<Student>> getStudentsByCourse(@PathVariable String course) {
        List<Student> students = studentService. getStudentsByCourse(course);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
}
```

## 9.2 API Response Wrapper

```java
package com.example.demo.dto;

import java.time.LocalDateTime;

public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    
    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }
    
    public ApiResponse(boolean success, String message, T data) {
        this();
        this.success = success;
        this.message = message;
        this. data = data;
    }
    
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Success", data);
    }
    
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }
    
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }
    
    // Getters and Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
```

## 9.3 Exception Handling

```java
package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework. http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind. annotation.ExceptionHandler;
import org. springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    // Handle Resource Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(
            ResourceNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", ex.getMessage());
        response.put("status", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    // Handle Validation Errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errors = new HashMap<>();
        
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage())
        );
        
        response.put("success", false);
        response.put("message", "Validation failed");
        response.put("errors", errors);
        response.put("status", HttpStatus.BAD_REQUEST.value());
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    // Handle Generic Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "An unexpected error occurred");
        response.put("error", ex.getMessage());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

// Custom Exception
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s:  '%s'", resourceName, fieldName, fieldValue));
    }
}
```

---

# 10. Spring Security Basics

## 10.1 Security Configuration

```java
package com.example. demo.config;

import org. springframework.context.annotation.Bean;
import org. springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web. builders.HttpSecurity;
import org. springframework.security.config.annotation.web. configuration.EnableWebSecurity;
import org.springframework.security. core.userdetails. User;
import org.springframework.security. core.userdetails.UserDetails;
import org.springframework. security.core.userdetails.UserDetailsService;
import org.springframework.security. crypto.bcrypt. BCryptPasswordEncoder;
import org.springframework.security.crypto. password.PasswordEncoder;
import org. springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework. security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                . requestMatchers("/", "/home", "/public/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login? logout")
                .permitAll()
            )
            .csrf(csrf -> csrf. disable());  // Disable for REST APIs
        
        return http.build();
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User. builder()
            .username("user")
            .password(passwordEncoder().encode("password"))
            .roles("USER")
            .build();
        
        UserDetails admin = User. builder()
            .username("admin")
            .password(passwordEncoder().encode("admin123"))
            .roles("ADMIN", "USER")
            .build();
        
        return new InMemoryUserDetailsManager(user, admin);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

---

# 11. Session Management

## 11.1 HttpSession in Servlets

```java
package com.cdac;

import jakarta.servlet.ServletException;
import jakarta. servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta. servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if (validateUser(username, password)) {
            // Create session
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("loggedIn", true);
            session.setMaxInactiveInterval(30 * 60);  // 30 minutes
            
            response.sendRedirect("dashboard");
        } else {
            response.sendRedirect("login? error=true");
        }
    }
    
    private boolean validateUser(String username, String password) {
        // Validate against database
        return "admin".equals(username) && "password".equals(password);
    }
}

// Logout Servlet
public class LogoutServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            session.invalidate();  // Destroy session
        }
        
        response. sendRedirect("login");
    }
}
```

## 11.2 Session in Spring MVC

```java
@Controller
@SessionAttributes("user")  // Store "user" in session
public class UserController {
    
    @ModelAttribute("user")
    public User getUser() {
        return new User();
    }
    
    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, HttpSession session) {
        if (userService.authenticate(user)) {
            session. setAttribute("loggedInUser", user);
            return "redirect:/dashboard";
        }
        return "login";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session, SessionStatus sessionStatus) {
        session.invalidate();
        sessionStatus.setComplete();
        return "redirect:/login";
    }
}
```

---

# 12. Spring Annotations Reference

## 12.1 Core Annotations

| Annotation | Description | Usage |
|------------|-------------|-------|
| `@Configuration` | Indicates a class declares bean definitions | Configuration classes |
| `@Bean` | Declares a method as a bean producer | Bean factory methods |
| `@Component` | Generic stereotype for Spring-managed component | General components |
| `@Service` | Specialization of @Component for service layer | Service classes |
| `@Repository` | Specialization of @Component for data access | DAO/Repository classes |
| `@Controller` | Specialization of @Component for web controllers | MVC controllers |
| `@RestController` | Combines @Controller and @ResponseBody | REST API controllers |
| `@Autowired` | Automatic dependency injection | Field, constructor, setter |
| `@Qualifier` | Specifies which bean to inject | With @Autowired |
| `@Primary` | Indicates primary bean when multiple candidates | Bean declaration |
| `@Value` | Injects values from properties | Fields, parameters |
| `@Scope` | Specifies bean scope | Bean declaration |

## 12.2 Web Annotations

| Annotation | Description | Example |
|------------|-------------|---------|
| `@RequestMapping` | Maps HTTP requests to methods | `@RequestMapping("/api")` |
| `@GetMapping` | Maps HTTP GET requests | `@GetMapping("/users")` |
| `@PostMapping` | Maps HTTP POST requests | `@PostMapping("/users")` |
| `@PutMapping` | Maps HTTP PUT requests | `@PutMapping("/users/{id}")` |
| `@DeleteMapping` | Maps HTTP DELETE requests | `@DeleteMapping("/users/{id}")` |
| `@PatchMapping` | Maps HTTP PATCH requests | `@PatchMapping("/users/{id}")` |
| `@PathVariable` | Extracts value from URL path | `@PathVariable Long id` |
| `@RequestParam` | Extracts query parameters | `@RequestParam String name` |
| `@RequestBody` | Binds request body to object | `@RequestBody User user` |
| `@ResponseBody` | Writes return value to response body | Method return |
| `@ResponseStatus` | Sets HTTP response status | `@ResponseStatus(HttpStatus.CREATED)` |
| `@ModelAttribute` | Binds form data to model | `@ModelAttribute User user` |
| `@SessionAttributes` | Stores model attributes in session | Class level |
| `@CrossOrigin` | Enables CORS | Class or method level |

## 12.3 JPA/Persistence Annotations

| Annotation | Description | Usage |
|------------|-------------|-------|
| `@Entity` | Marks class as JPA entity | Entity classes |
| `@Table` | Specifies table name | Entity classes |
| `@Id` | Marks primary key field | Primary key |
| `@GeneratedValue` | Specifies ID generation strategy | With @Id |
| `@Column` | Specifies column mapping | Entity fields |
| `@Transient` | Excludes field from persistence | Non-persistent fields |
| `@OneToMany` | One-to-many relationship | Relationship mapping |
| `@ManyToOne` | Many-to-one relationship | Relationship mapping |
| `@ManyToMany` | Many-to-many relationship | Relationship mapping |
| `@JoinColumn` | Specifies join column | Foreign keys |
| `@Query` | Defines custom query | Repository methods |
| `@Modifying` | Indicates modifying query | Update/Delete queries |

## 12.4 Transaction & Validation Annotations

| Annotation | Description |
|------------|-------------|
| `@Transactional` | Declares transactional boundary |
| `@Valid` | Triggers validation |
| `@NotNull` | Field must not be null |
| `@NotBlank` | String must not be blank |
| `@NotEmpty` | Collection must not be empty |
| `@Size` | Size constraints |
| `@Min` / `@Max` | Numeric range constraints |
| `@Email` | Valid email format |
| `@Pattern` | Regex pattern matching |

---

# 13. Common Interview Questions

## 13.1 Spring Core Questions

**Q1: What is Spring Framework?**
> Spring is a comprehensive, lightweight framework for building Java enterprise applications. It provides infrastructure support including IoC, DI, AOP, and MVC, allowing developers to focus on business logic. 

**Q2: What is Inversion of Control (IoC)?**
> IoC is a design principle where the control of object creation and lifecycle is transferred from application code to a container.  Instead of objects creating their dependencies, the container injects them.

**Q3: What is Dependency Injection? **
> DI is a design pattern implementing IoC where dependencies are "injected" into objects rather than being created by them. Spring supports constructor, setter, and field injection. 

**Q4: What are the types of Dependency Injection? **
> - **Constructor Injection**: Dependencies passed through constructor (recommended)
> - **Setter Injection**: Dependencies set through setter methods
> - **Field Injection**: Dependencies injected directly into fields using @Autowired

**Q5: What is the difference between @Component, @Service, @Repository, and @Controller?**
> All are stereotype annotations marking Spring-managed beans: 
> - `@Component`: Generic component
> - `@Service`: Business/service layer (semantic meaning)
> - `@Repository`: Data access layer (adds exception translation)
> - `@Controller`: Web MVC controller

**Q6: What are Spring Bean Scopes?**
> - **singleton**: Single instance per container (default)
> - **prototype**: New instance per request
> - **request**: New instance per HTTP request
> - **session**: New instance per HTTP session
> - **application**: Single instance per ServletContext

**Q7: What is the difference between BeanFactory and ApplicationContext?**
> - BeanFactory: Basic container, lazy initialization
> - ApplicationContext:  Advanced container, eager initialization, supports i18n, event publication, AOP

**Q8: What is @Autowired?**
> @Autowired enables automatic dependency injection. Spring resolves and injects collaborating beans into the bean. 

## 13.2 Spring MVC Questions

**Q9: Explain Spring MVC architecture.**
> 1. Request comes to DispatcherServlet (Front Controller)
> 2. DispatcherServlet consults HandlerMapping
> 3. Controller processes request and returns ModelAndView
> 4. ViewResolver resolves view name to actual view
> 5. View renders the response

**Q10: What is DispatcherServlet?**
> DispatcherServlet is the front controller in Spring MVC that intercepts all incoming requests and dispatches them to appropriate controllers.

**Q11: What is the difference between @Controller and @RestController?**
> - `@Controller`: Returns view names, used with view resolvers
> - `@RestController`: Combines @Controller + @ResponseBody, returns data directly (JSON/XML)

**Q12: What is the difference between @RequestParam and @PathVariable?**
> - `@RequestParam`: Extracts query parameters (`/users?id=1`)
> - `@PathVariable`: Extracts values from URL path (`/users/1`)

## 13.3 Spring Boot Questions

**Q13: What is Spring Boot?**
> Spring Boot is an opinionated framework built on Spring that simplifies application development with auto-configuration, embedded servers, and starter dependencies.

**Q14: What is @SpringBootApplication?**
> It's a combination of: 
> - `@Configuration`: Marks class as configuration
> - `@EnableAutoConfiguration`: Enables auto-configuration
> - `@ComponentScan`: Enables component scanning

**Q15: What is Spring Boot auto-configuration?**
> Auto-configuration automatically configures Spring application based on dependencies on the classpath. 

**Q16: What are Spring Boot Starters?**
> Starters are dependency descriptors that include a set of related dependencies. Examples:
> - `spring-boot-starter-web`: Web applications
> - `spring-boot-starter-data-jpa`: JPA with Hibernate
> - `spring-boot-starter-security`: Spring Security
> - `spring-boot-starter-test`: Testing libraries

**Q17: What is application.properties/application.yml?**
> Configuration files for externalizing application settings like server port, database connections, logging levels, etc. 

**Q18: How does Spring Boot handle embedded servers?**
> Spring Boot includes embedded Tomcat, Jetty, or Undertow servers, eliminating the need for external server deployment.  The application runs as a standalone JAR. 

**Q19: What is Spring Boot Actuator? **
> Actuator provides production-ready features like health checks, metrics, info endpoints, and monitoring capabilities out of the box. 

**Q20: What is the difference between @SpringBootApplication and @EnableAutoConfiguration?**
> - `@EnableAutoConfiguration`: Only enables auto-configuration
> - `@SpringBootApplication`: Combines @EnableAutoConfiguration + @ComponentScan + @Configuration

## 13.4 Spring Data JPA Questions

**Q21: What is Spring Data JPA?**
> Spring Data JPA is a part of Spring Data that makes it easy to implement JPA-based repositories by reducing boilerplate code. 

**Q22: What is JpaRepository?**
> JpaRepository is an interface that provides CRUD operations, pagination, and sorting.  It extends PagingAndSortingRepository and CrudRepository.

**Q23: What are derived query methods?**
> Methods where Spring generates the query implementation based on method name: 
> ```java
> List<Student> findByNameContaining(String keyword);
> List<Student> findByCoursAndStatus(String course, String status);
> ```

**Q24: What is the difference between CrudRepository and JpaRepository?**
> - `CrudRepository`: Basic CRUD operations
> - `JpaRepository`: Extends CrudRepository, adds JPA-specific methods like flush(), saveAndFlush(), batch operations, pagination

**Q25: What is @Query annotation?**
> @Query allows defining custom JPQL or native SQL queries on repository methods: 
> ```java
> @Query("SELECT s FROM Student s WHERE s.course = :course")
> List<Student> findByCourse(@Param("course") String course);
> ```

**Q26: What is @Transactional? **
> @Transactional defines transactional boundaries.  It ensures all operations within the method execute in a single transaction - either all succeed or all rollback.

## 13.5 REST API Questions

**Q27: What are HTTP methods used in REST? **
> | Method | Operation | Example |
> |--------|-----------|---------|
> | GET | Read | Retrieve resource |
> | POST | Create | Create new resource |
> | PUT | Update | Full update of resource |
> | PATCH | Partial Update | Partial update |
> | DELETE | Delete | Remove resource |

**Q28: What is ResponseEntity?**
> ResponseEntity represents the entire HTTP response including status code, headers, and body.  It provides full control over the response.

**Q29: How do you handle exceptions in REST APIs?**
> Using `@RestControllerAdvice` with `@ExceptionHandler` methods to handle exceptions globally and return appropriate HTTP responses.

**Q30: What is CORS and how to enable it?**
> CORS (Cross-Origin Resource Sharing) allows restricted resources to be requested from another domain.  Enable using: 
> - `@CrossOrigin` annotation
> - Global CORS configuration
> - WebMvcConfigurer

## 13.6 Advanced Questions

**Q31: What is AOP (Aspect-Oriented Programming)?**
> AOP separates cross-cutting concerns (logging, security, transactions) from business logic using aspects, advice, pointcuts, and join points.

**Q32: What are the types of advice in AOP?**
> - `@Before`: Runs before method execution
> - `@After`: Runs after method (regardless of outcome)
> - `@AfterReturning`: Runs after successful return
> - `@AfterThrowing`: Runs after exception
> - `@Around`: Wraps method execution

**Q33: What is Spring Boot DevTools?**
> Developer tools that provide: 
> - Automatic restart on code changes
> - LiveReload support
> - Disabled caching for templates
> - Enhanced development experience

**Q34: What is the difference between @Component and @Bean?**
> - `@Component`: Class-level, auto-detected via component scanning
> - `@Bean`: Method-level in @Configuration class, explicit bean definition, more control over instantiation

**Q35: How does Spring handle circular dependencies?**
> Spring can handle circular dependencies for setter/field injection using three-level cache.  Constructor injection circular dependencies cause errors.  Best practice:  redesign to avoid circular dependencies.

---

# 14. Troubleshooting Guide

## 14.1 Common Errors and Solutions

### Error: 404 - dispatcher-servlet.xml Not Found

**Problem:**
```
HTTP Status 404 - /WEB-INF/dispatcher-servlet.xml
```

**Solutions:**
1. Run Maven clean and install: 
   ```bash
   mvn clean install
   ```
2. Right-click project → Run As → Maven clean
3. Right-click project → Run As → Maven install
4. Right-click project → Properties → Deployment Assembly → Add Maven Dependencies

### Error: No qualifying bean of type found

**Problem:**
```
NoSuchBeanDefinitionException: No qualifying bean of type 'com.example.Service' available
```

**Solutions:**
1. Ensure class is annotated with @Component, @Service, @Repository, or @Controller
2. Verify component scan covers the package: 
   ```java
   @ComponentScan(basePackages = "com.example")
   ```
3. Check if bean is defined in configuration class
4. Verify @Autowired is on correct field/constructor

### Error: Could not autowire.  No beans of type found

**Problem:**
```
Could not autowire. No beans of 'StudentRepository' type found. 
```

**Solutions:**
1. Add @Repository annotation to repository interface
2. Ensure @EnableJpaRepositories is configured: 
   ```java
   @EnableJpaRepositories(basePackages = "com.example. repository")
   ```
3. Check if Spring Data JPA dependency is included

### Error: Table doesn't exist

**Problem:**
```
SQLSyntaxErrorException: Table 'database.student' doesn't exist
```

**Solutions:**
1. Set hibernate ddl-auto property: 
   ```properties
   spring.jpa.hibernate.ddl-auto=update
   ```
2. Create table manually in database
3. Verify database connection properties
4. Check @Table(name = "correct_table_name")

### Error: Failed to configure DataSource

**Problem:**
```
Failed to configure a DataSource: 'url' attribute is not specified
```

**Solutions:**
1. Add database configuration in application.properties:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/dbname
   spring. datasource.username=root
   spring. datasource.password=password
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   ```
2. Add database driver dependency to pom.xml
3. Verify database server is running

### Error: Whitelabel Error Page

**Problem:**
```
Whitelabel Error Page - This application has no explicit mapping for /error
```

**Solutions:**
1. Check if controller mapping exists for the URL
2. Verify @Controller or @RestController annotation
3. Ensure @RequestMapping path is correct
4. Check if view resolver is configured properly
5. Verify JSP files are in correct location

### Error: Cannot resolve view

**Problem:**
```
Could not resolve view with name 'viewName' in servlet
```

**Solutions:**
1. Configure view resolver: 
   ```properties
   spring.mvc.view.prefix=/WEB-INF/views/
   spring.mvc. view.suffix=. jsp
   ```
2. Verify JSP file exists at correct path
3. Add tomcat-embed-jasper dependency for JSP support
4. Check return value in controller method

### Error: JSON parse error

**Problem:**
```
JSON parse error:  Cannot deserialize value of type
```

**Solutions:**
1. Verify JSON structure matches Java object
2. Check field names match (case-sensitive)
3. Add @JsonProperty for custom field mapping
4. Ensure proper Content-Type header:  application/json
5. Add default constructor to model class

### Error:  Access Denied

**Problem:**
```
403 Forbidden - Access Denied
```

**Solutions:**
1. Check Spring Security configuration
2. Verify user has required role/authority
3. Disable CSRF for REST APIs if needed: 
   ```java
   http.csrf(csrf -> csrf.disable());
   ```
4. Add proper authorization rules

## 14.2 Maven Build Issues

### Clean and Rebuild
```bash
# Clean target directory
mvn clean

# Compile source code
mvn compile

# Run tests
mvn test

# Package application
mvn package

# Install to local repository
mvn install

# Skip tests during build
mvn package -DskipTests

# Force update dependencies
mvn clean install -U
```

### Dependency Conflicts
```xml
<!-- Exclude conflicting dependency -->
<dependency>
    <groupId>org.springframework. boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

## 14.3 Debugging Tips

### Enable Debug Logging
```properties
# application.properties
logging.level.root=INFO
logging.level. com.example=DEBUG
logging.level.org.springframework=DEBUG
logging.level.org.hibernate. SQL=DEBUG
logging.level.org. hibernate.type.descriptor.sql.BasicBinder=TRACE
```

### Show SQL Queries
```properties
spring.jpa.show-sql=true
spring.jpa. properties.hibernate.format_sql=true
```

### Enable Actuator Endpoints
```properties
management.endpoints.web.exposure.include=*
management.endpoint. health.show-details=always
```

---

# 15. Best Practices

## 15.1 Project Structure Best Practices

```
src/main/java/com/example/demo/
├── DemoApplication.java              # Main class
├── config/                           # Configuration classes
│   ├── SecurityConfig.java
│   ├── WebConfig.java
│   └── DatabaseConfig.java
├── controller/                       # REST/MVC Controllers
│   ├── StudentController.java
│   └── CourseController.java
├── service/                          # Business logic
│   ├── StudentService.java
│   ├── impl/
│   │   └── StudentServiceImpl. java
│   └── CourseService. java
├── repository/                       # Data access layer
│   ├── StudentRepository.java
│   └── CourseRepository.java
├── model/                            # Entity classes
│   ├── Student.java
│   └── Course.java
├── dto/                              # Data Transfer Objects
│   ├── StudentDTO.java
│   ├── request/
│   │   └── CreateStudentRequest.java
│   └── response/
│       └── StudentResponse.java
├── exception/                        # Custom exceptions
│   ├── ResourceNotFoundException.java
│   └── GlobalExceptionHandler.java
├── util/                             # Utility classes
│   └── DateUtils.java
└── constant/                         # Constants
    └── AppConstants.java
```

## 15.2 Coding Best Practices

### Use Constructor Injection
```java
// ✅ Recommended - Constructor Injection
@Service
public class StudentService {
    
    private final StudentRepository repository;
    private final EmailService emailService;
    
    public StudentService(StudentRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }
}

// ❌ Avoid - Field Injection
@Service
public class StudentService {
    
    @Autowired
    private StudentRepository repository;
}
```

### Use DTOs for API Responses
```java
// ✅ Use DTO - Don't expose entity directly
@GetMapping("/{id}")
public ResponseEntity<StudentDTO> getStudent(@PathVariable Long id) {
    Student student = studentService.findById(id);
    StudentDTO dto = mapToDTO(student);
    return ResponseEntity.ok(dto);
}

// ❌ Avoid - Exposing entity directly
@GetMapping("/{id}")
public ResponseEntity<Student> getStudent(@PathVariable Long id) {
    return ResponseEntity.ok(studentService.findById(id));
}
```

### Use Interface for Services
```java
// Interface
public interface StudentService {
    Student save(Student student);
    List<Student> findAll();
    Optional<Student> findById(Long id);
    void deleteById(Long id);
}

// Implementation
@Service
public class StudentServiceImpl implements StudentService {
    
    private final StudentRepository repository;
    
    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Student save(Student student) {
        return repository.save(student);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return repository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Student> findById(Long id) {
        return repository.findById(id);
    }
    
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
```

### Use Proper Exception Handling
```java
// Custom Exception
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Long id) {
        super("Student not found with id:  " + id);
    }
}

// Service Layer
public Student findById(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new StudentNotFoundException(id));
}

// Global Exception Handler
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(StudentNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
```

### Use Validation
```java
// Entity/DTO with validation
public class CreateStudentRequest {
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be 2-100 characters")
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @NotBlank(message = "Course is required")
    private String course;
    
    // getters and setters
}

// Controller with @Valid
@PostMapping
public ResponseEntity<Student> createStudent(@Valid @RequestBody CreateStudentRequest request) {
    Student student = studentService.create(request);
    return new ResponseEntity<>(student, HttpStatus.CREATED);
}
```

### Use Proper Transaction Management
```java
@Service
@Transactional  // Class-level transaction
public class OrderService {
    
    @Transactional(readOnly = true)  // Read-only optimization
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
    
    @Transactional(propagation = Propagation.REQUIRED)  // Default
    public Order createOrder(Order order) {
        // Multiple operations in single transaction
        Order savedOrder = orderRepository.save(order);
        inventoryService.updateStock(order.getItems());
        notificationService.sendConfirmation(order);
        return savedOrder;
    }
    
    @Transactional(rollbackFor = Exception. class)  // Rollback on any exception
    public void processPayment(Long orderId, Payment payment) {
        // Transaction rolls back if any exception occurs
    }
}
```

## 15.3 Security Best Practices

```java
// 1. Never store plain text passwords
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}

// 2. Use HTTPS in production
// 3. Implement proper CORS configuration
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays. asList("https://trusted-domain.com"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/api/**", configuration);
    return source;
}

// 4. Validate and sanitize all inputs
// 5. Use parameterized queries to prevent SQL injection
// 6. Implement rate limiting for APIs
// 7. Keep dependencies updated
```

## 15.4 Performance Best Practices

```java
// 1. Use pagination for large datasets
@GetMapping
public Page<Student> getStudents(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy) {
    
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
    return studentRepository.findAll(pageable);
}

// 2. Use @Transactional(readOnly = true) for read operations
@Transactional(readOnly = true)
public List<Student> findAll() {
    return repository.findAll();
}

// 3. Use lazy loading appropriately
@Entity
public class Student {
    @OneToMany(fetch = FetchType. LAZY, mappedBy = "student")
    private List<Course> courses;
}

// 4. Implement caching
@Cacheable(value = "students", key = "#id")
public Student findById(Long id) {
    return repository. findById(id).orElse(null);
}

@CacheEvict(value = "students", key = "#student.id")
public Student update(Student student) {
    return repository. save(student);
}

// 5. Use connection pooling (HikariCP is default in Spring Boot)
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
```

## 15.5 Testing Best Practices

```java
// Unit Test for Service
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    
    @Mock
    private StudentRepository repository;
    
    @InjectMocks
    private StudentServiceImpl studentService;
    
    @Test
    void shouldReturnStudentWhenFound() {
        // Given
        Long id = 1L;
        Student expected = new Student("John", "john@email. com", "Java");
        when(repository.findById(id)).thenReturn(Optional.of(expected));
        
        // When
        Optional<Student> result = studentService.findById(id);
        
        // Then
        assertTrue(result.isPresent());
        assertEquals("John", result.get().getName());
        verify(repository, times(1)).findById(id);
    }
    
    @Test
    void shouldThrowExceptionWhenNotFound() {
        // Given
        Long id = 999L;
        when(repository.findById(id)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(StudentNotFoundException. class, () -> {
            studentService. getStudentById(id);
        });
    }
}

// Integration Test for Controller
@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void shouldCreateStudent() throws Exception {
        Student student = new Student("Jane", "jane@email.com", "Spring");
        
        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Jane"))
                .andExpect(jsonPath("$.email").value("jane@email.com"));
    }
    
    @Test
    void shouldReturnNotFoundForInvalidId() throws Exception {
        mockMvc.perform(get("/api/students/999"))
                .andExpect(status().isNotFound());
    }
}

// Repository Test
@DataJpaTest
class StudentRepositoryTest {
    
    @Autowired
    private StudentRepository repository;
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Test
    void shouldFindByEmail() {
        // Given
        Student student = new Student("Test", "test@email.com", "Java");
        entityManager.persistAndFlush(student);
        
        // When
        Optional<Student> found = repository.findByEmail("test@email. com");
        
        // Then
        assertTrue(found.isPresent());
        assertEquals("Test", found.get().getName());
    }
}
```

---

# 16. Quick Reference Cheat Sheet

## 16.1 Spring Boot Starter Dependencies

| Starter | Purpose |
|---------|---------|
| `spring-boot-starter-web` | Web applications, REST APIs |
| `spring-boot-starter-data-jpa` | JPA with Hibernate |
| `spring-boot-starter-data-jdbc` | JDBC with Spring Data |
| `spring-boot-starter-security` | Spring Security |
| `spring-boot-starter-validation` | Bean Validation |
| `spring-boot-starter-test` | Testing (JUnit, Mockito) |
| `spring-boot-starter-actuator` | Production monitoring |
| `spring-boot-starter-thymeleaf` | Thymeleaf templates |
| `spring-boot-starter-mail` | Email support |
| `spring-boot-starter-cache` | Caching support |

## 16.2 HTTP Status Codes

| Code | Meaning | Usage |
|------|---------|-------|
| 200 | OK | Successful GET, PUT |
| 201 | Created | Successful POST |
| 204 | No Content | Successful DELETE |
| 400 | Bad Request | Validation error |
| 401 | Unauthorized | Authentication required |
| 403 | Forbidden | Access denied |
| 404 | Not Found | Resource not found |
| 409 | Conflict | Duplicate resource |
| 500 | Internal Server Error | Server error |

## 16.3 Common application.properties

```properties
# Server
server.port=8080
server.servlet. context-path=/api

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/db
spring.datasource. username=root
spring.datasource.password=password

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# View Resolver
spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view. suffix=.jsp

# Logging
logging.level.root=INFO
logging.level.com.example=DEBUG

# Actuator
management.endpoints.web. exposure.include=health,info
```

## 16.4 Useful Maven Commands

```bash
mvn clean                    # Clean build
mvn compile                  # Compile
mvn test                     # Run tests
mvn package                  # Create JAR/WAR
mvn install                  # Install to local repo
mvn spring-boot:run          # Run application
mvn dependency:tree          # Show dependencies
mvn versions:display-dependency-updates  # Check updates
```

---

# 17. Sample Complete Application

## 17.1 Main Application

```java
package com.cdac.demo;

import org.springframework.boot.SpringApplication;
import org.springframework. boot.autoconfigure. SpringBootApplication;

@SpringBootApplication
public class StudentManagementApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(StudentManagementApplication.class, args);
        System.out.println("=================================");
        System.out.println("Application Started Successfully!");
        System.out.println("Access:  http://localhost:8080");
        System.out.println("=================================");
    }
}
```

## 17.2 Complete Student Entity

```java
package com.cdac. demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "students")
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType. IDENTITY)
    private Long id;
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100)
    @Column(nullable = false)
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(unique = true, nullable = false)
    private String email;
    
    @NotBlank(message = "Course is required")
    private String course;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructors
    public Student() {}
    
    public Student(String name, String email, String course) {
        this.name = name;
        this.email = email;
        this.course = course;
    }
    
    // Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime. now();
        this.updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime. now();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    
    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', email='" + email + 
               "', course='" + course + "'}";
    }
}
```

## 17.3 Complete Repository

```java
package com.cdac. demo.repository;

import com.cdac.demo.model. Student;
import org.springframework.data. domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data. jpa.repository. JpaRepository;
import org.springframework. data.jpa. repository.Query;
import org.springframework. data.repository.query.Param;
import org.springframework. stereotype.Repository;
import java.util. List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    // Derived queries
    Optional<Student> findByEmail(String email);
    List<Student> findByCourse(String course);
    List<Student> findByNameContainingIgnoreCase(String name);
    boolean existsByEmail(String email);
    long countByCourse(String course);
    
    // Custom JPQL query
    @Query("SELECT s FROM Student s WHERE " +
           "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(s.course) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Student> searchStudents(@Param("keyword") String keyword, Pageable pageable);
    
    // Native query
    @Query(value = "SELECT * FROM students ORDER BY created_at DESC LIMIT :limit", 
           nativeQuery = true)
    List<Student> findRecentStudents(@Param("limit") int limit);
}
```

## 17.4 Complete Service

```java
package com.cdac.demo.service;

import com.cdac.demo.exception.ResourceNotFoundException;
import com.cdac.demo.exception.DuplicateResourceException;
import com. cdac.demo. model.Student;
import com.cdac.demo.repository. StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain. Pageable;
import org.springframework. stereotype.Service;
import org.springframework.transaction. annotation.Transactional;
import java.util. List;

@Service
@Transactional
public class StudentService {
    
    private final StudentRepository repository;
    
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }
    
    // Create
    public Student createStudent(Student student) {
        if (repository.existsByEmail(student.getEmail())) {
            throw new DuplicateResourceException("Email already exists:  " + student.getEmail());
        }
        return repository. save(student);
    }
    
    // Read all with pagination
    @Transactional(readOnly = true)
    public Page<Student> getAllStudents(Pageable pageable) {
        return repository. findAll(pageable);
    }
    
    // Read by ID
    @Transactional(readOnly = true)
    public Student getStudentById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
    }
    
    // Read by email
    @Transactional(readOnly = true)
    public Student getStudentByEmail(String email) {
        return repository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Student", "email", email));
    }
    
    // Read by course
    @Transactional(readOnly = true)
    public List<Student> getStudentsByCourse(String course) {
        return repository.findByCourse(course);
    }
    
    // Update
    public Student updateStudent(Long id, Student studentDetails) {
        Student student = getStudentById(id);
        
        // Check if email is being changed to an existing one
        if (! student.getEmail().equals(studentDetails.getEmail()) &&
            repository.existsByEmail(studentDetails.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + studentDetails.getEmail());
        }
        
        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        student.setCourse(studentDetails.getCourse());
        
        return repository.save(student);
    }
    
    // Delete
    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        repository.delete(student);
    }
    
    // Search
    @Transactional(readOnly = true)
    public Page<Student> searchStudents(String keyword, Pageable pageable) {
        return repository.searchStudents(keyword, pageable);
    }
    
    // Count
    @Transactional(readOnly = true)
    public long countStudents() {
        return repository.count();
    }
    
    // Recent students
    @Transactional(readOnly = true)
    public List<Student> getRecentStudents(int limit) {
        return repository.findRecentStudents(limit);
    }
}
```

## 17.5 Complete REST Controller

```java
package com.cdac.demo.controller;

import com.cdac.demo.model.Student;
import com.cdac. demo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain. PageRequest;
import org.springframework.data. domain.Pageable;
import org. springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org. springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java. util.List;
import java.util. Map;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {
    
    private final StudentService studentService;
    
    public StudentController(StudentService studentService) {
        this. studentService = studentService;
    }
    
    // GET all students with pagination
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") 
            ? Sort. by(sortBy).descending() 
            : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Student> studentPage = studentService. getAllStudents(pageable);
        
        Map<String, Object> response = new HashMap<>();
        response.put("students", studentPage.getContent());
        response.put("currentPage", studentPage.getNumber());
        response.put("totalItems", studentPage.getTotalElements());
        response.put("totalPages", studentPage. getTotalPages());
        
        return ResponseEntity. ok(response);
    }
    
    // GET student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }
    
    // POST create student
    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }
    
    // PUT update student
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody Student studentDetails) {
        Student updatedStudent = studentService. updateStudent(id, studentDetails);
        return ResponseEntity.ok(updatedStudent);
    }
    
    // DELETE student
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Student deleted successfully");
        return ResponseEntity.ok(response);
    }
    
    // GET search students
    @GetMapping("/search")
    public ResponseEntity<Page<Student>> searchStudents(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> results = studentService.searchStudents(keyword, pageable);
        return ResponseEntity.ok(results);
    }
    
    // GET students by course
    @GetMapping("/course/{course}")
    public ResponseEntity<List<Student>> getStudentsByCourse(@PathVariable String course) {
        List<Student> students = studentService.getStudentsByCourse(course);
        return ResponseEntity.ok(students);
    }
    
    // GET count
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getStudentCount() {
        Map<String, Long> response = new HashMap<>();
        response.put("count", studentService. countStudents());
        return ResponseEntity. ok(response);
    }
    
    // GET recent students
    @GetMapping("/recent")
    public ResponseEntity<List<Student>> getRecentStudents(
            @RequestParam(defaultValue = "5") int limit) {
        List<Student> students = studentService.getRecentStudents(limit);
        return ResponseEntity.ok(students);
    }
}
```

---

# 18. Conclusion

This comprehensive guide covers the essential aspects of Spring Framework and Spring Boot development. Key takeaways:

1. **Understand Core Concepts**: IoC, DI, and Bean lifecycle are fundamental
2. **Follow Best Practices**: Use constructor injection, DTOs, and proper exception handling
3. **Layer Your Application**: Controller → Service → Repository → Database
4. **Write Tests**: Unit tests, integration tests, and repository tests
5. **Handle Errors Gracefully**: Use global exception handlers
6. **Secure Your Application**: Implement proper authentication and authorization
7. **Optimize Performance**: Use pagination, caching, and proper transaction management

---

## 📚 Additional Resources

- [Spring Official Documentation](https://spring.io/docs)
- [Spring Boot Reference Guide](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA Reference](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Baeldung Spring Tutorials](https://www.baeldung.com/spring-tutorial)

---
