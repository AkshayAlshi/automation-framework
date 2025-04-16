pipeline {
    agent any

    tools {
        maven 'MAVEN_HOME'
        jdk 'JAVA_HOME'
    }

    environment {
        REPORT_DIR = "reports"
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/AkshayAlshi/automation-framework.git'
            }
        }

        stage('Build & Test') {
            steps {
                bat 'mvn clean compile test'
            }
        }

        stage('Archive Reports') {
            steps {
                archiveArtifacts artifacts: "${REPORT_DIR}/*.html", allowEmptyArchive: true
            }
        }

        stage('Send Email') {
            steps {
                script {
                    bat 'mvn dependency:copy-dependencies'
                    bat 'java -cp target\\automation-framework-0.0.1-SNAPSHOT-jar-with-dependencies.jar utils.EmailSender'
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline completed.'
        }

        failure {
            mail bcc: '',
                 body: "Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}\nCheck console output at ${env.BUILD_URL}",
                 from: 'akshayalshi@gmail.com',
                 replyTo: 'akshayalshi@gmail.com',
                 subject: "Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 to: 'alshiakshay55@gmail.com'
        }
    }
}
