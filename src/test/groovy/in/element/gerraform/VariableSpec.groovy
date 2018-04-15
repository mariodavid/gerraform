package in.element.gerraform

import in.element.gerraform.exception.DuplicateVariableException


class VariableSpec extends AbstractAtrifactSpec {

    def "a variable can be created and referenced in a resource"() {

        given:
        Variable foo = tf.variable("foo", [
                default: "bar"
        ])

        when:

        tf.resource("aws_instance", "my_app", [
                "ami": foo.ref()
        ])

        then:
        tf.toMap() == [
            variable: [
                    foo: [
                        default: "bar"
                    ]
            ],
            resource: [
                    aws_instance: [
                            my_app: [
                                ami: "\${var.foo}"
                            ]
                    ]
            ]
        ]

    }


    def "a variable can use a closure to configure the properties of the variable"() {

        when:
        tf.variable("foo") {
            value = "bar"
        }

        then:
        tf.toMap() == [
                variable: [
                        foo: [
                            value: "bar"
                        ]
                ]
        ]
    }
    def "a variable cannot be created twice"() {

        given:
        tf.variable("duplicateVariable")

        when:
        tf.variable("duplicateVariable")

        then:
        thrown(DuplicateVariableException)
    }

}