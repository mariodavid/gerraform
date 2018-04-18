package in.element.gerraform

import in.element.gerraform.artifact.Resource
import in.element.gerraform.artifact.Variable
import spock.lang.Specification


class ExampleUsageSpec extends Specification {



    def "Gerraform description can be used to create Terraform JSON"() {


        given: 'the gerraform object is the main interaction point'
        Gerraform tf = new Gerraform()

        when: 'some gerraform variables are created'

        Variable domainName = tf.variable('domainName', [default: "www.gerraform.com"])
        tf.variable('foo', [default: "bar"])


        and: 'some gerraform resources are created'

        Resource myApp = tf.resource("aws_instance","my_app")

        tf.resource("aws_route53_record","${myApp.name}_dns") {
                records = [myApp.ref('public_ip')]
                name = domainName.ref()
        }

        then: 'the terraform JSON lists all the stuff'

        tf.toJSON() == """{
    "variable": {
        "domainName": {
            "default": "www.gerraform.com"
        },
        "foo": {
            "default": "bar"
        }
    },
    "resource": {
        "aws_instance": {
            "my_app": {
                
            }
        },
        "aws_route53_record": {
            "my_app_dns": {
                "records": [
                    "\${aws_instance.my_app.public_ip}"
                ],
                "name": "\${var.domainName}"
            }
        }
    }
}"""
    }
}