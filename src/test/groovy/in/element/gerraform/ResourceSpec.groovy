package in.element.gerraform

import in.element.gerraform.exception.DuplicateResouceException

class ResourceSpec extends AbstractAtrifactSpec {

    def "a resource cannot be created twice"() {

        given:
        tf.resource("aws_instance","duplicateResource")

        when:
        tf.resource("aws_instance","duplicateResource")

        then:
        thrown(DuplicateResouceException)
    }

    def "a resource can be created and referenced afterwards"() {
        given:
        Resource myApp = tf.resource("aws_instance","my_app")

        when:
        tf.resource("aws_route53_record","${myApp.name}_dns", [
                records: [myApp.ref('public_ip')]
        ])

        then:
        tf.toMap() == [
                resource: [
                        aws_instance: [
                                my_app: [:]
                        ],
                        aws_route53_record: [
                                my_app_dns: [
                                        records: ["\${aws_instance.my_app.public_ip}"]
                                ]
                        ]
                ]
        ]
    }

    def "resource names can be reused in properties"() {

        when:
        tf.resource("aws_route53_record","dns") { resource ->
            name = resource.name
        }

        then:
        tf.toMap() == [
                resource: [
                        aws_route53_record: [
                                dns: [
                                        name: "dns"
                                ]
                        ]
                ]
        ]
    }

    def "resource can use a closure to configure the properties of the resource"() {

        when:
        tf.resource("aws_route53_record","dns") {
            name = "my_resource"
        }

        then:
        tf.toMap() == [
                resource: [
                        aws_route53_record: [
                                dns: [
                                        name: "my_resource"
                                ]
                        ]
                ]
        ]
    }

}
