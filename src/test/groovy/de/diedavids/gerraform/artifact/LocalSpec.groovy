package de.diedavids.gerraform.artifact

import de.diedavids.gerraform.exception.DuplicateLocalException


class LocalSpec extends AbstractAtrifactSpec {

    def "a local can be created and referenced in a resource"() {

        given:
        Local foo = tf.local("foo", 5)

        when:
        tf.resource("aws_instance", "my_app", [
                "ami": foo.ref()
        ])

        then:
        tf.toMap() == [
            locals: [
                    foo: 5
            ],
            resource: [
                    aws_instance: [
                            my_app: [
                                ami: "\${local.foo}"
                            ]
                    ]
            ]
        ]

    }
    def "locals can reference each other"() {

        given:
        Local foo = tf.local("foo", 5)

        when:
        tf.local("bar", foo.ref() + "-value")

        then:
        tf.toMap() == [
            locals: [
                    foo: 5,
                    bar: "\${local.foo}-value"
            ]
        ]
    }

    def "a local cannot be created twice"() {

        given:
        tf.local("duplicateLocal")

        when:
        tf.local("duplicateLocal")

        then:
        thrown(DuplicateLocalException)
    }

}