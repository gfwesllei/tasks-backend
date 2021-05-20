pipeline{
    agent any
    stages {
        stage('Build BackEnd'){
            steps{
                sh 'mvn clean package -DskipTests=true'
            }
        }
        stage('Unit Tests BackEnd'){
            steps{
                sh 'mvn test'
            }
        }
         stage('Sonar analysis'){
             environment{
                 scannerHome= tool 'SONAR_SCANNER'
             }
            steps{
                withSonarQubeEnv('SONAR_LOCAL'){
                    sh "${scannerHome}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBack -Dsonar.host.url=http://localhost:9000 -Dsonar.login=9eeaaf3a32f45b1f0aab11e7d2a365308866fd31 -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/src/test/**,**/model/**"
                }
            }
        }
        stage('Quality Gate'){
            steps{
                sleep(10)
                timeout(time: 1,unit: 'MINUTES'){
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Deploy BackEnd'){
            steps{
                deploy adapters: [tomcat8(credentialsId: 'tomcat_certo', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks-backend', war: 'target/tasks-backend.war'
            }
        }
        stage('API Test'){
            steps{
                dir('api-test'){
                    git branch: 'main', credentialsId: 'gfwesllei', url: 'https://github.com/gfwesllei/task-api-test'
                    sh 'mvn test'
                }
            }
        }
         stage('Build FrontEnd'){
            steps{
                dir('tasks-frontend'){
                    git credentialsId: 'gfwesllei', url: 'https://github.com/gfwesllei/tasks-frontend'
                    sh 'mvn clean package'
                }
            }
        }
        stage('Deploy FrontEnd'){
            steps{
                dir('tasks-frontend'){
                    deploy adapters: [tomcat8(credentialsId: 'tomcat_certo', path: '', url: 'http://localhost:8001/')], contextPath: 'tasks', war: 'target/tasks.war'
                }
            }
        }
        stage('Run FunctionalTest'){
            steps{
                dir('functional-test-tasks'){
                    git branch: 'main', credentialsId: 'gfwesllei', url: 'https://github.com/gfwesllei/functional-test-tasks'
                    sh 'mvn clean test'
                }
            }
        }
        stage('Deploy Prod'){
            steps{
               sh 'docker-compose build'
               sh 'docker-compose up -d'
            }            
        }
        stage('Run HealthCheck'){
            steps{
                sleep(10)
                dir('functional-test-tasks'){
                    sh 'mvn verify'
                }
            }
        }
        
    }
}

