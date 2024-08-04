### NOTES.md

```markdown
# Notes Application - Learning Summary

## Overview

This project involved building a Notes application using Spring Boot, integrating authentication with Auth0, and supporting social login with Google and GitHub. The application was deployed using Docker and Render.

## Key Learnings

### Spring Boot

- **Spring Boot Basics**: Learned how to set up a Spring Boot application, create controllers, and handle requests.
- **Spring Data JPA**: Used Spring Data JPA to interact with the database, including creating repositories and performing CRUD operations.

### Authentication with Auth0

- **Auth0 Integration**: Integrated Auth0 for user authentication, including setting up an Auth0 application and configuring callback URLs.
- **OAuth2 Login**: Implemented OAuth2 login with Auth0, Google, and GitHub, allowing users to log in using their social accounts.

### Frontend with Thymeleaf and Bulma

- **Thymeleaf**: Used Thymeleaf for server-side rendering of HTML templates, passing data from the backend to the frontend.
- **Bulma CSS**: Utilized the Bulma CSS framework for responsive and modern UI design.

### Docker

- **Docker Basics**: Learned how to create a Dockerfile to containerize the application.
- **Multi-Stage Builds**: Used multi-stage builds in Docker to optimize the build process and reduce the final image size.

### Deployment with Render

- **Render Setup**: Deployed the application on Render, including connecting the Git repository and configuring environment variables.
- **Troubleshooting**: Learned how to troubleshoot deployment issues, including verifying the presence of build artifacts and checking logs.

## Challenges and Solutions

### Authentication Issues

- **Problem**: Encountered issues with displaying the user's name instead of the Auth0 identifier.
- **Solution**: Modified the controller to use the `name` or `email` claim from the OAuth2 token, falling back to the `sub` claim if necessary.

### Docker Build Errors

- **Problem**: Faced errors related to missing JAR files during the Docker build process.
- **Solution**: Implemented a multi-stage Docker build to ensure the JAR file was created and copied correctly.

### Environment Configuration

- **Problem**: Needed to manage multiple environment variables for Auth0, Google, and GitHub credentials.
- **Solution**: Used environment variables to securely store and access credentials, both locally and in the Render deployment.

## Future Improvements

- **Testing**: Implement unit and integration tests to ensure the application's reliability and robustness.
- **Error Handling**: Improve error handling and user feedback, especially for authentication and authorization errors.
- **Scalability**: Explore options for scaling the application to handle more users and higher traffic.

## Conclusion

This project provided valuable experience in building a full-stack application with Spring Boot, integrating third-party authentication, and deploying using Docker and Render. The challenges faced and solutions implemented contributed to a deeper understanding of web development and deployment practices.
```
