## Project Structure

access http://localhost:8080/com.school/login    or
access http://localhost:8080/school/login
```
src/
└── main/
    ├── java/
    │   └── com/school/
    │       └── LoginController.java
    ├── resources/
    └── webapp/
        ├── index.jsp
        └── WEB-INF/
            ├── dispatcher-servlet.xml
            ├── web.xml
            └── views/
                ├── login.jsp
                ├── welcome.jsp
                └── error.jsp
```

## 1. Maven Dependencies (pom.xml)

```xml
<dependencies>
    <!-- Spring Web MVC -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>6.2.15</version>
    </dependency>
    
    <!-- Servlet API (provided by Tomcat) -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>4.0.1</version>
        <scope>provided</scope>
    </dependency>
    
    <!-- JSTL for JSP -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>jstl</artifactId>
        <version>1.2</version>
    </dependency>
</dependencies>
```

## 2. Web Configuration (web.xml)

```xml
<!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
    <servlet>
        <servlet-name>dispatcher</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <load-on-startup>1</load-on-startup>
    </servlet>
    
    <servlet-mapping>
        <servlet-name>dispatcher</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

**Key Points:**
- Servlet name is `dispatcher`, so Spring looks for `dispatcher-servlet.xml`
- URL pattern `/` means it handles all requests

## 3. Spring Configuration (dispatcher-servlet.xml)

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:context="http://www.springframework.org/schema/context"
    xmlns:mvc="http://www.springframework.org/schema/mvc"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="
        http://www.springframework.org/schema/beans 
        http://www.springframework.org/schema/beans/spring-beans.xsd 
        http://www.springframework.org/schema/context 
        http://www.springframework.org/schema/context/spring-context.xsd 
        http://www.springframework.org/schema/mvc 
        http://www.springframework.org/schema/mvc/spring-mvc.xsd">
    
    <mvc:annotation-driven />
    
    <!-- MUST match your controller package -->
    <context:component-scan base-package="com.school" />
    
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/views/" />
        <property name="suffix" value=".jsp" />
    </bean>
</beans>
```

**Key Points:**
- `component-scan` base-package **MUST** match your controller's package
- ViewResolver adds `/WEB-INF/views/` prefix and `.jsp` suffix to view names
- Returning `"login"` from controller → `/WEB-INF/views/login.jsp`

## 4. Controller (LoginController.java)

```java
package com.school;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller  // ⚠️ CRITICAL: Don't forget this annotation!
public class LoginController {
    
    @GetMapping("/login")
    public String showform() {
        return "login";  // Returns login.jsp
    }
    
    @PostMapping("/login")
    public String login(
            @RequestParam("username") String name, 
            @RequestParam("password") String pass, 
            Model model) {
        
        if(name.equals("admin") && pass.equals("admin123")) {
            model.addAttribute("user", name);
            return "welcome";  // Returns welcome.jsp
        } else {
            model.addAttribute("error", "Invalid username and password");
            return "error";  // Returns error.jsp
        }
    }
}
```

**Key Points:**
- `@Controller` annotation is **MANDATORY**
- `@GetMapping` for displaying forms
- `@PostMapping` for form submissions
- `Model` is used to pass data to views

## 5. JSP Views

### login.jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
    <h2>Login Here</h2>
    
    <form action="login" method="post"> 
        Username: <input type="text" name="username"> <br><br> 
        Password: <input type="password" name="password"> <br><br>
        <input type="submit" value="Login">
    </form>
</body>
</html>
```

### welcome.jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>
</head>
<body>
    <h2>Welcome ${user}</h2>
    <p>Login Successful</p>
</body>
</html>
```

### error.jsp

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error</title>
</head>
<body>
    <h2 style="color:red">${error}</h2>
    <a href="login">Try Again</a>
</body>
</html>
```

**Key Points:**
- `isELIgnored="false"` is **CRITICAL** for `${variable}` to work
- Without it, you'll see literal `${error}` or `${user}` text
- Expression Language (EL) allows accessing Model attributes

## 6. Common Issues and Solutions

### Issue 1: "No mapping for GET /login"

**Cause:** Missing `@Controller` annotation

**Solution:** Add `@Controller` to your controller class

---

### Issue 2: "${error}" or "${user}" displays literally

**Cause:** Expression Language is disabled

**Solution:** Add `isELIgnored="false"` to JSP page directive

---

### Issue 3: Controller not detected

**Cause:** Package mismatch in component-scan

**Solution:** Ensure `base-package` in dispatcher-servlet.xml matches controller package

Example:
- Controller package: `com.school`
- Component scan: `<context:component-scan base-package="com.school" />`

---

### Issue 4: 404 on view pages

**Cause:** Views not in correct location

**Solution:** Place JSP files in `/WEB-INF/views/` directory

---

## 7. URL Access

After deployment, access:
```
http://localhost:8080/com.school/login
```

Where `com.school` is your war file name (from `<finalName>` in pom.xml)

## 8. Testing Checklist

- [ ] Controller has `@Controller` annotation
- [ ] Component scan package matches controller package
- [ ] JSP files have `isELIgnored="false"`
- [ ] JSP files are in `/WEB-INF/views/` directory
- [ ] Maven dependencies are correct
- [ ] Project is built (Maven → Update Project)
- [ ] Tomcat is restarted after changes

## 9. Login Credentials

**Username:** admin  
**Password:** admin123

## 10. Flow Diagram

```
Browser → GET /login
    ↓
LoginController.showform()
    ↓
Returns "login"
    ↓
ViewResolver → /WEB-INF/views/login.jsp
    ↓
User submits form
    ↓
Browser → POST /login with username & password
    ↓
LoginController.login()
    ↓
Check credentials
    ├─ Valid → model.addAttribute("user", name) → return "welcome"
    └─ Invalid → model.addAttribute("error", message) → return "error"
    ↓
ViewResolver → /WEB-INF/views/welcome.jsp or error.jsp
    ↓
Display page with ${user} or ${error}
```

## 11. Quick Reference

| Annotation | Purpose |
|------------|---------|
| `@Controller` | Marks class as Spring MVC controller |
| `@GetMapping` | Handles HTTP GET requests |
| `@PostMapping` | Handles HTTP POST requests |
| `@RequestParam` | Binds request parameter to method parameter |

| Component | File Location |
|-----------|---------------|
| Controller | `src/main/java/com/school/` |
| Views | `src/main/webapp/WEB-INF/views/` |
| Spring Config | `src/main/webapp/WEB-INF/dispatcher-servlet.xml` |
| Web Config | `src/main/webapp/WEB-INF/web.xml` |

---

**Remember:** After ANY code changes, always rebuild the project and restart Tomcat!