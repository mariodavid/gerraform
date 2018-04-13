package in.element.gerraform

class Provider {
    String type
    Map<String, Object> params
    Gerraform tf


    def resource(String type, String name, Map<String, Object> params) {

        if (this.params.containsKey("alias")) {
            setProviderParam(params)
        }

        tf.resource(fullType(type), name, params)
    }

    private void setProviderParam(Map<String, Object> params) {
        params["provider"] = this.type + "." + this.params["alias"]
    }

    def data(String type, String name, Map<String, Object> params) {
        tf.data(fullType(type), name, params)
    }

    def fullType(String type) {
        "${this.type}_${type}"
    }


/*
    def resource(self, type, name, properties):
        if "alias" in self.properties:
            properties = properties.copy()
            properties["provider"] = self.type + "." + self.properties["alias"]

        return self.tf.resource(self._full_type(type), name, properties)


    def data(self, type, name, properties):
        return self.tf.data(self._full_type(type), name, properties)


    def _full_type(self, type):
        return self.type + "_" + type
 */
}
