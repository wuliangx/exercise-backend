# Eclipse Lombok Setup Guide

## Quick Setup Steps

### 1. Download Lombok
- Visit: https://projectlombok.org/download
- Download the latest `lombok.jar`

### 2. Install Lombok in Eclipse
1. Close Eclipse completely
2. Open command prompt/terminal
3. Navigate to the folder containing `lombok.jar`
4. Run: `java -jar lombok.jar`
5. The installer window will open
6. Click "Specify location..." and select your Eclipse installation folder
7. Click "Install/Update"
8. Click "Quit Installer"

### 3. Enable Annotation Processing
1. In Eclipse, right-click your project: `exercise-backend`
2. Select **Properties**
3. Go to **Java Compiler** → **Annotation Processing**
4. Check **Enable project specific settings**
5. Check **Enable annotation processing**
6. Click **Apply and Close**

### 4. Clean and Rebuild
1. Right-click project → **Clean...**
2. Select your project
3. Click **Clean**
4. Wait for the project to rebuild

### 5. Verify Installation
- Go to **Help** → **About Eclipse IDE**
- You should see Lombok version information in the dialog

## Troubleshooting

### If Lombok annotations still don't work:

1. **Check Eclipse version compatibility:**
   - Eclipse 2024-03 requires Lombok 1.18.31 or higher
   - Update Lombok if needed

2. **Manual eclipse.ini configuration:**
   - Find `eclipse.ini` in your Eclipse installation directory
   - Add this line before `-vmargs`:
     ```
     -javaagent:C:/path/to/lombok.jar
     ```
   - Replace with actual path to lombok.jar
   - Restart Eclipse

3. **Refresh Maven project:**
   - Right-click project → **Maven** → **Update Project**
   - Check "Force Update of Snapshots/Releases"
   - Click **OK**

4. **Check project build path:**
   - Right-click project → **Properties**
   - **Java Build Path** → **Libraries**
   - Ensure Lombok is listed (it should be under Maven Dependencies)

## Current Lombok Usage in Project

The following classes use Lombok annotations:
- `User.java` - @Data, @NoArgsConstructor, @AllArgsConstructor
- `Event.java` - @Data, @NoArgsConstructor, @AllArgsConstructor
- `EventRegistration.java` - @Data, @NoArgsConstructor, @AllArgsConstructor
- `AuthResponse.java` - @Data, @NoArgsConstructor, @AllArgsConstructor
- `EventDto.java` - @Data, @NoArgsConstructor, @AllArgsConstructor
- `CreateEventRequest.java` - @Data
- `LoginRequest.java` - @Data
- `RegisterRequest.java` - @Data

## Notes

- Lombok is already configured in `pom.xml` as a provided dependency
- The Spring Boot Maven plugin is configured to exclude Lombok from the final JAR (which is correct)
- After installation, you should see getters, setters, constructors, etc. generated automatically in your classes







