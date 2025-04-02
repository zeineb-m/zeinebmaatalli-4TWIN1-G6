pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'   
        maven 'M2_HOME'   
    }

    environment {
        IMAGE_NAME = 'kaddem'  // Nom de l'image Docker
        CONTAINER_NAME = 'kaddem'  // Nom du conteneur
        DOCKER_USERNAME = 'zeinebmaatalli'  // Ton nom d'utilisateur Docker Hub
        DOCKER_PASSWORD = 'Zeineb123' 
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


        stage('Deploy Container') {
            steps {
                script {
                    // Supprime l'ancien conteneur s'il existe déjà
                    sh 'docker stop $CONTAINER_NAME || true'
                    sh 'docker rm $CONTAINER_NAME || true'
                    
                    // Démarre un nouveau conteneur avec le nom 'kaddem' et expose le port 8082
                    sh 'docker run -d --name $CONTAINER_NAME -p 8082:8082 $IMAGE_NAME'
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
