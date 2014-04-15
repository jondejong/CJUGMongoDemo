package mongo.demo

import grails.converters.JSON

class CityController {
    def fileName = '/Users/jondejong/CJUG/code/grailsdemo/citydata/citydata.csv'

    def near() {
        def center = [Double.parseDouble(params.lat), Double.parseDouble(params.long)]
        def count = params.count ?: 5

        def cities =  City.findAllByLocationNear(center, [max: count])
        def resp = [count: cities.size(), cities : cities]
        render resp as JSON
    }

    def around() {
        def center = [Double.parseDouble(params.lat), Double.parseDouble(params.long)]
        def cities = City.findAllByLocationWithinCircle([center, Double.parseDouble(params.radius)])
        def resp = [count: cities.size(), cities : cities]
        render resp as JSON
    }

    def empty () {
        def count = 0
        City.findAll().each {
            it.delete()
            count++
        }

        def resp = [status: "success", count: count]
        render resp as JSON
    }

    def load() {
        def dataFile = new File(fileName)

        def cityMap = [:]

        def count = 0
        dataFile.eachLine {
            def tokens = it.split(",")
            tokens = tokens.collect{it.replace("\"", "")}
            if(tokens[1] == "US" && tokens[2] && tokens[3] && tokens[5].isDouble() && tokens[6].isDouble()) {
                def state = tokens[2]
                def city = tokens[3]
                def found = false
                if(cityMap.containsKey(state)) {
                    if(cityMap.get(state).contains(city)) {
                        found = true
                    }else {
                        cityMap.get(state).add(city)
                    }
                } else {
                    cityMap.put(state, [city])
                }
                if(!found) {
                    def latitude = Double.parseDouble(tokens[5])
                    def longitude = Double.parseDouble(tokens[6])
                    def location = [latitude, longitude]
                    new City(country: tokens[1], state: state, cityName: city, postalCode: tokens[4], location: location ).save(failOnError: true)
                    count++
                }
            }

        }

        def resp = [status: "success", count: count]
        render resp as JSON
    }
}