pipeline {
    agent any

    environment {
        AWS_ACCOUNT_ID = "802749364888"
        AWS_REGION = "ap-south-1"
        ECR_REGISTRY = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
        EC2_HOST = "ubuntu@13.205.117.50"
        EC2_KEY = "/d/HIS_Repos/his-key-ap-south-1.pem"
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
                    // Map service folder → ECR repo name
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
                        'BALANCE-ISSUANCE'             : 'balance-issuance-service',
                        'REPORTS-API'                  : 'reports-service',
                        'CONFIG-SERVER'                : 'config-server',
                        'EUREKA-SERVER'                : 'eureka-server'
                    ]

                    // Login once before loop
                    sh "aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${ECR_REGISTRY}"

                    services.each { folder, repoName ->
                        sh """
                            cd ${folder}
                            docker build -t ${repoName} .
                            docker tag ${repoName}:latest ${ECR_REGISTRY}/${repoName}:latest
                            docker push ${ECR_REGISTRY}/${repoName}:latest
                            cd ..
                        """
                    }
                }
            }
        }

        stage('Deploy to EC2') {
            steps {
                sh """
                    ssh -i ${EC2_KEY} ${EC2_HOST} '
                        cd /home/ubuntu/his-deployment &&
                        docker-compose pull &&
                        docker-compose up -d
                    '
                """
            }
        }
    }
}
