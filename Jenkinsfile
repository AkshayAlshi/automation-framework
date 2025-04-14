pipeline {
    agent any

    tools {
        maven 'Maven_3.8.6'  // Make sure this tool is configured in Jenkins global tools
        jdk 'JDK_17'         // Or your installed JDK version
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
                sh 'mvn clean test'
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
                    sh "java -cp target/classes:target/dependency/* ${EMAIL_CLASS}"
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
                 from: 'jenkins@example.com',
                 replyTo: 'qa@example.com',
                 subject: "❌ FAILURE: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 to: 'qa-team@example.com'
        }
    }
}
