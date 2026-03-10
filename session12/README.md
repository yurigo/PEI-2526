Exception and RuntimeException
checked vs unchecked exception

En esta sesión se han creado excepciones propias checked y unchecked:

Exception <|--- MyExceptionalException <|--- MoreThanTenException
                                       <|--- LessThanZeroException

          <|--- RuntimeException <|--------- NumberFiveException

Se ha encapsulado el mensaje de error dentro de la excepcion.

Se comparte el código implementado hoy

