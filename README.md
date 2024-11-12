# Biometric Recognition Tool  

**Biometric Recognition Tool** is a biometric recognition tool developed in Kotlin. The project uses advanced image processing algorithms, such as SIFT (Scale-Invariant Feature Transform) and FLANN (Fast Library for Approximate Nearest Neighbors), integrated into a command line interface (CLI) to allow user interaction.  

## Functionalities  
- User registration**: Register with basic information and biometrics.  
- **Login with biometric authentication**: Validate your identity through biometric recognition.  
- Biometric update**: Replace biometric data when necessary.  

## Technologies Used
[![Technologies Used](https://skillicons.dev/icons?i=kotlin,postgres,gradle,docker)](https://skillicons.dev)
- Kotlin: Main language of the project.  
- Gradle: Build automation tool.  
- SIFT (Scale-Invariant Feature Transform): Algorithm for detecting and describing features in images.  
- FLANN (Fast Library for Approximate Nearest Neighbors): Library for fast search of approximate neighbors.  
- PostgreSQL: Relational database for persistence.  
- JDBC: Interface for connecting to and performing operations on the database.  

## Requirements  
- Java 17 or higher  
- Gradle installed  
- PostgreSQL configured**  
- OpenCV library: The file `opencv-480.jar` is already included in the project.  

## How to Run  
1. Clone the repository:  
   ```bash
   git clone https://github.com/gb-alves03/biometric-recognition-tool.git
   cd biometric-recognition-tool
