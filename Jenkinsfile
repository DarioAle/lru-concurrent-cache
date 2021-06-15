pipeline {
    agent any
    properties{
        promotions {
            promotion {
                name('Development')
                conditions {
                    manual('testuser')
                }
                wrappers {
                    timestamps()
                }
                actions {
                    shell('echo hello;')
                }
            }
        }
    }
    
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
