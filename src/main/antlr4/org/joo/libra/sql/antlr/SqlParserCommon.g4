parser grammar SqlParserCommon;

options {
	tokenVocab = SqlLexerCommon;
}

predicate
:
	expression
;


expression
:
	NOT inner = expression # notExpr
	| left = expression AND right = expression # andExpr
	| left = expression OR right = expression # orExpr
	| term # termExpr
	| main = expression QUESTION left = factor COLON right = factor #
	conditionalExpr
	| op = ANY FOR indexName = TEMP_VAR IN listName = factor IF condition =
	expression # listMatchingExpr
	| op = NONE FOR indexName = TEMP_VAR IN listName = factor IF condition =
	expression # listMatchingExpr
	| op = ALL FOR indexName = TEMP_VAR IN listName = factor IF condition =
	expression # listMatchingExpr
	| op = EVERY FOR indexName = TEMP_VAR IN listName = factor IF condition =
    expression # listMatchingExpr
	| PRINT right = expression   # printExpr
	| listName = factor LSQUARE start = INTEGER COLON end = INTEGER RSQUARE # sliceExpr
	| mapName = factor LSQUARE keyName = factor RSQUARE # mapExpr
//	| serviceName = VARIABLE DBL_COLON methodName = VARIABLE LPAREN (argList)? RPAREN # serviceCallExpr
	| serviceName = VARIABLE DBL_COLON methodName = VARIABLE LPAREN inner = list RPAREN # serviceCallExpr
	| serviceName = VARIABLE DBL_COLON methodName = VARIABLE LPAREN RPAREN # serviceCallExpr

	| filter # filterMatching

;

argList: factor (',' factor)*;

term
:
	left = factor op = GREATER_THAN right = factor # compareExpr
	| left = factor op = GREATER_THAN_EQUALS right = factor # compareExpr
	| left = factor op = LESS_THAN right = factor # compareExpr
	| left = factor op = LESS_THAN_EQUALS right = factor # compareExpr
//	| left = factor op = EQUALS right = factor # equalExpr
	| left = factor op = DBL_EQUALS right = factor # equalExpr
	| left = factor op = NOT_EQUALS right = factor # notEqualExpr
	| left = factor op = IS_EQUALS_NOT right = factor # notEqualExpr
	| left = factor op = IS_EQUALS right = factor # equalExpr
	| left = factor op = IS_EMPTY # emptyExpr
	| left = factor op = IS_NOT_EMPTY # emptyExpr
	| left = factor op = CONTAINS right = factor # containsExpr
	| left = factor op = STARTS_WITH right = factor # containsExpr
	| left = factor op = ENDS_WITH right = factor # containsExpr
	| left = factor op = MATCHES right = factor # matchesExpr
	| left = factor op = APPEND right = factor # appendExpr
	| left = factor op = IN right = factor # inExpr
    | factor # factorExpr
;

factor
:
	DOUBLE # numberExpr
	| INTEGER # numberExpr
	| STRING # stringExpr
	| TRUE # booleanExpr
	| FALSE # booleanExpr
	| UNDEFINED # nullExpr
	| NULL # nullExpr
	| VARIABLE # variableExpr
	| TEMP_VAR # tempVarExpr
	| LPAREN expression RPAREN # parenExpr
	| name = VARIABLE LPAREN inner = list RPAREN # functionExpr
	| name = VARIABLE LPAREN RPAREN # functionExpr
	| left = factor op = POW right = factor # mathExpr
	| left = factor op = TIMES right = factor # mathExpr
	| left = factor op = DIVIDE right = factor # mathExpr
	| left = factor op = PLUS right = factor # mathExpr
	| left = factor op = MINUS right = factor # mathExpr
	| left = factor op = MOD right = factor # mathExpr
	| LSQUARE item = list RSQUARE # wrapListExpr
	| LSQUARE listResult = expression RSQUARE # wrapListExpr
	| LSQUARE RSQUARE # wrapListExpr
;

list
:
	factor # listFactorExpr
	| left = list COMMA right = list # listCommaExpr
	| filter # filterExpr
;

filter
:
	FOR indexName = TEMP_VAR IN listName = factor IF condition = expression # filterMatchingExpr
	| transform = factor FOR indexName = TEMP_VAR IN listName = factor IF condition = expression # filterMatchingExpr
;


