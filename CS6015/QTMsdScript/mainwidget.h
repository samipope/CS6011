#ifndef MAINWIDGET_H
#define MAINWIDGET_H

#include <QWidget>
#include <QTextEdit>
#include <QPushButton>
#include <QRadioButton>
#include <QLabel>
#include <QVBoxLayout>
#include <QHBoxLayout>
#include <QGroupBox>
#include <QFileDialog>

#include "expr.h"
#include "parse.h"
#include "val.h"
#include "pointer.h"
#include "env.h"



class mainWidget : public QWidget {
    Q_OBJECT

public:
    explicit mainWidget(QWidget *parent = nullptr);

private:
    // UI elements
    QTextEdit *expressionInput;
    QTextEdit *resultDisplay;
    QPushButton *submitButton;
    QPushButton *resetButton;
    QRadioButton *interpButton;
    QRadioButton *prettyPrintButton;
    QLabel *expressionLabel;
    QLabel *resultLabel;
    QVBoxLayout *mainLayout;
    QHBoxLayout *buttonLayout;
    QGroupBox *optionsGroupBox;

    // Setup functions
    void setupUI();
    void setupConnections();

private slots:
    void onInterpClicked();
    void onPrettyPrintClicked();
    void onSubmitClicked();
    void onResetClicked();
};

#endif // MAINWIDGET_H
