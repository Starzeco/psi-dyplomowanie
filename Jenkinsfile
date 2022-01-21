pipeline {
    agent {
    	docker { image 'docker:git'}
    }
    
    stages {
    	
    	stage('Cloning Git') {
      		steps {
        		git 'https://github.com/Starzeco/psi-dyplomowanie'
      		}
    	}
    	
		stage('Back-end-build') {
			
			steps {
				sh '''
					cd backend
                    docker build -t psi-backend .
                '''
			}
		}
		stage('Front-end-build') {
			steps {
				sh '''
					cd frontend
                    docker build -t psi-frontend .
                '''
			}
		}
	}
}
