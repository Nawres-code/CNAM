pipeline {
    agent any
    tools {
        maven 'MAVEN_HOME'
    }
    stages {
        stage('GIT') {
            steps {
                git branch: 'main',
                url: 'https://github.com/Nawres-code/CNAM.git'
            }
        }
        stage('CLEAN') {
            steps {
                sh 'mvn clean'
            }
        }
         stage('COMPILE') {
            steps {
                sh 'mvn compile'
            }
        }
    }
}
