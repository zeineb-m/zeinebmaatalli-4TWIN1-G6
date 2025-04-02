pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'   
        maven 'M2_HOME'   
    }

    environment {
        IMAGE_NAME = 'zeinebmaatalli/kaddem'  // Nom de l'image Docker
        CONTAINER_NAME = 'zeinebmaatalli/kaddem'  // Nom du conteneur
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
        stage('MVN SONARQUBE') {
            steps {
                sh "mvn sonar:sonar -Dsonar.login=65099d78d7dd1bb8e1d0f17069273c8524d7f3a8 -Dmaven.test.skip=true"
            }
        }
        // stage('Deploy to Nexus') {
        //             steps {
        //                 sh 'mvn deploy -Dmaven.test.skip=true'

        //             }
        //   }
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME .'
            }
        }

        stage('Docker Compose - Start') {
            steps {
                sh 'docker-compose -f $COMPOSE_FILE up -d'
            }
        }

        stage('Deploy with Docker Compose - Restart') {
            steps {
                sh 'docker-compose -f $COMPOSE_FILE down || true'
                sh 'docker-compose -f $COMPOSE_FILE up -d'
            }
        }

        stage('Push to Docker Hub') {
            steps {
                sh '''
                echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin
                docker push $IMAGE_NAME
                '''
            }
        }
    }
}
