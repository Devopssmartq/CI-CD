def call() {
		sh 'katalonc -noSplash -runMode=console -projectPath="/home/jenkins/agent/workspace/Katalona-test/My First Web UI Project.prj" -retry=0 -testSuitePath="Test Suites/simpleloginsuite" -browserType="Chrome (headless)" -executionProfile="default" -apiKey="657a0519-3dae-4354-a9ce-55de5167d269" -orgID=41312 -licenseRelease=true --config -proxy.auth.option=NO_PROXY -proxy.system.option=NO_PROXY -proxy.system.applyToDesiredCapabilities=true -webui.autoUpdateDrivers=true'
		sh 'ls'
}