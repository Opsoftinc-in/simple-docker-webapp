pipeline {
    agent any

    tools {
        maven 'Maven-3.9.x' // Configured under Manage Jenkins -> Tools -> Maven
        jdk 'JDK-21'        // Configured under Manage Jenkins -> Tools -> JDK
    }

    environment {
        APP_NAME     = 'elms-eureka-server'
        DOCKER_IMAGE = "elms-eureka-server:${BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                // If using the multi-module Maven wrapper or parent pom:
                sh 'mvn clean package -DskipTests=false'
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    dir('elms-eureka-server') {
                        sh "docker build -t ${DOCKER_IMAGE} -t ${APP_NAME}:latest ."
                    }
                }
            }
        }

        stage('Docker Verify') {
            steps {
                sh "docker images | grep ${APP_NAME}"
            }
        }
    }

    post {
        always {
            cleanWs()
        }
        success {
            echo "Successfully built and packaged Docker image for ${APP_NAME}."
        }
        failure {
            echo "Build failed. Check Maven or Docker logs."
        }
    }
}
