pipeline {
    agent any
    
    stages {
    	
    	stage('Cloning Git') {
      		steps {
        		git 'https://github.com/Starzeco/psi-dyplomowanie'
      		}
    	}
    	
		stage('Back-end-build') {
			agent {
				docker { image 'maven:3.8.1-jdk-11' }
			}
			steps {
				sh '''
					cd backend
                    mvn clean -Dmaven.test.skip=true package
                '''
			}
		}
		stage('Front-end-build') {
			agent {
				docker { image 'node:12.20-alpine' }
			}
			steps {
				sh '''
					cd frontend
                    npm install
                    npm run build -- --prod
                '''
			}
		}
	}
}
