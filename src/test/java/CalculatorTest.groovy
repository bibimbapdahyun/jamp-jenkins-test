import com.epam.id.calc.Calculator
import spock.lang.Specification

class CalculatorTest extends Specification {


    def "should process input"() {
        given:
        def defaultCalculator = new Calculator()

        when:
        def result = defaultCalculator.processInput("2+2*(5-7)/1")
        def result2 = defaultCalculator.processInput("(2+2*(5-7))+12*5")

        then:
        result == -2
        result2 == 58
    }


    def "should safe navigation operation"() {
        given:
        def defaultCalculator = new Calculator()

        when:
        def result = defaultCalculator.safeNavigationOperator(defaultCalculator)

        then:
        result == null
    }


    def "should elvis operator test"() {
        given:
        def defaultCalculator = new Calculator()

        when:
        def result = defaultCalculator.elvisOperator(defaultCalculator)

        then:
        result == 0
    }


    def "should operator over loading"() {
        given:
        def calc1 = new Calculator(4)
        def calc2 = new Calculator(11)

        expect:
        (calc1 + calc2).calculatorNumber == 15
    }
}