package in.element.gerraform

import in.element.gerraform.exception.DuplicateOutputException

class OutputSpec extends AbstractAtrifactSpec {

    def "an output will be created once it is defined"() {

        given:
        Resource elasticIp = tf.resource("aws_eip","elastic_ip", [:])

        when:
        tf.output("ip", [
                value: elasticIp.ref("public_ip")
        ])

        then:
        tf.toMap() == [
                resource: [
                        aws_eip: [
                                elastic_ip: [:]
                        ]
                ],
                output: [
                        ip: [
                                value: "\${aws_eip.elastic_ip.public_ip}"
                        ]
                ]
        ]
    }

    def "uplicate outputs are not possible"() {
        given:
        tf.output("duplicateOutput", [:])

        when:
        tf.output("duplicateOutput", [:])

        then:
        thrown DuplicateOutputException

    }

}
