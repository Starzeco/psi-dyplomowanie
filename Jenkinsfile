pipeline {

	agent {
		label 'docker-build-node'
	}
	
	environment {
		DOCKERHUB_CREDENTIALS = credentials('abb56d58-f54e-467a-8c07-0055d2847982')
		BACKEND_IMAGE_NAME = 'starzecstudent/psi-backend'
		FRONTEND_IMAGE_NAME = 'starzecstudent/psi-frontend'
		IMAGE_TAG = 'latest'
	}
    
    stages {
    	
    	stage('Login-dockerhub') {
    		steps {
    			sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
    		}
    	}
    	
		stage('Back-end-build') {
			steps {
				sh 'docker build -t $BACKEND_IMAGE_NAME:$IMAGE_TAG backend/'
			}
		}
		
		
		
		stage('Front-end-build') {
			steps {
				sh 'docker build -t $FRONTEND_IMAGE_NAME:$IMAGE_TAG frontend/'
			}
		}
		
		stage('Push-images') {
			steps {
				sh '''
					docker push $BACKEND_IMAGE_NAME:$IMAGE_TAG
					docker push $FRONTEND_IMAGE_NAME:$IMAGE_TAG
				'''
			}
		}
	}
	
	post {
		always {
			sh 'docker logout'
		}
	}
}
