# Database Options for Exercise Backend

## Current Configuration: H2 File-Based (Recommended)

✅ **Already configured!** The project now uses H2 in file-based mode, which means:
- Data persists between application restarts
- Database file is stored at: `./data/exercise-db.mv.db` (in project root)
- No additional dependencies needed
- Zero configuration required
- Perfect for small to medium projects

### Pros:
- ✅ Already in your `pom.xml`
- ✅ No server installation needed
- ✅ Very lightweight (~2MB)
- ✅ H2 Console available at `/h2-console`
- ✅ Full SQL support
- ✅ Good for development and small deployments

### Cons:
- ⚠️ Not ideal for high-concurrency production environments
- ⚠️ Single file can get corrupted if application crashes during write

---

## Alternative Option: SQLite

If you prefer SQLite, here's how to configure it:

### 1. Update `pom.xml`:
Replace H2 dependency with SQLite:
```xml
<!-- Remove H2 -->
<!-- <dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency> -->

<!-- Add SQLite -->
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>3.44.1.0</version>
</dependency>
```

### 2. Update `application.properties`:
```properties
# Database Configuration (SQLite)
spring.datasource.url=jdbc:sqlite:./data/exercise.db
spring.datasource.driverClassName=org.sqlite.JDBC
spring.datasource.username=
spring.datasource.password=

# JPA Configuration
spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### 3. Add SQLite Dialect Dependency:
```xml
<dependency>
    <groupId>org.hibernate.orm</groupId>
    <artifactId>hibernate-community-dialects</artifactId>
</dependency>
```

### SQLite Pros:
- ✅ Industry standard for embedded databases
- ✅ Very stable and widely used
- ✅ Excellent for mobile and desktop apps
- ✅ Single file database

### SQLite Cons:
- ⚠️ Requires additional dependencies
- ⚠️ No built-in web console (need external tool)
- ⚠️ Limited concurrent write performance

---

## Comparison Table

| Feature | H2 (File-based) | SQLite |
|---------|----------------|--------|
| **Setup Complexity** | ⭐⭐⭐⭐⭐ (Already configured) | ⭐⭐⭐ (Need to add dependencies) |
| **File Size** | Small | Small |
| **Web Console** | ✅ Built-in | ❌ Need external tool |
| **Spring Boot Support** | ✅ Excellent | ⚠️ Good (needs dialect) |
| **Performance** | Fast | Fast |
| **Production Ready** | ⚠️ Small projects | ✅ Widely used |
| **Dependencies** | ✅ Already in project | ❌ Need to add |

---

## Recommendation

**For this project, H2 file-based is the best choice because:**
1. ✅ Already configured and working
2. ✅ No additional setup needed
3. ✅ Perfect for event management system (small to medium scale)
4. ✅ H2 Console makes debugging easy
5. ✅ Can easily migrate to PostgreSQL/MySQL later if needed

The database file will be created at: `d:\exercise-backend\data\exercise-db.mv.db`

---

## Database File Location

The H2 database file is stored in:
```
./data/exercise-db.mv.db
```

**Important:** Add this to `.gitignore` if you don't want to commit test data:
```
data/
*.mv.db
```

---

## Migration Path (Future)

If you need to scale up later, you can easily migrate to:
- **PostgreSQL** - Best for production, excellent performance
- **MySQL/MariaDB** - Popular choice, good performance
- **H2 Server Mode** - Run H2 as a separate server

The JPA entities and repositories will work with minimal changes!


