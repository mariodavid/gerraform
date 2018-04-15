package in.element.gerraform.artifact

import in.element.gerraform.Gerraform
import spock.lang.Specification

abstract class AbstractAtrifactSpec extends Specification {

    Gerraform tf

    void setup() {
        tf = new Gerraform()
    }


}