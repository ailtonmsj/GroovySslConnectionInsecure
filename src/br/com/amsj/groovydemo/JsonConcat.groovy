package br.com.amsj.groovydemo


import groovy.json.JsonOutput
import groovy.json.JsonSlurper

/*
{
	"apiVersion": "v1",
	"kind": "Secret",
	"metadata": {
		"name": "sts-secret-application28"
	},
	"type": "Opaque",
	"data": {
		"client_id_1": "dmFsdWUtMQ==",
		"client_id_2": "dmFsdWUtMg==",
		"client_id_3": "dmFsdWUtMw=="
	}
}
*/

class JsonConcat {

    static final String JSON_INPUT =
            "{" +
                "\"apiVersion\": \"v1\"," +
                "\"kind\": \"Secret\"," +
                "\"metadata\": {" +
                    "\"name\": \"sts-secret-application28\"" +
                "}," +
                "\"type\": \"Opaque\"," +
                "\"data\": {" +
                    "\"client_id_1\": \"dmFsdWUtMQ==\"," + // value-1
                    "\"client_id_2\": \"dmFsdWUtMg==\"," + // value-2
                    "\"client_id_3\": \"dmFsdWUtMw==\"" + // value-3

                "}" +
            "}"

    static String getSecret(){
        return JSON_INPUT
    }


    static void main(def args) throws Exception {

        final String CLIENT_ID_2 = "client_id_2"
        final String NEW_VALUE = "dmFsdWUtNA==" // value-4

        String secret = getSecret()

        //println(secret)

        JsonSlurper jsonSlurper = new JsonSlurper()
        def object = jsonSlurper.parseText(secret)

        Map<String, String> data = object.getAt("data")

        println(object)
        println(data)

        data.put(CLIENT_ID_2, NEW_VALUE)

        println(data)
        println(object)

        def json = JsonOutput.toJson(object)

        println(json)
    }
}
