package in.element.gerraform

class ProviderSpec extends AbstractAtrifactSpec {

    def "no provider will lead to no output"() {

        expect:
        tf.toMap() == [:]
    }


    def "provider can create resources and will be part of the output"() {
        given:
        Provider aws = tf.provider("aws", [:])
        when:
        aws.resource('instance', 'my_app', [:])
        then:
        tf.toMap() == [
                provider: [[aws: [:]]],
                resource: [
                        aws_instance: [
                                my_app: [:]
                        ]
                ]
        ]
    }

    def "provider can create data sources and will be part of the output"() {
        given:
        Provider aws = tf.provider("aws", [:])
        when:
        aws.data('instance', 'my_app', [:])
        then:
        tf.toMap() == [
                provider: [[aws: [:]]],
                data: [
                        aws_instance: [
                                my_app: [:]
                        ]
                ]
        ]
    }


    def "multiple providers with different aliases can exist"() {
        given:
        Provider aws = tf.provider("aws", [:])
        Provider aws2 = tf.provider("aws", [alias: "provider2"])
        when:
        aws.resource('instance', 'app1', [:])

        and:
        aws2.resource('instance', 'app2', [:])
        then:
        tf.toMap() == [
                provider: [
                        [
                                aws: [:]
                        ],
                        [
                                aws: [
                                        alias: "provider2"
                                ]
                        ],
                ],
                resource: [
                        aws_instance: [
                                app1: [:],
                                app2: [
                                        provider: "aws.provider2"
                                ]
                        ],
                ]
        ]
    }
}
