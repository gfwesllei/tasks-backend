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
        stage{
            steps{
                timout(time:1,unit:'MINUTES'){
                    waitForQualityGate abortPipeline:true
                }
            }
        }
        
    }
}

