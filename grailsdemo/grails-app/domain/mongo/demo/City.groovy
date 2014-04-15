package mongo.demo

class City {

    String country
    String state
    String cityName
    String postalCode
    List location

    static mapping = {
        location geoIndex:true, indexAttributes:[location: "2dsphere"]
    }

    static constraints = {
        country nullable: true
        state nullable: true
        cityName nullable: true
        postalCode nullable: true
    }
}
