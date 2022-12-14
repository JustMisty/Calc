pipeline {
    agent any

    stages {
        stage('build') {
            steps {
                bat "build.bat"
            }
        }
      stage('test') {
            steps {
                bat "buildTest.bat"
            }
        }
	    
	    stage('Archive'){
                steps{
			dir('C:\\'){
				echo "Current build: ${BUILD_NUMBER}"
				zip zipFile: "${BUILD_NUMBER}.zip", archive:false, dir: 'ProgramData\\Jenkins\\.jenkins\\workspace\\Job228\\Calc\\bin\\Debug\\netcoreapp3.1'
				archiveArtifacts artifacts: "${BUILD_NUMBER}.zip"
			}
		  }
    	}
	    stage('Deploy'){
		  steps{
			  dir('C:\\'){
				  script{
					  try
					  {
						  bat("md C:\\Deploy\\")
					  }catch(Exception e){}
				  }
				  unzip zipFile: "${BUILD_NUMBER}.zip", dir: 'C:\\Deploy'
			  }
		  }
 }
	    
    }
}
