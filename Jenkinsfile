pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'   
        maven 'M2_HOME'   
    }

    environment {
        IMAGE_NAME = 'zeinebmaatalli/kaddem'  // Nom de l'image Docker
        CONTAINER_NAME = 'zeinebmaatalli/kaddem' // Nom du conteneur
        DOCKER_USERNAME = 'zeinebmaatalli'  // Ton nom d'utilisateur Docker Hub
        DOCKER_PASSWORD = 'Zeineb123'  // Ton mot de passe Docker Hub
        COMPOSE_FILE = 'docker-compose.yml'  // Fichier Docker Compose
    }

    stages {
        stage('GIT') {
            steps {
                git branch: 'main', url: 'https://github.com/zeineb-m/zeinebmaatalli-4TWIN1-G6.git'
            }
        }

        stage('Maven') {
            steps {
                sh 'mvn -version'
            }
        }

        stage('Compile Stage') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Package JAR') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Construction de l'image Docker avec le nom 'kaddem'
                    sh 'docker build -t $IMAGE_NAME .'
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                script {
                    // Arrêter et supprimer les anciens conteneurs si nécessaire
                    sh 'docker-compose -f $COMPOSE_FILE down || true'
                    // Démarre les services définis dans le fichier docker-compose.yml
                    sh 'docker-compose -f $COMPOSE_FILE up -d'
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    // Utilisation de docker login pour s'authentifier avant de pousser l'image
                    sh '''
                    echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin
                    docker push $IMAGE_NAME
                    '''
                }
            }
        }
    }
}
