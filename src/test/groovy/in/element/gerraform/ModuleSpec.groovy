package in.element.gerraform

import in.element.gerraform.artifact.AbstractAtrifactSpec
import in.element.gerraform.artifact.Module
import in.element.gerraform.artifact.Resource
import in.element.gerraform.exception.DuplicateModuleException
import in.element.gerraform.exception.DuplicateResouceException
import org.junit.Ignore

class ModuleSpec extends AbstractAtrifactSpec {


    def "a module cannot be created twice"() {

        given:
        tf.module("name")

        when:
        tf.module("name")

        then:
        thrown(DuplicateModuleException)
    }


    def "a module can be created"() {

        when:
        tf.module("my_app")
        then:
        tf.toMap() == [
                module: [
                        my_app: [:]
                ]
        ]
    }


    def "a module can be created and referenced afterwards"() {
        given:
        Module myModule = tf.module("my_app")

        when:
        tf.resource("aws_route53_record", "${myModule.name}_dns", [
                records: [myModule.ref('aws_instance_public_ip')]
        ])

        then:
        tf.toMap() == [
                resource: [
                        aws_route53_record: [
                                my_app_dns: [
                                        records: ["\${module.my_app.aws_instance_public_ip}"]
                                ]
                        ]
                ]
        ]
    }


}