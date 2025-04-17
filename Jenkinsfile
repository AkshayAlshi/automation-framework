pipeline {
    agent any

    tools {
        maven 'MAVEN_HOME'
        jdk 'JAVA_HOME'
    }

    environment {
        REPORT_DIR = "target/surefire-reports"
        EXTENT_REPORT_DIR = "target/reports"
        EXTENT_REPORT_FILE = "target/reports/ExtentReport.html"
        EXTENT_ZIP = "target/ExtentReport.zip"
        EMAIL_CLASS = "utils.EmailSender"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/AkshayAlshi/automation-framework.git'
            }
        }

        stage('Build & Test') {
            steps {
                bat 'mvn clean test dependency:copy-dependencies'
            }
        }

        stage('Archive Reports') {
            steps {
                archiveArtifacts artifacts: "${REPORT_DIR}/**", allowEmptyArchive: true
                archiveArtifacts artifacts: "${EXTENT_REPORT_FILE}", allowEmptyArchive: true
            }
        }

        stage('Zip Extent Report') {
            steps {
                bat """
                    powershell -Command "Compress-Archive -Path '${EXTENT_REPORT_DIR}\\*' -DestinationPath '${EXTENT_ZIP}' -Force"
                """
                archiveArtifacts artifacts: "${EXTENT_ZIP}", allowEmptyArchive: true
            }
        }

        stage('Send Email') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'smtp-creds', usernameVariable: 'SMTP_USER', passwordVariable: 'SMTP_PASS')]) {
                    bat """
                        java -Demail.username="%SMTP_USER%" -Demail.password="%SMTP_PASS%" -cp "target\\classes;target\\dependency\\*" ${EMAIL_CLASS}
                    """
                }
            }
        }
    }

    post {
        always {
            echo '✅ Pipeline completed.'
        }

        failure {
            mail bcc: '',
                 body: """❌ Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}
                          Check console output at ${env.BUILD_URL}""",
                 from: 'akshayalshi10@gmail.com',
                 replyTo: 'akshayalshi10@gmail.com',
                 subject: "❌ Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 to: 'dnyaneshwarhage111@gmail.com'
        }
    }
}
