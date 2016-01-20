A simple node script to write some fake data into /zktests znode. The script uses FakerJS to create some fake person names and phone numbers. It uses node-zookeeper-client node module to interact with ZK quorum. The script sets some random fake data once every 3 secs.

Steps ---

	Install NodeJS
	Once done open a new cmd prompt and cd to this dir of the sample and do "npm install"
	start ZKServer in a separate prompt
	open a ZKCli and create the test path via the following
		create /zktests SomeData
	set a watch on /zktests
		get /zktests watch

	run the node app by typing in
		 node zktest.js







