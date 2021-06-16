pipeline {
    agent any
    properties(
        [ buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '4', numToKeepStr: '100'))]
    )
    parameters {
        string(name: 'buildType', defaultValue: 'maven', description: 'What type of build can be used, gradle or maven')
    }
    
    stages {
        stage('Build') {
            steps {
                echo "The build will be performed using ${params.buildType}"
                sh 'ant -version'
                sh 'ant -v clean compile'
                echo "Compilation successful"
            }
        }
        stage('Test') {
            steps {
                sh 'ant -v junit'
            }
        }
    }
}
