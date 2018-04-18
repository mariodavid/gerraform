package de.diedavids.gerraform

import de.diedavids.gerraform.artifact.AbstractAtrifactSpec
import de.diedavids.gerraform.artifact.Module
import de.diedavids.gerraform.exception.DuplicateModuleException

class ModuleSpec extends AbstractAtrifactSpec {


    def "a module cannot be created twice"() {

        given:
        tf.module("name")

        when:
        tf.module("name")

        then:
        thrown(DuplicateModuleException)
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