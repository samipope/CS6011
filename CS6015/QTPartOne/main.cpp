
#include "mainWidget.h"
#include <QApplication>
#include <QSpinBox>

int main(int argc, char *argv[])
{
    QApplication app(argc, argv);
    mainWidget w;
    w.show();
    return app.exec();
}
