#include "main.h"
#include "mainwidget.h"
#include <QApplication>

int main(int argc, char *argv[]) {
    QApplication app(argc, argv);

    mainWidget w; // Create an instance of your mainWidget class
    w.show();     // Use the show() function to display the widget

    return app.exec(); // Start the event loop
}
