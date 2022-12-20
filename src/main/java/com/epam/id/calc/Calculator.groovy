package com.epam.id.calc

/**
 * Calculator.
 */
class Calculator {

    def valueStack
    def operatorStack
    def calculatorNumber

    Calculator() {
        operatorStack = new Stack<Character>()
        valueStack = new Stack<Double>()
    }


    Calculator(Integer calculatorNumber) {
        operatorStack = new Stack<Character>()
        valueStack = new Stack<Double>()
        this.calculatorNumber = calculatorNumber
    }

    /**
     * Groovy operator overloading.
     */
    def plus(Calculator other) {
        return new Calculator(this.calculatorNumber + other.calculatorNumber)
    }

    /**
     * Main method that split inputs and after all operations return result.
     *
     * @param input the input
     * @return result
     */
    def processInput(String input) {
        String regex = "${'((?<=\\D)|(?=\\D))'}"
        String result = getRegex(regex)
        String[] tokens = input.split(result)

        for (String token : tokens) {
            char firstSymbols = token.charAt(0)

            if (firstSymbols >= ('0' as Character) && firstSymbols <= ('9' as Character)) {
                valueStack.push(Double.parseDouble(token))
            } else if (isOperator(firstSymbols)) {
                addOperatorToOperatorStack(firstSymbols)
            } else {
                checkParenthesisOperationsAndCalculateResult(firstSymbols)
            }
        }
        clearOperationsStack()
        return getResult()
    }

    static def elvisOperator(Calculator calculator) {
        return calculator.calculatorNumber ?: 0
    }

    static def safeNavigationOperator(Calculator calculator) {
        return calculator?.calculatorNumber
    }

    def clearOperationsStack() {
        while (!operatorStack.empty() && isOperator(operatorStack.peek())) {
            char toProcess = operatorStack.peek()
            operatorStack.pop()
            processOperation(toProcess)
        }
    }

    def getResult() {
        Double result = valueStack.peek()
        valueStack.pop()
        if (operatorStack.empty() || valueStack.empty()) {
            return result
        } else {
            throw new UnsupportedOperationException("Expression error")
        }
    }

    def checkParenthesisOperationsAndCalculateResult(Character operation) {
        if (operation == '(') {
            operatorStack.push(operation)
        } else if (operation == ')') {
            while (!operatorStack.empty() && isOperator(operatorStack.peek())) {
                char toProcess = operatorStack.peek()
                operatorStack.pop()
                processOperation(toProcess)
            }
            if (!operatorStack.empty() && operatorStack.peek() == '(') {
                operatorStack.pop()
            } else {
                throw new UnsupportedOperationException("Unbalanced parenthesis")
            }
        }
    }

    static def getRegex(String regex) {
        return regex
    }

    /**
     * Return true if param is math operators.
     *
     * @param operator the operator
     * @return result
     */
    static def isOperator(Character operator) {
        return operator == '+' || operator == '-' || operator == '*' || operator == '/'
    }

    static def getPriorityOfOperations(Character operator) {
        if (operator == '+' || operator == '-') {
            return 1
        }
        if (operator == '*' || operator == '/') {
            return 2
        }
        return 0
    }

    def addOperatorToOperatorStack(Character operator) {
        if (!operatorStack.empty() && getPriorityOfOperations(operator) <=
                getPriorityOfOperations(operatorStack.peek())) {
            while (!operatorStack.empty() && getPriorityOfOperations(operator) <=
                    getPriorityOfOperations(operatorStack.peek())) {
                Character toProcess = operatorStack.peek()
                operatorStack.pop()
                processOperation(toProcess)
            }
        }
        operatorStack.push(operator)
    }

    /**
     * Set numbers and calculate result.
     *
     * @param operator the operator
     */
    def processOperation(Character operator) {
        double firstNumber = 0, secondNumber = 0
        if (!valueStack.empty()) {
            secondNumber = valueStack.peek()
            valueStack.pop()
        }
        if (!valueStack.empty()) {
            firstNumber = valueStack.peek()
            valueStack.pop()
        }

        calculateResult(operator, firstNumber, secondNumber)
    }

    def calculateResult(Character operator, double firstNumber, double secondNumber) {
        double result = 0
        if (operator == '+') {
            result = firstNumber + secondNumber
        } else if (operator == '-') {
            result = firstNumber - secondNumber
        } else if (operator == '*') {
            result = firstNumber * secondNumber
        } else if (operator == '/') {
            result = firstNumber / secondNumber
        }
        valueStack.push(result)
    }
}


