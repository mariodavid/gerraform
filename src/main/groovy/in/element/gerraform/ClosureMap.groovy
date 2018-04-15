package in.element.gerraform

class ClosureMap {
    Map<String, Object> params = [:]


    def propertyMissing(String name, value) {
        params[name] = value
    }

    def propertyMissing(String name) {
        params[name]
    }
}