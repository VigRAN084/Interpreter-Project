README
-------


Author: Vignesh Rangarajan

Interpreter

--------------------------

The language is called Simple. There are two sample code attachments:

1. demo.simple - demonstrates a sample program, with assignments, conditions, loops, string comparisons,
scanners, print functionalities, and the other project specifications.

2. errors.simple - demonstrates a sample program, with intentional problems with the code
to showcase how the program generates compilation errors

How to run the program:
-----------------------

The main class is called Simple.java. It takes the .simple file as an argument
when in the run configurations.

Supported Statements:
----------------------

1. Assignments of Variables - number and string accepted
2. If Statements
3. Print Statements
4. Jumpto Statements
5. Scan Statements

Supported Expressions:
----------------------

1. Variable Expressions
2. Arithmetic Expressions
3. Number Values
4. String Values

Supported TokenTypes:
---------------------

1. =,+,-,*,/,%,<,>
2. Comments begin with '#'
3. Word tokens (any word, like if, then, etc.)
4. Label Tokens (end with colons, like "top:")
5. String tokens are enclosed in ""
6. Number tokens - only takes integers, floats and doubles not allowed
7. New Lines
8. EOF - indicating the end of the file

Important Classes:
------------------

1. Simple.java - Main Class
2. Tokenizer.java - Reads file as string and extracts supported tokens
3. Parser.java - Goes through the list of tokens and builds executable Statements

Important Interfaces:
----------------------

1. SimpleExpression.java - contains the evaluate() method, implemented by OperatorExpression and
VariableExpression
2. ValueExpression.java - inherits from SimpleExpression.java, and defines toNumber(), toString(), and
getType() methods; implemented by StringValueExpression and NumberValueExpression
3. SimpleStatement.java - Defines the execute() method and implemented by PrintStatement.java,
JumptoStatement.java, AssignStatement.java, IfElseStatement.java, etc.

Important Methods:
------------------

1. Simple.run() - main entry point, runs the .simple code
2. Tokenizer.extractTokens() - extracts the tokens from the program, and returns a list of Token
Objects.
3. Parser.parse() - Parses through the list of tokens and builds a List of Statement objects; it
also looks for compilation issues and generates error messages for the user.
4. Simple.execute() - Executes the individual Statement objects from the Parser.parse() method and
outputs

