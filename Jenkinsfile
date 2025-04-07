pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'   
        maven 'M2_HOME'   
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

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh '''
                        mvn sonar:sonar \
                        -Dmaven.test.skip=true
                    '''
                }
            }
        }

        stage('Deploy to Nexus') {
            steps {
                sh 'mvn deploy -Dmaven.test.skip=true'
            }
        }
    }
}
