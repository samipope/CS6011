#include "mainwidget.h"
#include <QVBoxLayout>
#include <QHBoxLayout>
#include <QFormLayout>

mainWidget::mainWidget(QWidget *parent) : QWidget(parent) {
    setupUI();
    setupConnections();
}

void mainWidget::setupUI() {
    // Create and configure widgets
    expressionInput = new QTextEdit(this);
    resultDisplay = new QTextEdit(this);
    submitButton = new QPushButton("Submit", this);
    resetButton = new QPushButton("Reset", this);
    interpButton = new QRadioButton("Interp", this);
    prettyPrintButton = new QRadioButton("Pretty Print", this);
    expressionLabel = new QLabel("Expression:", this);
    resultLabel = new QLabel("Result:", this);

    // Set up the layout
    mainLayout = new QVBoxLayout(this);
    buttonLayout = new QHBoxLayout();
    optionsGroupBox = new QGroupBox();

    // Options for radio buttons
    QVBoxLayout *optionsLayout = new QVBoxLayout(optionsGroupBox);
    optionsLayout->addWidget(interpButton);
    optionsLayout->addWidget(prettyPrintButton);
    interpButton->setChecked(true);  // Set interp as default

    // Arrange widgets in the layouts
    mainLayout->addWidget(expressionLabel);
    mainLayout->addWidget(expressionInput);
    mainLayout->addWidget(optionsGroupBox);

    buttonLayout->addWidget(submitButton);
    buttonLayout->addWidget(resetButton);
    mainLayout->addLayout(buttonLayout);

    mainLayout->addWidget(resultLabel);
    mainLayout->addWidget(resultDisplay);

    // Set the main layout of the widget
    this->setLayout(mainLayout);
}

void mainWidget::setupConnections() {
    // Connect signals to slots
    connect(submitButton, &QPushButton::clicked, this, &mainWidget::onSubmitClicked);
    connect(resetButton, &QPushButton::clicked, this, &mainWidget::onResetClicked);
    connect(interpButton, &QRadioButton::clicked, this, &mainWidget::onInterpClicked);
    connect(prettyPrintButton, &QRadioButton::clicked, this, &mainWidget::onPrettyPrintClicked);

}

void mainWidget::onSubmitClicked() {
    // Get the text from the input field and prepare it for parsing
    std::string inputText = expressionInput->toPlainText().toStdString();
    std::istringstream exprStream(inputText);

    try {
        PTR(Expr) expr = parse_expr(exprStream); // Parse the expression from the input stream

        if (interpButton->isChecked()) {
            // If 'Interp' radio button is selected
            PTR(Val) result = expr->interp(Env::empty); // Interpret the expression
            std::string resultStr = result->to_string(); // Convert the result to a string
            resultDisplay->setText(QString::fromStdString(resultStr)); // Display the result
        } else if (prettyPrintButton->isChecked()) {
            // If 'Pretty Print' radio button is selected
            std::string prettyStr = expr->to_pp_string(); // Get the pretty-printed string
            resultDisplay->setText(QString::fromStdString(prettyStr)); // Display the pretty-printed result
        } else {
            // If no radio button is selected (which ideally shouldn't happen)
            resultDisplay->setText("Please select a mode (Interp or Pretty Print).");
        }
    } catch (const std::runtime_error& e) {
        // Catch and display errors specific to runtime issues (e.g., no value for a variable)
        resultDisplay->setText("Error: " + QString::fromStdString(e.what()));
    } catch (const std::exception& e) {
        // Catch any other std::exceptions
        resultDisplay->setText("An error occurred: " + QString::fromStdString(e.what()));
    } catch (...) {
        // Catch-all for any other exceptions
        resultDisplay->setText("An unknown error occurred.");
    }
}


void mainWidget::onResetClicked() {
    expressionInput->clear();
    resultDisplay->clear();
}

void mainWidget::onInterpClicked() {
    QString inputText = expressionInput->toPlainText();  // Get the text from the input QTextEdit
    if (inputText.trimmed().isEmpty()) {
        resultDisplay->setText("Please enter an expression to interpret.");
        return;  // Exit if no input is provided
    }

    std::istringstream exprStream(inputText.toStdString());  // Convert QString to std::string and then to stream

    try {
        PTR(Expr) expr = parse_expr(exprStream);
        PTR(Val) result = expr->interp(Env::empty);
        std::string resultStr = result->to_string();
        resultDisplay->setText(QString::fromStdString(resultStr));
    } catch (const std::runtime_error& e) {
        resultDisplay->setText("Error: " + QString::fromStdString(e.what()));
    } catch (const std::exception& e) {
        resultDisplay->setText("An error occurred: " + QString::fromStdString(e.what()));
    } catch (...) {
        resultDisplay->setText("An unknown error occurred.");
    }
}

void mainWidget::onPrettyPrintClicked() {
    QString inputText = expressionInput->toPlainText();  // Get the text from the input QTextEdit
    if (inputText.trimmed().isEmpty()) {
        resultDisplay->setText("Please enter an expression to pretty print.");
        return;  // Exit if no input is provided
    }

    std::istringstream expr_stream(inputText.toStdString());  // Convert QString to std::string and then to stream

    try {
        PTR(Expr) expr = parse_expr(expr_stream);
        std::string prettyStr = expr->to_pp_string();
        resultDisplay->setText(QString::fromStdString(prettyStr));
    } catch (const std::runtime_error& e) {
        //no variable error caught
        resultDisplay->setText("Error: " + QString::fromStdString(e.what()));
    } catch (const std::exception& e) {
        // catch any other std::exception
        resultDisplay->setText("An error occurred: " + QString::fromStdString(e.what()));
    } catch (...) {
        // catch-all for any other exceptions not covered above
        resultDisplay->setText("An unknown error occurred.");
    }

}
