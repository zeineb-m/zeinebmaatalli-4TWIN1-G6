pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    environment {
        IMAGE_NAME = 'zeinebmaatalli/kaddem'
        DOCKER_USERNAME = 'zeinebmaatalli'
        DOCKER_PASSWORD = 'Zeineb123'
        COMPOSE_FILE = 'docker-compose.yml'
    }

    stages {

        stage('1. Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/zeineb-m/zeinebmaatalli-4TWIN1-G6.git'
            }
        }

        stage('2. Check Maven Version') {
            steps {
                sh 'mvn -version'
            }
        }

        stage('3. Compile Project') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('4. Run Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('5. SonarQube Analysis') {
            steps {
                sh '''
                    mvn sonar:sonar \
                    -Dsonar.login=squ_5cd14c3d1442652f6739461650faa58ab2be84ac
                '''
            }
        }

        stage('6. Package Application') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }

        stage('7. Deploy to Nexus') {
            steps {
                sh 'mvn deploy -DskipTests'
            }
        }

        stage('8. Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME .'
            }
        }

        stage('9. Push Docker Image to Docker Hub') {
            steps {
                sh '''
                    echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin
                    docker push $IMAGE_NAME
                '''
            }
        }

        stage('10. Restart Services with Docker Compose') {
            steps {
                sh '''
                    docker-compose -f $COMPOSE_FILE down || true
                    docker-compose -f $COMPOSE_FILE up -d
                '''
            }
        }
    }
}
