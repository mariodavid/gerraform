package in.element.gerraform.artifact

import in.element.gerraform.exception.DuplicateDataSourceException

class DataSourceSpec extends AbstractAtrifactSpec {


    def "a data source cannot be created twice"() {

        given:
        tf.data("aws_instance", "my_app")

        when:
        tf.data("aws_instance", "my_app")

        then:
        thrown(DuplicateDataSourceException)
    }

    def "a resource can be created and referenced afterwards"() {
        given:
        DataSource myApp = tf.data("aws_instance", "my_app", [
                filter: [[name: "image-id", values: "ami-xxxxxxx"]]
        ])

        when:
        tf.resource("aws_route53_record", "${myApp.name}_dns", [
                records: [myApp.ref('public_ip')]
        ])

        then:
        tf.toMap() == [
                data: [
                        aws_instance: [
                                my_app: [
                                        filter: [[name: "image-id", values: "ami-xxxxxxx"]]
                                ]
                        ]
                ],
                resource: [
                        aws_route53_record: [
                                my_app_dns: [
                                        records: ["\${data.aws_instance.my_app.public_ip}"]
                                ]
                        ]
                ]
        ]
    }

    def "data sources names can be reused in properties"() {

        when:
        tf.data("aws_route53_record", "dns") { dataSource ->
            name = dataSource.name
        }

        then:
        tf.toMap() == [
                data: [
                        aws_route53_record: [
                                dns: [
                                        name: "dns"
                                ]
                        ]
                ]
        ]
    }
}

