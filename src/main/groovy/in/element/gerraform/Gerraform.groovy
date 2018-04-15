package in.element.gerraform

import groovy.json.JsonOutput
import in.element.gerraform.exception.DuplicateDataSourceException
import in.element.gerraform.exception.DuplicateLocalException
import in.element.gerraform.exception.DuplicateOutputException
import in.element.gerraform.exception.DuplicateResouceException
import in.element.gerraform.exception.DuplicateVariableException

class Gerraform {

    List<Provider> providers = []
    Map<String, Map<String, Object>> variables = [:]
    Map<String, Object> locals = [:]
    Map<String, Map<String, Object>> resources = [:]
    Map<String, Map<String, Object>> dataSources = [:]
    Map<String, Map<String, Object>> outputs = [:]



    def variable(String name, Map<String, Object> params = [:]) {
        if (variables.containsKey(name)) {
            throw new DuplicateVariableException("The variable $name already exists")
        }
        variables[name] = params
        new Variable(name: name)
    }

    def variable(String name, Closure paramsClosure) {
        if (variables.containsKey(name)) {
            throw new DuplicateVariableException("The variable $name already exists")
        }
        def variable = new Variable(name: name)
        variables[name] = closureToMap(paramsClosure, variable)

        variable
    }

    def local(String name, Object value = "") {
        if (locals.containsKey(name)) {
            throw new DuplicateLocalException("The local $name already exists")
        }
        locals[name] = value
        new Local(name: name)
    }

    def provider(String type, Map<String, Object> params = [:]) {
        def provider = new Provider(type: type, params: params, tf: this)
        providers << provider
        provider
    }
    def provider(String type, Closure paramsClosure) {
        def provider = new Provider(type: type, tf: this)

        provider.params = closureToMap(paramsClosure, provider)

        providers << provider
        provider
    }


    def resource(String type, String name, Map<String, Object> params = [:]) {
        initResourceForType(type, name)

        Resource resource = new Resource(type: type, name: name)

        resources[type][name] = params

        resource
    }

    def resource(String type, String name, Closure paramsClosure) {
        initResourceForType(type, name)

        Resource resource = new Resource(type: type, name: name)

        resources[type][name] = closureToMap(paramsClosure, resource)

        resource
    }

    Map<String, Object> closureToMap(Closure paramsClosure, def closureParameter) {
        ClosureMap subResource = new ClosureMap()
        paramsClosure.delegate = subResource
        paramsClosure.call(closureParameter)
        subResource.params
    }

    private void initResourceForType(String type, String name) {
        resources[type] = resources[type] ?: [:]
        if (resources[type].containsKey(name)) {
            throw new DuplicateResouceException()
        }
    }


    def data(String type, String name, Map<String, Object> params = [:]) {
        initDataSourceForType(type, name)

        DataSource dataSource = new DataSource(type: type, name: name)

        dataSources[type][name] = params

        dataSource
    }

    def data(String type, String name, Closure paramsClosure) {
        initDataSourceForType(type, name)

        DataSource dataSource = new DataSource(type: type, name: name)

        dataSources[type][name] = closureToMap(paramsClosure, dataSource)

        dataSource
    }

    private void initDataSourceForType(String type, String name) {
        dataSources[type] = dataSources[type] ?: [:]
        if (dataSources[type].containsKey(name)) {
            throw new DuplicateDataSourceException()
        }
    }


    def output(String name,Map<String, Object> params = [:]) {
        if (outputs.containsKey(name)) {
            throw new DuplicateOutputException()
        }

        outputs[name] = params
    }



    List<Map<String, Object>> toProviderMap() {
        providers.collect { Provider provider ->
            [(provider.type): provider.params]
        }
    }

    Map toMap() {
        Map<String, Object> result = [:]
        if (dataSources) {
            result["data"] = dataSources
        }
        if (providers) {
            result["provider"] = toProviderMap()
        }
        if (variables) {
            result["variable"] = variables
        }
        if (locals) {
            result["locals"] = locals
        }
        if (resources) {
            result["resource"] = resources
        }
        if (outputs) {
            result["output"] = outputs
        }
        result
    }

    String toJSON() {
        JsonOutput.prettyPrint(JsonOutput.toJson(toMap()))
    }
}

class ClosureMap {
    Map<String, Object> params = [:]


    def propertyMissing(String name, value) {
        params[name] = value
    }

    def propertyMissing(String name) {
        params[name]
    }
}