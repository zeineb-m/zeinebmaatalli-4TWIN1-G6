pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'   
        maven 'M2_HOME'   
    }

    environment {
        IMAGE_NAME = 'zeinebmaatalli/kaddem'  

        CONTAINER_NAME = 'zeinebmaatalli/kaddem'  
        DOCKER_USERNAME = 'zeinebmaatalli'  
        DOCKER_PASSWORD = 'Zeineb123' 
        COMPOSE_FILE = 'docker-compose.yml'  
    
    }

    stages {

        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/zeineb-m/zeinebmaatalli-4TWIN1-G6.git'
            }
        }

        stage('Check Maven Version') {
            steps {
                sh 'mvn -version'
            }
        }

        stage('Compile Project') {
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
                sh '''
                    mvn sonar:sonar \
                    -Dsonar.login=squ_5cd14c3d1442652f6739461650faa58ab2be84ac \
                    -Dmaven.test.skip=true
                '''
            }
        }

        stage('Deploy to Nexus') {
            steps {
                sh 'mvn deploy -Dmaven.test.skip=true'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME .'
            }
        }
    }  stage('Start with Docker Compose') {
            steps {
                sh 'docker-compose -f $COMPOSE_FILE up -d'
            }
        }

        stage('Restart Docker Compose') {
            steps {
                sh '''
                    docker-compose -f $COMPOSE_FILE down || true
                    docker-compose -f $COMPOSE_FILE up -d
                '''
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
