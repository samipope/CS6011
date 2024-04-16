// mainWidget.h
#ifndef MAINWIDGET_H
#define MAINWIDGET_H

#include <QWidget>
#include <QLabel>
#include <QLineEdit>
#include <QPushButton>
#include <QTextEdit>
#include <QRadioButton>
#include <QGroupBox>
#include <QDialogButtonBox>
#include <QVBoxLayout>
#include <QGridLayout>
#include <QSpinBox>

class mainWidget : public QWidget
{
    Q_OBJECT

public:
    explicit mainWidget(QWidget *parent = nullptr);

private slots:
    void fillSummary();

private:
    QLabel *firstNameLabel;
    QLineEdit *firstNameEdit;
    QLabel *lastNameLabel;
    QLineEdit *lastNameEdit;
    QLabel *ageLabel;
    QSpinBox *ageSpinBox;
    QGroupBox *genderGroupBox;
    QRadioButton *maleRadioButton;
    QRadioButton *femaleRadioButton;
    QPushButton *refreshButton;
    QTextEdit *summaryTextEdit;
    QPushButton *finishButton;

    QVBoxLayout *mainLayout;
    QGridLayout *gridLayout;

    void setGridLayout();
    void setVerticalLayout();
    void clearAll();
};

#endif // MAINWIDGET_H
