[![Build Status](https://travis-ci.org/mariodavid/gerraform.svg?branch=master)](https://travis-ci.org/mariodavid/gerraform)
[![license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

# gerraform
Terraform scripting in Groovy

## Why
Gerraform allows to write Terraform descriptions in Groovy instead of dealing with [Terraform's HCL](https://www.terraform.io/docs/configuration/syntax.html).

It uses the facility of Terraform to interact with JSON as an input format. Gerraform will create the Terraform JSON. 

## Example usage

Here's a snippet of example code (copied over from an [ExampleUsageSpec](https://github.com/mariodavid/gerraform/blob/master/src/test/groovy/in/element/gerraform/ExampleUsageSpec.groovy))
which should give you a hint on how to use this project:

```

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
```


## Credits
This project is based on the idea of the python based library: [ulich/terrafornication](https://github.com/ulich/terrafornication)
