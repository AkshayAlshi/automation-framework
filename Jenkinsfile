pipeline {
    agent any

    tools {
<<<<<<< HEAD
		maven 'MAVEN_HOME'  // Make sure this tool is configured in Jenkins global tools
        jdk 'JAVA_HOME'         // Ensure this JDK is added in Jenkins global tools
		  }
		  
=======
        maven 'MAVEN_HOME'  // Make sure this tool is configured in Jenkins global tools
        jdk 'JAVA_HOME'         // Ensure this JDK is added in Jenkins global tools
    }
>>>>>>> 33810879a251568799cc4ba185329e8b7315d52d

    environment {
        REPORT_DIR = "reports"
        EMAIL_CLASS = "utils.EmailSender"
    }

   stage('Checkout') {
    steps {
        git branch: 'main', url: 'https://github.com/AkshayAlshi/automation-framework.git'
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
                 from: 'akshayalshi@gmail.com',
                 replyTo: 'akshayalshi@gmail.com',
                 subject: "Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 to: 'alshiakshay55@gmail.com'
        }
    }
}
