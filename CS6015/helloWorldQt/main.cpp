#include "main.h"
#include <QPushButton>

#include <QApplication>
int main(int argc, char **argv) {
    QApplication app (argc, argv);
    QPushButton *button = new QPushButton("hello world");
    button->show();
    return app.exec();
}
