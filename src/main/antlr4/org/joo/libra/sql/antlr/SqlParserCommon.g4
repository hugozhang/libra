
// 如果不在顶层规则中显式要求 EOF（End Of File），
// ANTLR解析器在成功解析到一个有效的表达式后，可能会默认忽略后续的无效输入。
// 具体行为取决于ANTLR解析器的配置和实现细节，以及您在解析过程中如何处理解析结果。
// 通常情况下，ANTLR解析器在成功匹配一个完整的、非终结的语法单元（如 predicate 规则）后，
// 会停止解析并返回结果。这意味着解析器不会尝试进一步解析后续的无效输入，
// 而是将这些输入视为解析结果的“剩余文本”（trailing text）。
// 在大多数情况下，解析器不会对此类剩余文本产生错误或警告，
// 除非您特别配置或编程要求解析器对剩余文本进行检查。
// 如果您希望解析器在遇到无效输入时立即停止并抛出错误，而不是忽略它们，
// 可以采用我在上一条回答中提到的方法：
// 1. **添加一个通用的错误处理规则**，确保词法分析器（lexer）能够识别并标记无效输入为 ERROR Token。
// 2. **在解析器中处理错误**，使用 catch 语句块捕获和抛出预期之外的 ERROR Token。
// 3. **改进顶层规则**，要求其后的输入必须是 EOF，以确保解析器在遇到无效输入时立即停止并抛出错误。
// 通过这些方法，您可以确保解析器对输入的严格校验，及时发现并报告语法错误，而不是简单地忽略无效输入
parser grammar SqlParserCommon;

options {
	tokenVocab = SqlLexerCommon;
}

predicate
:
	expression EOF # expressionExpr
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
	| op = EXIST FOR indexName = TEMP_VAR IN listName = factor IF condition =
    expression # listMatchingExpr
	| PRINT right = expression   # printExpr
	| listName = factor LSQUARE start = INTEGER COLON end = INTEGER RSQUARE # sliceExpr
	| mapName = factor LSQUARE keyName = factor RSQUARE # mapExpr
	| filter # filterMatching

;

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


