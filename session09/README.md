Interfaces

Se han introducido las interfaces para separar el qué (tiene que hacer una clase) del cómo (lo debe hacer).

Se ha explicado un ejemplo (teórico) con DAO y DAOCSV y cómo en un futuro cambiar la implementación a DAOJSON o DAOMySQL o DAOTXT o DAOPostgress o DAOSQLite...


Se ha implementado en clase un ejemplo de MenuInterface y su implementación MenuConsole ó MenuColor o MenuWindows...

Se ha explicado que List es una Interface y ArrayList y Vector son una implementación de ella.

Se ha añadido un MySuperList como implementación de Lista y visto y comprobado que para hacer una Lista debemos implementar tódos los métodos de la interfaz (cumplimentar el contrato)


Como actividad se ha pedido que se recupere la actividad de Sensors Biométrics (session05) e iterarla para añadirle 2 interfaces: Menu y DAO.  De este modo conseguimos desacoplar System.in y System.out del main y acoplarlo a una interface Menu.  Y lectura y escritura a ficheros a través de una interfaz DAO.