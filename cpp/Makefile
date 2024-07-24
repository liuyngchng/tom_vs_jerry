CC=gcc
#CFLAGS += -D__SSL1_0__
CM_OBJ:= gtk_test.o

all: obj _game
obj:gtk_test.c
	${CC} -c -g -Wall *.c `pkg-config --cflags --libs gtk+-2.0`
_game: gtk_test.o
	${CC} -g ${CFLAGS} -o _game gtk_test.o `pkg-config --cflags --libs gtk+-2.0`
clean:
	rm -fr _* *.o *.so *.out

