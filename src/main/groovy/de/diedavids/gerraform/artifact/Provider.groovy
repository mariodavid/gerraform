package de.diedavids.gerraform.artifact

import de.diedavids.gerraform.Gerraform

class Provider {
    String type
    Map<String, Object> params
    Gerraform tf


    def resource(String type, String name, Map<String, Object> params = [:]) {

        if (this.params.containsKey("alias")) {
            setProviderParam(params)
        }

        tf.resource(fullType(type), name, params)
    }

    private void setProviderParam(Map<String, Object> params) {
        params["provider"] = this.type + "." + this.params["alias"]
    }

    def data(String type, String name, Map<String, Object> params = [:]) {
        tf.data(fullType(type), name, params)
    }

    def fullType(String type) {
        "${this.type}_${type}"
    }

}
