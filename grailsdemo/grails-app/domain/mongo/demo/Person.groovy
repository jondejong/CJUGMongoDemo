package mongo.demo

import org.bson.types.ObjectId

class Person {

    ObjectId id
    String firstName
    String lastName
    Integer age

    String toString() {
        "${firstName} ${lastName}"
    }

    static constraints = {
    }
}
