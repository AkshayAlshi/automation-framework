pipeline {
    agent any

    tools {
        maven 'MAVEN_HOME'  // Make sure this tool is configured in Jenkins global tools
        jdk 'JAVA_HOME'         // Ensure this JDK is added in Jenkins global tools
    }

    environment {
        REPORT_DIR = "reports"
        EMAIL_CLASS = "utils.EmailSender"
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/your-repo/test-automation.git'
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
