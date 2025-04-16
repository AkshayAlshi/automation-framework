pipeline {
    agent any

    tools {
        maven 'MAVEN_HOME'      // Make sure this is configured in Jenkins > Global Tool Configuration
        jdk 'JAVA_HOME'         // Same here
    }

    environment {
        REPORT_DIR = "reports"
        EMAIL_CLASS = "utils.EmailSender"
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/AkshayAlshi/automation-framework.git' // ✅ Updated
            }
        }

        stage('Build & Test') {
            steps {
                bat 'mvn clean test'
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
                    bat "java -cp target\\classes;target\\dependency\\* %EMAIL_CLASS%"
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
