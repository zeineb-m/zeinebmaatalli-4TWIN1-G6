pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'   
        maven 'M2_HOME'   
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
    }

 
}
