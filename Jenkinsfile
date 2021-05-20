pipeline{
    agent any
    stages {
        stage('Build BackEnd'){
            steps{
                sh 'mvn clean package'
                deploy adapters: [tomcat8(credentialsId: 'tomcat_certo', path: '', url: 'http://localhost:8001/')], contextPath: null, war: 'target/tasks-backend.war'
            }
        }
    }
}