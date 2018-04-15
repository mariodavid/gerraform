package in.element.gerraform

import in.element.gerraform.artifact.AbstractAtrifactSpec


class RenderingSpec extends AbstractAtrifactSpec {


    def "toMap renders a Map representation"() {
        given:
        tf.variable('foo', [default: "bar"])

        expect:
        tf.toMap() == [
                variable: [
                        foo: [default: "bar"]
                ]
        ]
    }

    def "toJSON renders the JSON representation"() {
        given:
        tf.variable('foo', [default: "bar"])
        when:
        String jsonString = tf.toJSON()
        then:
        jsonString == """{
    "variable": {
        "foo": {
            "default": "bar"
        }
    }
}"""
    }
}