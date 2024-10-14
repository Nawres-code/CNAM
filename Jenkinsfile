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
        stage('SONAR ANALYSIS') {
            steps {
                sh 'mvn sonar:sonar'
            }
        }
    }
}
