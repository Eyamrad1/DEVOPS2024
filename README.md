# DevOps Pipeline for Spring Boot Project

**Technologies Used**: Jenkins, Docker, Nexus, SonarQube, Prometheus, Grafana, Git, JUnit, Mockito

## Project Description:

This project demonstrates a complete DevOps pipeline for a Spring Boot application, showcasing automation and continuous integration/continuous deployment (CI/CD) using **Jenkins**. The pipeline ensures code quality, containerization, testing, and monitoring, creating a seamless deployment process from development to production.

## Key Features:

1. **CI/CD Pipeline with Jenkins**:
   - Fully automated pipeline built with **Jenkins**, which performs tasks such as code retrieval, build, test execution, and deployment.
   - Integrated stages for testing, code analysis, deployment to Nexus, and image building for Docker.

2. **Code Quality Analysis**:
   - **SonarQube** is integrated into the pipeline to perform static code analysis, helping ensure high code quality and maintainability.
   - **JUnit** and **Mockito** are used to perform unit and integration tests, ensuring code reliability before deployment.

3. **Containerization with Docker**:
   - The Spring Boot application is packaged as a Docker image, making it portable and easy to deploy across different environments.
   - The application is tested within Docker containers before deployment to production.

4. **Artifact Management with Nexus**:
   - After building the project, artifacts are stored in **Nexus** for easy access and deployment across environments.

5. **Continuous Monitoring with Prometheus & Grafana**:
   - **Prometheus** collects metrics and **Grafana** is used to visualize them, providing real-time monitoring and insights into the application’s performance.

6. **Automated Notifications**:
   - Customized notifications are configured to keep the team informed about the pipeline's status, such as build failures or successful deployments.

## How the Pipeline Works:

- **Source Code Management**: Jenkins pulls the code from the GitHub repository.
- **Build & Test**: The project is built, and unit tests are executed using **JUnit** and **Mockito**. The code quality is analyzed with **SonarQube**.
- **Dockerization**: The application is packaged as a Docker container for consistency across different environments.
- **Artifact Deployment**: The build artifacts are stored in **Nexus** for further usage.
- **Deployment & Monitoring**: The application is deployed, and its performance is monitored using **Prometheus** and visualized with **Grafana**.

## Technologies and Tools Used:
- **CI/CD**: Jenkins
- **Containerization**: Docker
- **Artifact Repository**: Nexus
- **Code Quality**: SonarQube
- **Testing**: JUnit, Mockito
- **Monitoring**: Prometheus, Grafana
- **Version Control**: Git (GitHub)

## Benefits of This Approach:
- **Automation**: This pipeline automates every step from development to production, saving time and reducing the possibility of human errors.
- **Code Quality Assurance**: By integrating SonarQube, we ensure that the code meets high-quality standards before it’s deployed.
- **Scalability**: Docker containers ensure that the application can be easily scaled and deployed on any environment.
- **Real-time Monitoring**: Prometheus and Grafana allow for continuous monitoring of application health, helping teams proactively address issues.

## Screenshots:
![Capture d'écran 2024-11-17 112558](https://github.com/user-attachments/assets/f1ef7902-03e4-41fb-9db1-e845f0599d7e)
![Capture d'écran 2024-11-17 112615](https://github.com/user-attachments/assets/7379f903-dec9-402d-b853-c2e67b6dd266)
![Capture d'écran 2024-11-17 112236](https://github.com/user-attachments/assets/347e3628-7aa0-49c1-bd46-985c9d5579a1)
![Capture d'écran 2024-11-17 112220](https://github.com/user-attachments/assets/1ed5efe8-fd75-417b-bb67-32280b9fb5d6)
![Capture d'écran 2024-11-17 112129](https://github.com/user-attachments/assets/95db98e3-a469-44c8-a06a-0a7e80d4854d)
![Capture d'écran 2024-11-17 112220](https://github.com/user-attachments/assets/fd3cfff6-ceb7-4106-b255-8928558d0ba0)
![Capture d'écran 2024-11-17 112705](https://github.com/user-attachments/assets/2a3dae55-eb57-451c-8011-c6ab3999d397)
![Capture d'écran 2024-11-17 112718](https://github.com/user-attachments/assets/4e38fd3e-d428-4bd0-8a60-b1b63fb1a134)
![Capture d'écran 2024-11-17 112946](https://github.com/user-attachments/assets/c60b4481-c029-48a4-9adc-ab2679304e27)
