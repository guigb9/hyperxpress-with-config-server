pipeline {
    agent any
    stages {
    // by thirsu 26/11/2021 04AM:00
        stage('Compilando ...') {
            steps {
                sh "mvn clean compile -Dmaven.test.skip=true"
            }
        }

        stage('Gerando pacotes ...') {
            steps {
                sh "mvn package"
            }
        }

        stage('Gerando imagem docker'){
             steps {
                sh 'docker build -t hyperxpress/hyperxpress-back:${BUILD_NUMBER} .'
             }
        }

        stage('Docker Hub Login'){
            steps {
                 withCredentials([string(credentialsId: 'DockerId', variable: 'Dockerpwd')]) {
                    sh "docker login -u hyperxpress -p hyper12345"
                }
            }
        }

        stage('Docker Push'){
            steps {
                sh 'docker push hyperxpress/hyperxpress-back:${BUILD_NUMBER}'
            }
        }

       stage('Limpando memória ...') {
         steps {
            sh "docker system prune --all --volumes --force"
         }
       }

        stage('Parando anterior imagem'){
            steps{
                sh 'docker rm --force hyper-container'
            }
        }

        stage('Docker deploy'){
            steps {
                sh 'docker run --name hyper-container -itd -p 80:80 hyperxpress/hyperxpress-back:${BUILD_NUMBER}'
            }
        }

        stage('Arquivando target') {
            steps {
                 archiveArtifacts '**/target/*.jar'
            }
        }
    }
}