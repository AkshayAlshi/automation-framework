pipeline {
    agent any

    tools {
        maven 'MAVEN_HOME'      // Ensure this matches your Jenkins tool name
        jdk 'JAVA_HOME'         // Ensure this matches your Jenkins JDK name
    }

    environment {
        REPORT_DIR = "reports"
        EMAIL_CLASS = "utils.EmailSender"
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
                archiveArtifacts artifacts: "${REPORT_DIR}/extent-report.html", allowEmptyArchive: true
            }
        }

        stage('Send Email') {
            steps {
                bat "java -cp target/classes;target/dependency/* ${EMAIL_CLASS}"
            }
        }
    }

    post {
        always {
            echo 'Pipeline completed.'
        }
        failure {
            mail bcc: '',
                 body: "❌ Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}\nCheck: ${env.BUILD_URL}",
                 from: 'akshayalshi@gmail.com',
                 replyTo: 'akshayalshi@gmail.com',
                 subject: "❌ Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 to: 'alshiakshay55@gmail.com'
        }
    }
}
