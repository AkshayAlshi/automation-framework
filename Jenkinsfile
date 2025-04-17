pipeline {
    agent any

    tools {
        maven 'MAVEN_HOME'  // Make sure this name matches Global Tool Configuration in Jenkins
        jdk 'JAVA_HOME'     // Same here
    }

    environment {
        REPORT_DIR = "target/surefire-reports"
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
                archiveArtifacts artifacts: "${REPORT_DIR}/*.*", allowEmptyArchive: true
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
                 body: """❌ Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}
                          Check console output at ${env.BUILD_URL}""",
                 from: 'akshayalshi@gmail.com',
                 replyTo: 'akshayalshi@gmail.com',
                 subject: "❌ Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 to: 'alshiakshay55@gmail.com'
        }
    }
}
