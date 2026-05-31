pipeline {
    agent any

    environment {
        AWS_ACCOUNT_ID = "802749364888"
        AWS_REGION = "ap-south-1"
        ECR_REGISTRY = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
        EC2_HOST = "ubuntu@52.66.168.244"
        EC2_KEY = "/var/lib/jenkins/.ssh/his-key-ap-south-1.pem"
        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'develop', url: 'https://github.com/deepamunesh/his-project.git'
            }
        }

        stage('Build JARs') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    def services = [
                        'API-GATEWAY'                  : 'api-gateway',
                        'AUTH-SERVICE'                 : 'auth-service',
                        'ar-service'                   : 'ar-service',
                        'ssa-web-service'              : 'ssa-web-service',
                        'HIS-ADMIN-API'                : 'admin-service',
                        'Eligibility-Determination-API': 'eligibility-determination-service',
                        'ED-RULES-API'                 : 'ed-rules-service',
                        'DATA-COLLECTION-API'          : 'data-collection-service',
                        'CORRESPONDENCE-API'           : 'correspondence-service',
                        'BALLANCE-ISSUANCE'            : 'balance-issuance-service',
                        'REPORTS-API'                  : 'reports-service',
                        'CONFIG-SERVER'                : 'config-server',
                        'EUREKA-SERVER'                : 'eureka-server'
                    ]

                    withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws-creds']]) {
                        sh "aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${ECR_REGISTRY}"

                        services.each { folder, repoName ->
                            sh """
                                cd ${folder}
                                docker build -t ${repoName}:${IMAGE_TAG} .
                                docker tag ${repoName}:${IMAGE_TAG} ${ECR_REGISTRY}/${repoName}:${IMAGE_TAG}
                                docker push ${ECR_REGISTRY}/${repoName}:${IMAGE_TAG}
                                cd ..
                            """
                        }
                    }
                }
            }
        }

        stage('Update Compose & Deploy') {
            steps {
                sh """
                    sed -i 's/:latest/:${IMAGE_TAG}/g' docker-compose.yml
                    scp -i ${EC2_KEY} docker-compose.yml ${EC2_HOST}:/home/ubuntu/his-deployment/
                    ssh -i ${EC2_KEY} ${EC2_HOST} 'cd /home/ubuntu/his-deployment && docker-compose pull && docker-compose up -d'
                """
            }
        }
    }
}
