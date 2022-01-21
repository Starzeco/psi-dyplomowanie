pipeline {
    agent any
    tools {
        jdk 'jdk9'
    	maven 'Maven 3.8.4'
    	nodejs "node"
    }
    
    stages {
    	stage('Initialize') {
    		steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
    	}
    	
    	stage('Cloning Git') {
      		steps {
        		git 'https://github.com/Starzeco/psi-dyplomowanie'
      		}
    	}
    	
		stage('Back-end-build') {
			
			steps {
				sh '''
					cd backend
                    mvn clean -Dmaven.test.skip=true package
                '''
			}
		}
		stage('Front-end-build') {
			steps {
				sh '''
					cd frontend
                    npm run build -- --prod
                '''
			}
		}
	}
}
