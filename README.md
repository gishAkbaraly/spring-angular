# Spring Angular Project

## Description

Ce projet est une application web avec un backend développé en Spring Boot et un frontend en Angular. L'application utilise Spring Security pour la gestion de la sécurité et JWT pour l'authentification. Le frontend Angular gère les scopes pour le contrôle des accès.

## Table des matières

- [Installation](#installation)
- [Configuration](#configuration)
- [Démarrage](#démarrage)
- [Fonctionnalités](#fonctionnalités)

## Installation

### Backend (Spring Boot)

1. Clonez le dépôt :

    ```sh
    git clone https://github.com/gishAkbaraly/spring-angular.git
    ```

2. Compilez le projet avec Maven :

    ```sh
    mvn clean install
    ```

### Frontend (Angular)

1. Allez dans le répertoire du frontend :

    ```sh
    cd /frontend
    ```

2. Installez les dépendances npm :

    ```sh
    npm install
    ```

## Configuration

### Backend

1. Créez un fichier `application.properties` dans `src/main/resources` et configurez votre base de données et autres propriétés nécessaires :

    ```properties
    spring.datasource.url=jdbc:xxx://localhost:xxx/votrebase
    spring.datasource.username=utilisateur
    spring.datasource.password=motdepasse
    jwt.secret=votreSecretJWT
    ```

### Frontend

1. Configurez l'URL de l'API dans `src/environments/environment.ts` :

    ```typescript
    export const environment = {
      production: false,
      apiUrl: 'http://localhost:8080/api'
    };
    ```

## Démarrage

### Backend

1. Démarrez l'application Spring Boot :

    ```sh
    mvn spring-boot:run
    ```

### Frontend

1. Démarrez l'application Angular :

    ```sh
    ng serve
    ```

2. Ouvrez votre navigateur et allez à `http://localhost:4200`.

## Fonctionnalités

- **Authentification JWT** : Utilisation de JSON Web Tokens pour l'authentification sécurisée.
- **Gestion des rôles et des permissions** : Contrôle d'accès basé sur les rôles et les scopes.
- **Interface utilisateur réactive** : Utilisation d'Angular pour une expérience utilisateur fluide et réactive.



## Licence

Ce projet est sous licence MIT. Voir le fichier [LICENSE](LICENSE) pour plus de détails.
