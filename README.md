# Notes Application

This is a simple Notes application built with Spring Boot and Auth0 for authentication. The application allows users to create, edit, and delete notes. It also supports social login using Google and GitHub.

## Features

- User authentication with Auth0
- Social login with Google and GitHub
- Create, edit, and delete notes
- Responsive UI using Bulma CSS framework

## Technologies Used

- Spring Boot
- Auth0
- Google OAuth2
- GitHub OAuth2
- Thymeleaf
- Bulma CSS
- Docker

## Prerequisites

- Java 17
- Maven
- Docker
- Auth0 account
- Google Developer account
- GitHub Developer account

## Getting Started

### Auth0 Setup

1. Create an Auth0 account and set up a new application.
2. Configure the application with the following settings:
   - Allowed Callback URLs: `http://localhost:8080/login/oauth2/code/auth0`
   - Allowed Logout URLs: `http://localhost:8080`
   - Allowed Web Origins: `http://localhost:8080`
3. Note down the Client ID and Client Secret.

### Google OAuth2 Setup

1. Create a project in the Google Developer Console.
2. Set up OAuth 2.0 credentials and note down the Client ID and Client Secret.
3. Configure the OAuth consent screen and add the following redirect URI:
   - `http://localhost:8080/login/oauth2/code/google`

### GitHub OAuth2 Setup

1. Create an OAuth application in the GitHub Developer settings.
2. Note down the Client ID and Client Secret.
3. Add the following redirect URI:
   - `http://localhost:8080/login/oauth2/code/github`

### Environment Variables

Set the following environment variables in your application:

```plaintext
SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_AUTH0_CLIENT_ID=your_auth0_client_id
SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_AUTH0_CLIENT_SECRET=your_auth0_client_secret
SPRING_SECURITY_OAUTH2_CLIENT_PROVIDER_AUTH0_ISSUER_URI=https://your-auth0-domain/
SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_ID=your_google_client_id
SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GOOGLE_CLIENT_SECRET=your_google_client_secret
SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GITHUB_CLIENT_ID=your_github_client_id
SPRING_SECURITY_OAUTH2_CLIENT_REGISTRATION_GITHUB_CLIENT_SECRET=your_github_client_secret
```
