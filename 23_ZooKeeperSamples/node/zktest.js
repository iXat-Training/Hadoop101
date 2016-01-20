var zookeeper = require("node-zookeeper-client");
var faker = require("faker");

var path = "/zktests";

console.log("connecting to zookeeper");

zk = new zookeeper.createClient("localhost:2181", {
        sessionTimeout: 10,
        spinDelay : 10,
        retries : 0
});
		
zk.on("connected", function () {
    console.log("connected");
    getData(zk, path);
   
    setInterval  (setRandomData,3000,zk,path);
    	
    
});


function setRandomData(client, path){
    var dataF = faker.name.findName() + ":" + faker.phone.phoneNumber();
    var data = new Buffer(dataF);
	//console.log("setting===>" + dataF);
   	client.setData(path, data, function (error, stat) {
        if (error) {
            console.log('Got error when setting data: ' + error);
            return;
        }

        /*
        	console.log(
            '\n\nSet data "%s" on node %s, version: %d.',
            data.toString(),
            path,
            stat.version
        );
        */
    
    });
    
}

function getData(client, path) {
    client.getData(
        path,
        function (event) {
            console.log('Got event: %s', event);
            getData(client, path);
        },
        function (error, data, stat) {
            if (error) {
                console.log('Error occurred when getting data: %s.', error);
                return;
            }

            console.log(
                'Node: %s has data: %s, version: %d',
                path,
                data ? data.toString() : undefined,
                stat.version
            );

        }
    );
}
zk.connect();