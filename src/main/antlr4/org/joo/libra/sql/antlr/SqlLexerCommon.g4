lexer grammar SqlLexerCommon;

fragment Digit: 		'0'..'9' ;
fragment Alpha: 		'.' | '_' | 'A'..'Z' | 'a'..'z' ;

AND:					('AND' | 'and' | '&&') ;
OR:						('OR' | 'or' | '||') ;
NOT:					('NOT' | 'not' | '!') ;

IS_EQUALS:				('IS' | 'is') ;
IS_EQUALS_NOT:			('IS NOT' | 'is not') ;
IS_EMPTY:				('IS EMPTY' | 'is empty') ;
IS_NOT_EMPTY:			('IS NOT EMPTY' | 'is not empty') ;

CONTAINS:				('CONTAINS' | 'contains') ;
IN:						('IN' | 'in') ;
MATCHES:				('MATCHES' | 'matches') ;
APPEND:					('APPEND' | 'append') ;
PRINT:					('PRINT' | 'print') ;

TRUE:					('TRUE' | 'true') ;
FALSE:					('FALSE' | 'false') ;
UNDEFINED:				('UNDEFINED' | 'undefined') ;
NULL:					('NULL' | 'null') ;

ANY:					('ANY' | 'any') ;
NONE:					('NONE' | 'none') ;
ALL:					('ALL' | 'all') ;
EVERY:					('EVERY' | 'every') ;
FILTER:					('WITH' | 'with') ;
OF:						('OF' | 'of') ;
SATISFIES:				('SATISFIES' | 'satisfies') ;
IF:				        ('IF' | 'if') ;
FOR:				    ('FOR' | 'for') ;

GREATER_THAN:			'>' ;
GREATER_THAN_EQUALS:	'>=' ;
LESS_THAN:				'<' ;
LESS_THAN_EQUALS:		'<=' ;
EQUALS:					'=' ;
NOT_EQUALS:				'!=' ;
DBL_EQUALS:				'==' ;
DOT:					'.' ;
PLUS:					'+' ;
MINUS:					'-' ;
TIMES:					'*' ;
DIVIDE:					'/' ;
MOD:					'%' ;
POW:					'^' ;
QUESTION:				'?' ;
COLON:					':' ;
DBL_COLON:				'::' ;
LPAREN:					'(' ;
RPAREN:					')' ;
LBRACE:					'{' ;
RBRACE:					'}' ;
LSQUARE:                '[' ;
RSQUARE:                ']' ;
COMMA:					',' ;
STRING:					'\'' ~('\r' | '\n' | '\'')* '\'' ;
INTEGER:				'-'* Digit+ ;
DOUBLE:					'-'* Digit+'.'Digit+ ;
INDEX:					'[' Digit+ ']' ;

VARIABLE:				Alpha+ (Alpha | Digit | INDEX)* ;
TEMP_VAR:				'$' Alpha+ (Alpha | Digit | INDEX)* ;